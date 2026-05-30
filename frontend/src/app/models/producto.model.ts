export interface Producto {
  id?: number;
  nombre: string;
  categoria: string;
  precio: number;
  stock: number;
  activo: boolean;
}

export interface ItemCompra {
  producto: Producto;
  cantidad: number;
}

export interface DetalleBoleta {
  productoId: number;
  nombre: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

export interface Boleta {
  fecha: string;
  productos: DetalleBoleta[];
  total: number;
}
