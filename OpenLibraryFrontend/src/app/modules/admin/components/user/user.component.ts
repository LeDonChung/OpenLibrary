import { Component, OnInit } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from 'src/app/shared/models/User';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { UserService } from 'src/app/shared/services/user/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit{
  
  public displayColumns: string[] = ["position", "fullName", "username", "image", "action"]
  public dataSource: User[] =  [];
  public responeMessage: any;

  constructor(
    private userService: UserService,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService) {
    
  }
  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.ngxService.start();
    this.userService.getAllUser().subscribe((res: any) => {
      this.ngxService.stop();
      this.dataSource = res.data;
      
    }, (errors: any) => {
      console.log(errors);
      let error = errors.error;
      if(error.data) {
        this.responeMessage = error.data;
      } else {
        this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responeMessage, 'error');
    });
  }

  enable(id: number) {
    this.ngxService.start();
    this.userService.enableById(id).subscribe((res: any) => {
      this.ngxService.stop();
      this.snackbarService.open(res.data, 'success');
      this.loadData();
    }, (errors: any) => {
      console.log(errors);
      let error = errors.error;
      if(error.data) {
        this.responeMessage = error.data;
      } else {
        this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responeMessage, 'error');
    });
  }
  disable(id: number) {
    this.ngxService.start();
    this.userService.disableById(id).subscribe((res: any) => {
      this.ngxService.stop();
      this.snackbarService.open(res.data, 'success');
      this.loadData();
    }, (errors: any) => {
      console.log(errors);
      let error = errors.error;
      if(error.data) {
        this.responeMessage = error.data;
      } else {
        this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responeMessage, 'error');
    });
  }
}
