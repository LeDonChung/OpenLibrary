import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from 'src/app/components/login/login.component';
import { RegisterComponent } from 'src/app/components/register/register.component';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import { FindResultComponent } from './components/find-result/find-result.component';
import { AccountComponent } from './components/account/account.component';
import { RouteGuardService } from 'src/app/shared/services/guards/route-guard.service';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent },
  {path: 'category/:code', component: FindResultComponent},
  {path: 'author/:id', component: FindResultComponent},
  {path: 'publisher/:id', component: FindResultComponent},
  {path: 'book/:id', component: BookDetailComponent},
  {path: 'login', component: LoginComponent},
  {path: 'result/:searchBy/:searchValue', component: FindResultComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: 'account',
    component: AccountComponent,
    loadChildren: () =>
      import('./components/account/account.module').then((m) => m.AccountModule),
      canActivate: [RouteGuardService],
      data: {
        expectedRole: ['CUSTOMER']
      }
  },
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
