import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { ChangePassword } from 'src/app/shared/models/ChangePassword';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit{
  public changePasswordForm: any = FormGroup;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}
  ngOnInit(): void { 
    this.changePasswordForm = this.formBuilder.group({
      passwordNew: [null, [Validators.required, Validators.pattern('^[A-Za-z0-9]{6,}$')]],
      passwordOld: [null, [Validators.required]],
      passwordRe: [null, [Validators.required]],
      username: [this.authService.getUserCurrent()]
    });
    console.log(this.changePasswordForm)
  }

  
  validationSubmit() {
    if(this.changePasswordForm.controls['passwordNew'].value != this.changePasswordForm.controls['passwordRe'].value) {
      return true;
    } else {
      return false;
    }
  }

  onSubmit() {
    let data: ChangePassword = this.changePasswordForm.value as ChangePassword;
    this.userService.changePassword(data).subscribe((res: any) => {
      Swal.fire({
        title: 'Đổi mật khẩu',
        text: `${res.data}`,
        icon: 'info',
        showCancelButton: false,
        confirmButtonText: 'Xác nhận.',
      }).then((result) => {
        if (result.value) {
          window.location.href = 'http://localhost:4200/account/change-password';
        } else if (result.dismiss === Swal.DismissReason.cancel) {
        }
      });

    }, (errors: any) => {
      let error = errors.error;
      let responseMessage = '';
      if (error) {
        responseMessage = error.data;
      } else {
        responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      Swal.fire('Đổi mật khẩu', responseMessage, 'error');
    });
  }

}
