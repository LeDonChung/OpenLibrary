import { NgxSpinnerService } from 'ngx-spinner';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/shared/models/User';
import { UserService } from 'src/app/shared/services/user/user.service';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public loginForm:any = FormGroup;
  public message: string = "";
  constructor(
    private spinnerService: NgxSpinnerService,
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.createLoginForm();
  }
  createLoginForm() {
    this.loginForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
  }
  onSubmit() {
    this.spinnerService.show();
    let user: User = this.loginForm.value;
    this.userService.login(user).subscribe((res: any) => {
      this.spinnerService.hide();
      this.authService.setToken(res.data); 
      this.router.navigate(['/admin/dashboard']);
    }, (errors: any) => {
      this.spinnerService.hide();
      if(errors.error.data) {
        this.message = errors.error.data;
      } else {
        this.message = SystemConstraints.SOMETHING_WENT_WRONG
      }
    });
  }

}
