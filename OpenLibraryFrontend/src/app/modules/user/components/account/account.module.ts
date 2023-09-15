import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountComponent } from './account.component';
import { AccountRoutingModule } from './account-routing.module';
import { ProfileComponent } from './components/profile/profile.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { BookFavoriteComponent } from './components/book-favorite/book-favorite.component';
import { QuoteFavoriteComponent } from './components/quote-favorite/quote-favorite.component';



@NgModule({
  declarations: [
    AccountComponent,
    ProfileComponent,
    ChangePasswordComponent,
    BookFavoriteComponent,
    QuoteFavoriteComponent
  ],
  imports: [
    AccountRoutingModule,
    CommonModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AccountModule { }
