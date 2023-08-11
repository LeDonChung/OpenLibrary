import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FeedbackComponent } from '../../feedback/feedback.component';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { FeedbackService } from 'src/app/shared/services/feedback/feedback.service';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';

@Component({
  selector: 'app-feedback-dialog',
  templateUrl: './feedback-dialog.component.html',
  styleUrls: ['./feedback-dialog.component.scss']
})
export class FeedbackDialogComponent implements OnInit{
  
  public feedbackForm!: any;
  public responseMessage: any;
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public dialogData: any,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<FeedbackComponent>,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService,
    private feedbackService: FeedbackService
  ) {}
  ngOnInit(): void {
    this.feedbackForm = this.formBuilder.group({
      id: [null],
      name: [null],
      email: [null],
      message: [null],
      responseMessage: [null, [Validators.required]],
      status: [null]
    });
    this.feedbackForm.patchValue(this.dialogData.data);
  }

  onSendResponse() {
    this.ngxService.start(); 
    let id: number = parseInt(this.feedbackForm.controls.id.value);
    let formData: FormData = new FormData();
    formData.append('content', this.feedbackForm.controls.responseMessage.value);
    formData.append('defaultFeedback', 'false');
    this.feedbackService.sendEmail(id, formData).subscribe((res: any) => {
      this.ngxService.stop(); 
      this.dialogRef.close();
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
