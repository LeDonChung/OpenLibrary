import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/shared/models/Category';
import { CategoryService } from 'src/app/shared/services/category/category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit{
  categories: Category[] = []
  constructor(
    private router: Router,
    private categoryService: CategoryService
  ) {

  }
  ngOnInit(): void {
    this.categoryService.getAllByActivated().subscribe((res: any) => {
      this.categories = res.data;
    }, (errors: any) => {
      console.log(errors);
    });
  }
}
