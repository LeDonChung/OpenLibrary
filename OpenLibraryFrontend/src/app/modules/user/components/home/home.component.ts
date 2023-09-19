import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Category } from 'src/app/shared/models/Category';
import { CategoryService } from 'src/app/shared/services/category/category.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categories: Category[] = [];
  constructor(private categoryService: CategoryService,
    private spinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.spinnerService.show();
    this.categoryService.getAllByActivated().subscribe((res: any) => {
      this.categories = res.data;
      this.spinnerService.hide();
    }, (errros: any) => {
      console.log(errros);
      this.spinnerService.hide();
    })
  }

}
