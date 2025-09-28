import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cliente } from './cliente.service';
import { Fondo } from './fondo.service';

export type EstadoTx = 'APERTURA' | 'CANCELAR';

export interface SuscripcionItem {
  id: number;
  cliente: Cliente;
  fondo: Fondo;
}

export interface SuscripcionResponse {
  cliente: Cliente;
  fondo: Fondo;
}

@Injectable({ providedIn: 'root' })
export class SuscripcionService {
  private apiUrl = `${environment.apiUrl}/suscripciones`;

  constructor(private http: HttpClient) {}

  crearOActualizar(clienteId: number, fondoId: number, estado: EstadoTx): Observable<SuscripcionResponse> {
    const body = { clienteId, fondoId, estadoTransaccion: estado };
    return this.http.post<SuscripcionResponse>(this.apiUrl, body);
  }

  obtenerPorCliente(clienteId: number): Observable<SuscripcionItem[]> {
    return this.http.get<SuscripcionItem[]>(`${this.apiUrl}/cliente-id/${clienteId}`);
  }
}
