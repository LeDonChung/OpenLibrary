import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/User';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user/user.service';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Observable } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit{
  public imgUrl: any = "https://soklyphone.com/img/avatar.jpeg";
  public isImg: boolean = false;
  public uploadImage!: File;
  public user!: User;
  public userForm: any = FormGroup;
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {

  }
  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      id: [null],
      fullName: [null, [Validators.required, Validators.pattern('^[A-Za-z ]{5,}$')]],
      email: [null, [Validators.pattern(SystemConstraints.REGEX_EMAIL)]],
      phoneNumber: [null, [Validators.pattern(SystemConstraints.REGEX_PHONE)]]
    });
    this.userService.getCurrentUser().subscribe((res: any) => {
      this.user = res.data;
      
      if(this.user.image != null) {
        this.isImg = true;
      }
      this.userForm.patchValue(this.user);
    },(errors: any) => { 
      console.log(errors);
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

  updateProfile() {
    const formData = new FormData();
    let data: User = this.userForm.value;
    formData.append('image', this.uploadImage !== undefined ? this.uploadImage : '');
    formData.append('userDto', JSON.stringify(data));
    this.userService.updateProfile(formData).subscribe((res: any) => {

      Swal.fire({
        title: 'Thông tin cá nhân',
        text: `${res.body.data}`,
        icon: 'info',
        showCancelButton: false,
        confirmButtonText: 'Xác nhận.',
      }).then((result) => {
        if (result.value) {
          window.location.href = 'http://localhost:4200/account/profile';
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
      Swal.fire('Thông tin cá nhân', responseMessage, 'error');
    });
  }

}
