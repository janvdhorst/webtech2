import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AngularComponent } from './angular/angular.component';
import { AuthComponent } from './auth/auth.component';
import { SecurityComponent } from './security/security.component';
import { NewLoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';

const routes: Routes = [
  { path: 'angular', component: AngularComponent },
  { path: 'auth', component: AuthComponent},
  { path: 'security', component: SecurityComponent },
  { path : 'login' , component: NewLoginComponent },
  { path : 'registration' , component: RegistrationComponent },
  { path: '',
    redirectTo: '/login' ,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
