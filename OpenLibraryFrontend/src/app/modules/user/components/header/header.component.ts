import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/shared/models/Category';
import { AuthService } from 'src/app/shared/services/auth.service';
import { BookService } from 'src/app/shared/services/book/book.service';
import { CategoryService } from 'src/app/shared/services/category/category.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isMenuCollapsed = true;
  categories: Category[] = [];
  selected: any = 'search_book';
  searchValue: any = '';
  isLogin: boolean = false;
  searchs: Object[] = [
    {name: 'Tác giả', value: 'search_author'},
    {name: 'Thể loại', value: 'search_category'},
    {name: 'Nhà xuất bản', value: 'search_publisher'},
    {name: 'Sách', value: 'search_book'},
  ]

  accounts: Object[] = ['Họ và tên', 'Cá nhân', 'Đăng xuất']

  constructor(
    private categoryService: CategoryService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.categoryService.getAllByActivated().subscribe((res: any) => {
      this.categories = res.data;
    }, (errors: any) => {
      console.log(errors);
    })

    this.isLogin = this.authService.isLoggedIn();
    console.log(this.isLogin);
  }

  search() {
    if(this.searchValue !== '' ) {
      window.location.href = `http://localhost:4200/result/${this.selected}/${this.searchValue}`;
    }
  }

  openProfile() {
    this.router.navigate(['/account/profile']);
  }

  logout() {
    this.authService.logout();
    this.isLogin = false;
  }

}
