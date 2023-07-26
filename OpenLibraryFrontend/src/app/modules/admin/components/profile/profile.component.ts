import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from 'src/app/shared/models/User';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { UserService } from 'src/app/shared/services/user/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit{
  public userForm: any = FormGroup;
  public user: User = new User();
  public imgUrl: string = "";
  public isImg: boolean = false;
  public responeMessage:string = "";
  public uploadImage!: File;
  constructor(
    private userService: UserService,
    private snackbarService: SnackbarService,
    private formBuilder: FormBuilder,
    private ngxService: NgxUiLoaderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      fullName: ['', [Validators.required, Validators.pattern('^[A-Za-z ]{5,}$')]],
      phoneNumber: [''],
      email: ['']
    });

    this.userService.getCurrentUser().subscribe((res: any) => {
      this.userForm = this.formBuilder.group(res.data);
      this.imgUrl = res.data.image;
      this.isImg = true;
    }); 

  }
  onUpdateInformation () { 
    this.ngxService.start();
    let user: User = this.userForm.value;
    this.userService.update(user).subscribe((res: any) => {
      this.ngxService.stop();
      this.responeMessage = res.data;
      this.snackbarService.open(this.responeMessage, 'success');
      window.location.reload();
    }, (errors: any) => {
      console.log(errors);
      this.ngxService.stop();
      let message = errors.error.data;
      if(message) {
        this.responeMessage = message;
      } else {
        this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responeMessage, 'error');
    });
  }

  onUploadImageUser() {
    const imageFormData = new FormData();
    imageFormData.append('image', this.uploadImage, this.uploadImage.name);
    this.ngxService.start();
    this.userService.uploadImage(this.userForm.controls.id.value, imageFormData).subscribe((res: any) => {
      this.ngxService.stop();
      this.responeMessage = res.data;
      this.snackbarService.open(this.responeMessage, 'success');
      window.location.reload();
    }, (errors: any) => {
      console.log(errors);
      this.ngxService.stop();
      let message = errors.error.data;
      if(message) {
        this.responeMessage = message;
      } else {
        this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responeMessage, 'error');
    });
  }
  onViewImage(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (event: any) => {
          this.imgUrl = event.target.result;
      }
      reader.readAsDataURL(event.target.files[0]);
      this.uploadImage = event.target.files[0];
      this.isImg = false;
    } 
  }
}



