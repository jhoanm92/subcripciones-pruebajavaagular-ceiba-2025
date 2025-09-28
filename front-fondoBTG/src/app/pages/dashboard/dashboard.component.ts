import { CommonModule } from '@angular/common';
import { Component, inject, Inject, OnInit } from '@angular/core';
import { Cliente, ClienteService } from '../../services/cliente.service';
import { Fondo as FondoBase, FondoService } from '../../services/fondo.service';
import { Transaccion, TransaccionService } from '../../services/transacion.service';
import { EstadoTx, SuscripcionService } from '../../services/suscripcion.service';
import { finalize, forkJoin, map, switchMap } from 'rxjs';

type Fondo = FondoBase & { suscrito?: boolean; loading?: boolean };

@Component({
    selector: 'app-dashboard',
    imports: [CommonModule],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

    cliente!: Cliente;
    fondos: Fondo[] = [];
    transaccionService = inject(TransaccionService);
    clienteService = inject(ClienteService);
    fondoService = inject(FondoService);
    suscripcionService = inject(SuscripcionService);

    ngOnInit(): void {
        this.clienteService.obtenerCliente()
            .pipe(
                switchMap(cliente => {
                    this.cliente = cliente;
                    return forkJoin({
                        fondos: this.fondoService.obtenerFondos().pipe(
                            map(fs => fs.map(f => ({ ...f, suscrito: false, loading: false })))
                        ),
                        fondosSuscritos: this.suscripcionService.obtenerPorCliente(cliente.id)
                    });
                })
            )
            .subscribe({
                next: ({ fondos, fondosSuscritos }) => {
                    this.fondos = fondos;

                    const idsSuscritos = new Set(fondosSuscritos.map(f => f.id));

                    this.fondos.forEach(f => {
                        f.suscrito = idsSuscritos.has(f.id);
                    });
                },
                error: (e) => console.error('Error inicializando dashboard:', e)
            });
    }

    onClickAccion(f: Fondo) {
        if (!this.cliente) return;

        const estado: EstadoTx = f.suscrito ? 'CANCELAR' : 'APERTURA';
        f.loading = true;

        this.suscripcionService
            .crearOActualizar(this.cliente.id, f.id, estado)
            .pipe(finalize(() => (f.loading = false)))
            .subscribe({
                next: (resp) => {
                    this.cliente.creditos = resp.cliente.creditos;
                    f.suscrito = (estado === 'APERTURA');
                },
                error: (err) => {
                    if (err?.status === 404) {
                        console.warn('Suscripción no encontrada / no aplicada (404).');
                        alert('No fue posible completar la operación (404). Intenta más tarde.');
                    } else {
                        console.error('Error en suscripción:', err);
                        alert('Ocurrió un error. Intenta más tarde.');
                    }
                }
            });
    }
}
