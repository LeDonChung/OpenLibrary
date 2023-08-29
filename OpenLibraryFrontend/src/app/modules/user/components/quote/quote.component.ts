import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Quote } from 'src/app/shared/models/Quote';
import { AuthService } from 'src/app/shared/services/auth.service';
import { QuoteService } from 'src/app/shared/services/quote/quote.service';
import { UserService } from 'src/app/shared/services/user/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-quote',
  templateUrl: './quote.component.html',
  styleUrls: ['./quote.component.scss']
})
export class QuoteComponent implements OnInit{
  public quote: Quote = new Quote();
  public quotes: Quote[] = [];
  public isLiked: boolean = false;
  constructor(
    private quoteService: QuoteService,
    private authService: AuthService,
    private router: Router,
    private userService: UserService) {}
  ngOnInit(): void {
    this.quoteService.getRand().subscribe((res: any) => {
      this.quote = res.data;
      this.existsQuote();
    }, (errors: any) => {
      console.log(errors);
    });
  }

  existsQuote() {
    if(this.authService.isLoggedIn()) {
      this.userService.existsQuote(this.quote.id).subscribe((res: any) => {
        this.isLiked = res.data;
      }, (errors: any) => {
        this.isLiked = false;
        console.log(errors);
      })
    }
  }

  onUnlike() {
    let isLogin: boolean = this.authService.isLoggedIn();
    if(!isLogin) {
      this.router.navigate(['/login']);
    } else {
      Swal.fire({
        title: 'Yêu thích',
        text: 'Bạn có chắc xóa khỏi danh sách yêu thích?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Đồng ý.',
        cancelButtonText: 'Hủy',
      }).then((result) => {
        if (result.value) {
          this.quoteService.unlikeByid(this.quote.id).subscribe((res: any) => {
            Swal.fire('Yêu thích', res.data, 'success');
            this.isLiked = false;
          }, (errors: any) => {
            Swal.fire('Yêu thích', errors.error.data, 'error');
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          
        }
      });
      
    }
  }
  onLike() {
    let isLogin: boolean = this.authService.isLoggedIn();
    if(!isLogin) {
      this.router.navigate(['/login']);
    } else {
      Swal.fire({
        title: 'Yêu thích',
        text: 'Bạn có chắc thêm vào danh sách yêu thích?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Đồng ý.',
        cancelButtonText: 'Hủy',
      }).then((result) => {
        if (result.value) {
          this.quoteService.likeByid(this.quote.id).subscribe((res: any) => {
            Swal.fire('Yêu thích', res.data, 'success');
            this.isLiked = true;
          }, (errors: any) => {
            Swal.fire('Yêu thích', errors.error.data, 'error');
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          
        }
      });
      
    }
  }
}
