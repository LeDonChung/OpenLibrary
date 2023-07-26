import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from 'src/app/shared/models/User';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { UserService } from 'src/app/shared/services/user/user.service';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { Page } from 'src/app/shared/models/Page';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  public displayColumns: string[] = ["position", "fullName", "username", "image", "action"]
  public responeMessage: any;
  public page: Page<User> = new Page(0, 0, 5, []);
  public dataSource: any;
  constructor(
    private userService: UserService,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private router: Router) {

  }
  ngOnInit(): void {
    this.loadData(null);
  }

  loadData(event: any) {
    this.ngxService.start();
    
    if(event === null) {
      this.page = new Page(0, 0, 5, []);
    } else {
      this.page = new Page(event.length, event.pageIndex, event.pageSize, []);
    }
    this.userService.getAllUser(this.page).subscribe((res: any) => {
      this.ngxService.stop();
      this.page = res.data;
      console.log(this.page);
    }, (errors: any) => {
      console.log(errors);
      let error = errors.error;
      if (error.data) {
        this.responeMessage = error.data;
      } else {
        this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responeMessage, 'error');
    });
  }

  enable(user: User) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: ' bật ' + user.username,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    })

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.userService.enableById(user.id).subscribe((res: any) => {
        this.ngxService.stop();
        this.snackbarService.open(res.data, 'success');
        this.loadData(null);
      }, (errors: any) => {
        console.log(errors);
        let error = errors.error;
        if (error.data) {
          this.responeMessage = error.data;
        } else {
          this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
        }
        this.snackbarService.open(this.responeMessage, 'error');
      });
    });

  }
  disable(user: User) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: ' xóa ' + user.username,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    })

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.userService.disableById(user.id).subscribe((res: any) => {
        this.ngxService.stop();
        this.snackbarService.open(res.data, 'success');
        this.loadData(null);
      }, (errors: any) => {
        console.log(errors);
        let error = errors.error;
        if (error.data) {
          this.responeMessage = error.data;
        } else {
          this.responeMessage = SystemConstraints.SOMETHING_WENT_WRONG;
        }
        this.snackbarService.open(this.responeMessage, 'error');
      });
    });
  }
}
