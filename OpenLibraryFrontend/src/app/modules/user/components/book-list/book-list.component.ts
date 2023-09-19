import { NgxSpinnerService } from 'ngx-spinner';
import { Component, Input, OnInit } from '@angular/core';
import { Author } from 'src/app/shared/models/Author';
import { Book } from 'src/app/shared/models/Book';
import { Category } from 'src/app/shared/models/Category';
import { Page } from 'src/app/shared/models/Page';
import { Publisher } from 'src/app/shared/models/Publisher';
import { Sorter } from 'src/app/shared/models/Sorter';
import { AuthorService } from 'src/app/shared/services/author/author.service';
import { BookService } from 'src/app/shared/services/book/book.service';
import { CategoryService } from 'src/app/shared/services/category/category.service';
import { PublisherService } from 'src/app/shared/services/publisher/publisher.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit{
  @Input()
  value?: any; // value

  @Input()
  type?: any; // category, author, pulisher

  title?: any;

  page: Page<Book> = new Page(0, 0, 12, []);
  
  constructor(
    private categoryService: CategoryService,
    private authorService: AuthorService,
    private publisherService: PublisherService,
    private bookService: BookService,
    private ngxSpinnerService: NgxSpinnerService
  ) { 
    
  }
  
  ngOnInit() {
    this.ngxSpinnerService.show();
    this.getTitle();
    this.loadData(null);
    this.ngxSpinnerService.hide();
  }
  getTitle() {
    // kiểm tra trường hợp tìm tất cả.
    if(this.value?.code == 'all') {
      this.title = 'Tất cả sách';
    } else {
      if(this.type === 'category') {
        this.categoryService.getByCode(this.value).subscribe((res: any) => {
          this.title = (res.data as Category).name;
        }, (errors: any) => {
          console.log(errors);
        });
      } else if(this.type === 'author') {
        this.authorService.getById(this.value).subscribe((res: any) => {
          this.title = (res.data as Author).fullName;
        }, (errors: any) => {
          console.log(errors);
        });
      } else if(this.type === 'publisher') {
        this.publisherService.getById(this.value).subscribe((res: any) => {
          this.title = (res.data as Publisher).name;
        }, (errors: any) => {
          console.log(errors);
        });
      } else {
        this.title = "Tất cả tìm kiếm cho: " + this.value;
      }
    }
  }
  loadData(event: any) {
    if(event === null) {
      this.page = new Page(0, 1, 12, [], new Sorter('title', 'asc'));
    } else {
      this.page.pageIndex = event;
    }
    this.bookService.getPagesByTypeAndValue(this.page, this.type, this.value).subscribe((res: any) => {
      this.page = res.data;
    }, (errors: any) => {
      console.log(errors);
    });
  }

  onChange(event: any) { 
    this.loadData(event);
  }
}
