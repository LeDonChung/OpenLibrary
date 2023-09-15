import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './components/profile/profile.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { BookFavoriteComponent } from './components/book-favorite/book-favorite.component';
import { QuoteFavoriteComponent } from './components/quote-favorite/quote-favorite.component';

const routes: Routes = [
  {path: 'profile', component: ProfileComponent},
  {path: 'change-password', component: ChangePasswordComponent},
  {path: 'book-favorite', component: BookFavoriteComponent},
  {path: 'quote-favorite', component: QuoteFavoriteComponent},
  {path: '**', redirectTo: 'profile', pathMatch: 'full'}
];
  
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
