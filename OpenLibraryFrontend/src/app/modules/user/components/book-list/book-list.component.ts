import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit{
  title?: string;
  @Input()
  type?: string = 'all';
  page!: number;
  constructor() { 
    this.page = 1;
  }
  ngOnInit(): void {
    this.title = 'Tất cả sách';
    this.type = 'all';
  }

  onChange(event: any) {
    console.log(this.page);
  }
}
