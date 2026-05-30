import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ProductoService } from '../../services/producto.service';
import { Boleta, ItemCompra, Producto } from '../../models/producto.model';

@Component({
  selector: 'app-producto-venta',
  standalone: true,
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './producto-venta.component.html',
  styleUrl: './producto-venta.component.css'
})
export class ProductoVentaComponent implements OnInit {
  private productoService = inject(ProductoService);
  readonly auth = inject(AuthService);

  productos = signal<Producto[]>([]);
  compra = signal<ItemCompra[]>([]);
  boleta = signal<Boleta | null>(null);
  loading = signal(false);
  generating = signal(false);
  error = signal<string | null>(null);

  totalActual = computed(() =>
    this.compra().reduce((total, item) => total + item.producto.precio * item.cantidad, 0)
  );

  ngOnInit(): void {
    if (this.canLoadProtectedData()) {
      this.loadProductos();
      return;
    }

    const waitForAuth = window.setInterval(() => {
      if (this.canLoadProtectedData()) {
        window.clearInterval(waitForAuth);
        this.loadProductos();
      }
    }, 250);
  }

  loadProductos(): void {
    this.loading.set(true);
    this.error.set(null);

    this.productoService.getAll().subscribe({
      next: productos => {
        this.productos.set(productos);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('No se pudo cargar el catalogo. Inicia sesion o revisa que el BFF este activo.');
        this.loading.set(false);
      }
    });
  }

  agregarProducto(producto: Producto): void {
    this.boleta.set(null);
    this.compra.update(items => {
      const existente = items.find(item => item.producto.id === producto.id);

      if (existente) {
        return items.map(item =>
          item.producto.id === producto.id
            ? { ...item, cantidad: Math.min(item.cantidad + 1, producto.stock) }
            : item
        );
      }

      return [...items, { producto, cantidad: 1 }];
    });
  }

  cambiarCantidad(productoId: number | undefined, cantidad: number): void {
    if (!productoId) {
      return;
    }

    this.boleta.set(null);
    this.compra.update(items =>
      items
        .map(item =>
          item.producto.id === productoId
            ? { ...item, cantidad: Math.min(Math.max(cantidad, 1), item.producto.stock) }
            : item
        )
        .filter(item => item.cantidad > 0)
    );
  }

  quitarProducto(productoId: number | undefined): void {
    this.boleta.set(null);
    this.compra.update(items => items.filter(item => item.producto.id !== productoId));
  }

  generarBoleta(): void {
    if (this.compra().length === 0) {
      return;
    }

    this.generating.set(true);
    this.error.set(null);

    this.productoService.generarBoleta(this.compra()).subscribe({
      next: boleta => {
        this.boleta.set(boleta);
        this.generating.set(false);
      },
      error: () => {
        this.error.set('No se pudo generar la boleta. Revisa stock, sesion y conexion con el BFF.');
        this.generating.set(false);
      }
    });
  }

  limpiarCompra(): void {
    this.compra.set([]);
    this.boleta.set(null);
  }

  cantidadEnCompra(productoId: number | undefined): number {
    return this.compra().find(item => item.producto.id === productoId)?.cantidad ?? 0;
  }

  private canLoadProtectedData(): boolean {
    return this.auth.isReady() && !!this.auth.currentUser() && !!this.auth.idToken();
  }
}
