import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './service/AuthGuard';
import {GuestGuard} from './service/GuestGuard';

export const routes: Routes = [
  {
    path: 'timeline', canActivate : [AuthGuard],
    loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'timeline/videos', canActivate : [AuthGuard],
    loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'profile/:id', canActivate : [AuthGuard],
    loadComponent: () => import('./components/user-profile/user-profile.component').then(m => m.UserProfileComponent)
  },


  {
    path: 'timeline/album', canActivate : [AuthGuard],
    loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'login', canActivate : [GuestGuard],
    loadComponent: () => import('./components/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'signup', canActivate : [GuestGuard],
    loadComponent: () => import('./components/signup/signup.component').then(m => m.SignupComponent)
  },
  {
    path: 'contact-info', canActivate: [AuthGuard],
    loadComponent: () => import('./components/contact-info/contact-info.component').then(m => m.ContactInfoComponent)
  },
  {
    path: '**',
    redirectTo: 'timeline'
  }
];



RouterModule.forRoot(routes, {
  scrollPositionRestoration: 'enabled'  // دي بتخلي الصفحة ترجع لفوق
})

