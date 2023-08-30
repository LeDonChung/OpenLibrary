import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from 'src/app/components/login/login.component';
import { RegisterComponent } from 'src/app/components/register/register.component';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import { FindResultComponent } from './components/find-result/find-result.component';

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
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
