import {ExtraOptions, Routes} from '@angular/router';
import {AuthGuard} from './service/AuthGuard';

export const routes: Routes = [
  {
    path: '', canActivate : [AuthGuard],
    loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./components/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'signup',
    loadComponent: () => import('./components/signup/signup.component').then(m => m.SignupComponent)
  }



];

const routerOptions: ExtraOptions = {
  scrollPositionRestoration: 'enabled'
};
