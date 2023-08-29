import { Component, EventEmitter, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { QuoteService } from 'src/app/shared/services/quote/quote.service';
import { QuoteComponent } from '../../quote/quote.component';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { Quote } from 'src/app/shared/models/Quote';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';

@Component({
  selector: 'app-quote-dialog',
  templateUrl: './quote-dialog.component.html',
  styleUrls: ['./quote-dialog.component.scss']
})
export class QuoteDialogComponent {
  onAddQuote = new EventEmitter();
  onUpdateQuote = new EventEmitter();
  quoteForm: any = FormGroup;
  dialogAction: any = 'add';
  action: any = 'Thêm';
  responseMessage: any;
  
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public dialogData: any,
    private formBuilder: FormBuilder,
    private quoteService: QuoteService,
    public dialogRef: MatDialogRef<QuoteComponent>,
    private snackbarService: SnackbarService

  ) {}
  ngOnInit(): void {
    this.quoteForm = this.formBuilder.group({
      id: [null],
      content: [null, [Validators.required]],
      likes: [0]
    });
    if(this.dialogData.action === 'edit') {
      this.action = 'Chỉnh sửa';
      this.dialogAction = 'edit';
      this.quoteForm.patchValue(this.dialogData.data);
    } else if(this.dialogData.action === 'view') {
      this.action = 'Hiển thị';
      this.dialogAction = 'view';
      this.quoteForm.patchValue(this.dialogData.data);
    }
  }
  handlerSubmit() {
    if(this.dialogAction === 'edit') {
      this.edit();
    } else {
      this.add();
    }
  }
  add() {
    let data: Quote = this.quoteForm.value;
    this.quoteService.insertOne(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onAddQuote.emit();
      this.responseMessage = res.body.data;
      this.snackbarService.open(this.responseMessage, 'success');
    }, (errors: any) => {
      let error = errors.error;
      if(error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
    });
  }
  edit() {
    let data: Quote = this.quoteForm.value;
    this.quoteService.updateOne(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onUpdateQuote.emit();
      this.responseMessage = res.body.data;
      this.snackbarService.open(this.responseMessage, 'success');
    }, (errors: any) => {
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
