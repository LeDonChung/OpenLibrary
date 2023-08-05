import { Component, EventEmitter, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PublisherService } from 'src/app/shared/services/publisher/publisher.service';
import { PublisherComponent } from '../../publisher/publisher.component';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { Publisher } from 'src/app/shared/models/Publisher';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'app-publisher-dialog',
  templateUrl: './publisher-dialog.component.html',
  styleUrls: ['./publisher-dialog.component.scss']
})
export class PublisherDialogComponent {
  onAddPublisher = new EventEmitter();
  onUpdatePublisher = new EventEmitter();
  publisherForm: any = FormGroup;
  dialogAction: any = 'add';
  action: any = 'Thêm';
  responseMessage: any;
  editor = ClassicEditor;
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public dialogData: any,
    private formBuilder: FormBuilder,
    private publisherService: PublisherService,
    public dialogRef: MatDialogRef<PublisherComponent>,
    private snackbarService: SnackbarService

  ) {}

  ngOnInit(): void {
    this.publisherForm = this.formBuilder.group({
      id: [null],
      name: [null, [Validators.required]],
      address: [null, [Validators.required]],
      story: [null, [Validators.required]],
    });
    if(this.dialogData.action === 'edit') {
      this.action = 'Chỉnh sửa';
      this.dialogAction = 'edit';
      this.publisherForm.patchValue(this.dialogData.data);
    } else if(this.dialogData.action === 'view') {
      this.action = 'Hiển thị';
      this.dialogAction = 'view';
      this.publisherForm.patchValue(this.dialogData.data);
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
    let data: Publisher = this.publisherForm.value;
    this.publisherService.insertOne(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onAddPublisher.emit();
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
    let data: Publisher = this.publisherForm.value;
    this.publisherService.updateOne(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onUpdatePublisher.emit();
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
