import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/shared/models/Category';
import { CategoryService } from 'src/app/shared/services/category/category.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isMenuCollapsed = true;
  categories: Category[] = [];
  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.categoryService.getAllByActivated().subscribe((res: any) => {
      this.categories = res.data;
    }, (errors: any) => {
      console.log(errors);
    })
  }

}
