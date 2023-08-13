import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent {
  @Input()
  authors?: any;
  @Input()
  publisher: any;
  @Input()
  categories: any;
  @Input()
  tagBy: any;
}
