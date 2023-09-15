import { NgbDropdown, NgbDropdownModule, NgbModule, NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './user.component';
import { HeaderComponent } from './components/header/header.component';
import { UserRoutingModule } from './user-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { CarouselComponent } from './components/carousel/carousel.component';
import { LoginComponent } from 'src/app/components/login/login.component';
import { RegisterComponent } from 'src/app/components/register/register.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { CategoryListComponent } from './components/category-list/category-list.component';
import { BookDetailComponent } from './components/book-detail/book-detail.component';
import { TagsComponent } from './components/tags/tags.component';
import { FindResultComponent } from './components/find-result/find-result.component';
import { SanitizeHtmlPipe } from 'src/app/shared/pipe/sanitize-html.pipe';
import { QuoteComponent } from './components/quote/quote.component';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [
    SanitizeHtmlPipe,
    UserComponent,
    HeaderComponent,
    HomeComponent,
    FooterComponent,
    CarouselComponent,
    LoginComponent,
    RegisterComponent,
    BookListComponent,
    FindResultComponent,
    CategoryListComponent,
    CarouselComponent,
    BookDetailComponent,
    TagsComponent,
    QuoteComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    NgbPagination,
    NgSelectModule
  ]
})
export class UserModule { }
  