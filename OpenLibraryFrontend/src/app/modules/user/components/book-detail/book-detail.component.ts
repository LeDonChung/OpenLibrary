import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLinkActive } from '@angular/router';
import { Author } from 'src/app/shared/models/Author';
import { Book } from 'src/app/shared/models/Book';
import { BookService } from 'src/app/shared/services/book/book.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.scss']
})
export class BookDetailComponent implements OnInit{
  public book!: Book;
  constructor(
    private bookService: BookService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if(params['id']) {
        this.bookService.getById(params['id']).subscribe((res: any) => {
          this.book = res.data;
          console.log(res.data);
        }, (errors: any) => {
          console.log(errors);
        })
      }
    });
  }
}
