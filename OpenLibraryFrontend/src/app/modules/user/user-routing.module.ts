import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  {path: '', redirectTo: '/user/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
