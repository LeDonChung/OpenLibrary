import { FeedbackService } from './../../../../shared/services/feedback/feedback.service';
import { FeedBack } from './../../../../shared/models/FeedBack';
import { Component, OnInit } from '@angular/core';
import { Page } from 'src/app/shared/models/Page';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { FeedbackDialogComponent } from '../dialog/feedback-dialog/feedback-dialog.component';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit{
  
  public displayColumns: string[] = ["position", "name", "email", "status" ,"action"];
  public responseMessage: any;
  public page: Page<FeedBack> = new Page(0, 0, 5, []);

  constructor(
    private FeedbackService: FeedbackService,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private router: Router,
    private feedbackService: FeedbackService
  ) {}

  ngOnInit(): void {
    this.loadData(null);
  }
  loadData(event: any) {
    if(event === null) {
      this.page = new Page(0, 0, 5, []);
    } else {
      this.page = new Page(event.length, event.pageIndex, event.pageSize, []);
    }
    this.ngxService.start();
    this.FeedbackService.getPages(this.page).subscribe((res: any) => {
      this.ngxService.stop();
      this.page = res.data;
    }, (errors: any) => {
      this.ngxService.stop();
      let error = errors.error;
      if(error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
    });
  }
  onView(values: FeedBack) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(FeedbackDialogComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });
  }
  onSendResponseDefault(id: number) {
    this.ngxService.start();  
    let formData: FormData = new FormData();
    formData.append('defaultFeedback', 'true');
    this.feedbackService.sendEmail(id, formData).subscribe((res: any) => {
      this.ngxService.stop(); 
      this.loadData(this.page);
      this.responseMessage = res.body.data;
      this.snackbarService.open(this.responseMessage, 'success');
    }, (errors: any) => {
      this.ngxService.stop(); 
      let error = errors.error;
      if(error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
    });
  }
}
