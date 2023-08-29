import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UserComponent } from './components/user/user.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CategoryComponent } from './components/category/category.component';
import { PublisherComponent } from './components/publisher/publisher.component';
import { AuthorComponent } from './components/author/author.component';
import { BookComponent } from './components/book/book.component';
import { EditBookComponent } from './components/edit-book/edit-book.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { QuoteComponent } from './components/quote/quote.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'users', component: UserComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'categories', component: CategoryComponent },
  { path: 'publishers', component: PublisherComponent },
  { path: 'authors', component: AuthorComponent },
  { path: 'books', component: BookComponent },
  { path: 'quotes', component: QuoteComponent },
  { path: 'books/edit', component: EditBookComponent },
  { path: 'books/edit/:id', component: EditBookComponent },
  { path: 'books/view/:id', component: EditBookComponent },
  { path: 'feedbacks', component: FeedbackComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule { }
