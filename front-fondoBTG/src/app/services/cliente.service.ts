// src/app/services/cliente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Cliente {
  id: number;
  nombre: string;
  apellidos: string;
  numeroIdentificacion: string;
  creditos: number;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = `${environment.apiUrl}/clientes`;

  constructor(private http: HttpClient) {}

  obtenerCliente(): Observable<Cliente> {
    return this.http.get<Cliente[]>(this.apiUrl).pipe(
      map(clientes => clientes[0])
    );
  }
}
