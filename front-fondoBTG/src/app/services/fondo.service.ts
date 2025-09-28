import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Fondo {
  id: number;
  nombre: string;
  montoVinculacion: number;
  categoria: string;
  suscrito?: boolean; 
}

@Injectable({
  providedIn: 'root'
})
export class FondoService {
  private apiUrl = `${environment.apiUrl}/fondos`;

  constructor(private http: HttpClient) {}

  obtenerFondos(): Observable<Fondo[]> {
    return this.http.get<Fondo[]>(this.apiUrl);
  }
}
