import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Boleta, ItemCompra, Producto } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private http = inject(HttpClient);
  private auth = inject(AuthService);

  private readonly productosUrl = '/api/productos';
  private readonly ventasUrl = '/api/ventas/generar';

  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.productosUrl, {
      headers: this.authHeaders()
    });
  }

  generarBoleta(items: ItemCompra[]): Observable<Boleta> {
    return this.http.post<Boleta>(
      this.ventasUrl,
      {
        items: items.map(item => ({
          productoId: item.producto.id,
          cantidad: item.cantidad
        }))
      },
      { headers: this.authHeaders() }
    );
  }

  private authHeaders(): HttpHeaders {
    const token = this.auth.idToken();
    return token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : new HttpHeaders();
  }
}
