import { Component } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.scss']
})
export class CarouselComponent {
  title?: string;
  images = [
    'https://firebasestorage.googleapis.com/v0/b/openlibrary-d2a93.appspot.com/o/carousel%2Ffeature-vn.jpg?alt=media&token=5c7e9ded-f4a6-4b0d-97d4-6f047f5a0fa5',
    'https://firebasestorage.googleapis.com/v0/b/openlibrary-d2a93.appspot.com/o/carousel%2Ffeature-vn.jpg?alt=media&token=5c7e9ded-f4a6-4b0d-97d4-6f047f5a0fa5',
    'https://firebasestorage.googleapis.com/v0/b/openlibrary-d2a93.appspot.com/o/carousel%2Ffeature-vn.jpg?alt=media&token=5c7e9ded-f4a6-4b0d-97d4-6f047f5a0fa5'
  ]
  constructor(config: NgbCarouselConfig) {
		// customize default values of carousels used by this component tree
		config.interval = 3000;
		config.wrap = true;
		config.keyboard = false;
		config.pauseOnHover = false;
	}
}
