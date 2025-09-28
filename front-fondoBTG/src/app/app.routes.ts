import { Routes } from '@angular/router';
import { MenuComponent } from './shared/menu/menu.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HistorialComponent } from './pages/historial/historial.component';

export const routes: Routes = [
  {
    path: '',
    component: MenuComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'historial', component: HistorialComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' } // redirige al dashboard
    ]
  }
];