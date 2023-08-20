import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/shared/models/Category';
import { CategoryService } from 'src/app/shared/services/category/category.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categories: Category[] = [];
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getAllByActivated().subscribe((res: any) => {
      this.categories = res.data;
    }, (errros: any) => {
      console.log(errros);
    })
  }

}
