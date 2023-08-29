import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { UserComponent } from './components/user/user.component';
import { NgxUiLoaderConfig, NgxUiLoaderModule, SPINNER } from 'ngx-ui-loader';
import { ConfirmationComponent } from './components/dialog/confirmation/confirmation.component';
import { MatDialogModule } from '@angular/material/dialog';
import {MatChipsModule} from '@angular/material/chips';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ProfileComponent } from './components/profile/profile.component';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CategoryComponent } from './components/category/category.component';
import { CategoryDialogComponent } from './components/dialog/category-dialog/category-dialog.component';
import { PublisherDialogComponent } from './components/dialog/publisher-dialog/publisher-dialog.component';
import { PublisherComponent } from './components/publisher/publisher.component';
import { AuthorComponent } from './components/author/author.component';
import { AuthorDialogComponent } from './components/dialog/author-dialog/author-dialog.component';
import { BookComponent } from './components/book/book.component';
import { EditBookComponent } from './components/edit-book/edit-book.component';
import { MatSelectModule } from '@angular/material/select';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { FeedbackDialogComponent } from './components/dialog/feedback-dialog/feedback-dialog.component';
import { CKEditorModule } from 'ng2-ckeditor';
import { QuoteComponent } from './components/quote/quote.component';
import { QuoteDialogComponent } from './components/dialog/quote-dialog/quote-dialog.component';
import { SanitizeHtmlPipe } from 'src/app/shared/pipe/sanitize-html.pipe';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  text: 'Loading',
  textColor: '#FFFFFF',
  textPosition: 'center-center',
  bgsColor: '#7b1fa2',
  fgsColor: '#7b1fa2',
  fgsType: SPINNER.squareJellyBox,
  fgsSize: 100,
  hasProgressBar: false
}

@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    HeaderComponent,
    FooterComponent,
    HeaderComponent,
    SidenavComponent,
    UserComponent,
    ConfirmationComponent,
    ProfileComponent,
    CategoryComponent,
    CategoryDialogComponent,
    PublisherDialogComponent,
    PublisherComponent,
    AuthorComponent,
    AuthorDialogComponent,
    BookComponent,
    EditBookComponent,
    FeedbackComponent,
    FeedbackDialogComponent,
    QuoteComponent,
    QuoteDialogComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    // * MATERIAL IMPORTS
    MatSidenavModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatButtonModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule,
    MatSelectModule,
    MatOptionModule,
    MatDialogModule,
    MatTableModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
    CKEditorModule
  ]
})
export class AdminModule { }
