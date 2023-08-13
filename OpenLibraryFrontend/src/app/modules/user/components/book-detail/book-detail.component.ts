import { Component } from '@angular/core';
import { Author } from 'src/app/shared/models/Author';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.scss']
})
export class BookDetailComponent {
  authors: any = [
    {
      id: 1,
      fullName: 'Lê Đôn Chủng', 
      story: 'sssss'
    },
    {
      id: 2,
      fullName: 'Thanh Tuyền', 
      story: 'sssss'
    },
    {
      id: 3,
      fullName: 'Thanh thảo', 
      story: 'sssss'
    },
  ]
}
