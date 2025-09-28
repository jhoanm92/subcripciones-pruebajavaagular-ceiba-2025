import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Transaccion, TransaccionService } from '../../services/transacion.service';

@Component({
  selector: 'app-historial',
  imports: [CommonModule],
  templateUrl: './historial.component.html',
  styleUrl: './historial.component.scss'
})
export class HistorialComponent implements OnInit {

  movimientos: Transaccion[] = [];
  clienteNombre = '';
  saldoActual = 0;
  cargado = false;

  constructor(private txService: TransaccionService) {}

  ngOnInit(): void {
    // OJO: si ya tienes el id del cliente autenticado, úsalo aquí.
    const clienteId = 1;

    this.txService.obtenerPorCliente(clienteId).subscribe({
      next: (txs) => {
        this.movimientos = txs.reverse() ?? [];
        // Tomamos datos del cliente desde el primer movimiento (si existe)
        if (this.movimientos.length > 0) {
          const c = this.movimientos[0].cliente;
          this.clienteNombre = `${c.nombre} ${c.apellidos}`;
          this.saldoActual = c.creditos;
        }
        this.cargado = true;
      },
      error: (e) => {
        console.error('Error cargando historial:', e);
        this.cargado = true;
      }
    });
  }
}
