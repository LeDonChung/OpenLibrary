import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from 'src/app/shared/models/User';
import { UserService } from 'src/app/shared/services/user/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit{
  public registerForm:any = FormGroup;
  public message: string = "";
  constructor(
    private formBuider: FormBuilder,
    private userService: UserService,
    private router: Router,
    private spinnerService: NgxSpinnerService) {}

  ngOnInit(): void {
    this.createForm()
  }

  createForm() {
    this.registerForm = this.formBuider.group({
      fullName: [null, [Validators.required, Validators.pattern('^[A-Za-z ]{5,}$')]],
      username: [null, [Validators.required]],
      password: [null, [Validators.required, , Validators.pattern('^[A-Za-z0-9]{6,}$')]],
      confirmPassword: [null, [Validators.required]] 
    });
  }

  invalidUsername() {
    let username:string = this.registerForm.controls.username.value;
    if(username.length > 0) {
      if(username.includes('@')) {
        let regexEmail = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
        if(!regexEmail.test(username)) {
          return true;
        }
        return false;
      } else {
        let regexPhone = /^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$/;
        if(!regexPhone.test(username)) {
          return true; 
        }
        return false;
      }
    }
    return true;
  }

  invalidConfirmPassword() {
    if(this.registerForm.controls.password.value != this.registerForm.controls.confirmPassword.value) {
      return true;
    }
    return false;
  }
  onSubmit() {
    this.spinnerService.show()
    let data: User = this.registerForm.value;
    this.userService.register(data).subscribe((res: any) => {
      this.spinnerService.hide();
      Swal.fire({
        title: 'Đăng ký thành công.',
        icon: 'success',
        showCancelButton: false,
        confirmButtonText: 'Đăng nhập',
      }).then((result) => {
        if (result.value) {
          this.router.navigateByUrl('/login');
        } else if (result.dismiss === Swal.DismissReason.cancel) {
        }
      });
      
    }, (errors: any) => {
      this.spinnerService.hide();
      if(errors.error.data) {
        this.message = errors.error.data;
      } else {
        this.message = SystemConstraints.SOMETHING_WENT_WRONG;
      }
    });

  }
}
