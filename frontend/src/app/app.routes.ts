import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./components/producto-venta/producto-venta.component').then(m => m.ProductoVentaComponent)
  },
  { path: '**', redirectTo: '' }
];
