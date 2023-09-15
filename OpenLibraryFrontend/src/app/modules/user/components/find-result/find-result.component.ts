import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-find-result',
  templateUrl: './find-result.component.html',
  styleUrls: ['./find-result.component.scss'] 
})
export class FindResultComponent implements OnInit{
  type?: any;
  value?: any;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {
    
  }

  ngOnInit(): void {
    if(this.router.url.includes('result')) {
      this.route.params.subscribe(params => {
        let searchBy =  params['searchBy'];
        let searchValue = params['searchValue'];
        this.type = searchBy;
        this.value = searchValue; 
      });
    } else if(this.router.url.includes('category')) {
      this.route.params.subscribe(params => {
        if(params['code'] && params['code'] != 'all') {
          this.type = 'category';
          this.value = params['code'];
        }
      });
    } else if(this.router.url.includes('author')) {
      this.route.params.subscribe(params => {
        if(params['id'] && params['id'] != 'all') {
          this.type = 'author';
          this.value = params['id'];
        }
      });
    } else if(this.router.url.includes('publisher')) {
      this.route.params.subscribe(params => {
        if(params['id'] && params['id'] != 'all') {
          this.type = 'publisher';
          this.value = params['id'];
        }
      }); 
    } 
  }
}


