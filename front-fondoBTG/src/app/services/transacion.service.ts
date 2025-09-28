// src/app/services/transaccion.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cliente } from './cliente.service';
import { Fondo } from './fondo.service';

export interface EstadoTransaccion {
  id: number;
  estado: string; 
  descripcion: string;
}

export interface Transaccion {
  id: number;
  cliente: Cliente;
  fondo: Fondo;
  estadoTransaccion: EstadoTransaccion;
  fecha: string;
}

@Injectable({
  providedIn: 'root'
})
export class TransaccionService {
  private apiUrl = `${environment.apiUrl}/transacciones`;

  constructor(private http: HttpClient) {}

  obtenerPorCliente(clienteId: number): Observable<Transaccion[]> {
    return this.http.get<Transaccion[]>(`${this.apiUrl}/cliente-id/${clienteId}`);
  }
}
