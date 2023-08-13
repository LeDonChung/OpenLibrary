import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserComponent } from './modules/user/user.component';
import { AdminComponent } from './modules/admin/admin.component';
import { RouteGuardService } from './shared/services/guards/route-guard.service';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    loadChildren: () =>
      import('./modules/user/user.module').then((m) => m.UserModule),
   
  },
  {
    path: 'admin',
    component: AdminComponent,
    loadChildren: () =>
      import('./modules/admin/admin.module').then((m) => m.AdminModule),
      canActivate: [RouteGuardService],
      data: {
        expectedRole: ['ADMIN']
      }
    },
  { path: '**', component: UserComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
