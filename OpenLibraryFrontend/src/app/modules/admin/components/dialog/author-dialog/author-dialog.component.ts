import { Component, EventEmitter, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AuthorService } from 'src/app/shared/services/author/author.service';
import { AuthorComponent } from '../../author/author.component';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Author } from 'src/app/shared/models/Author';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'app-author-dialog',
  templateUrl: './author-dialog.component.html',
  styleUrls: ['./author-dialog.component.scss']
})
export class AuthorDialogComponent {
  onAddAuthor = new EventEmitter();
  onUpdateAuthor = new EventEmitter();
  authorForm: any = FormGroup;
  dialogAction: any = 'add';
  action: any = 'Thêm';
  responseMessage: any;
  editor = ClassicEditor;
  public imgUrl: string = "";
  public isImg: boolean = false;
  public uploadImage!: File;
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public dialogData: any,
    private formBuilder: FormBuilder,
    private authorService: AuthorService,
    public dialogRef: MatDialogRef<AuthorComponent>,
    private snackbarService: SnackbarService

  ) { }
  ngOnInit(): void {
    this.authorForm = this.formBuilder.group({
      id: [null],
      fullName: [null, [Validators.required]],
      story: ['Tiểu sử', [Validators.required]]
    });
    if (this.dialogData.action === 'edit') {
      this.action = 'Chỉnh sửa';
      this.dialogAction = 'edit';
      this.authorForm.patchValue(this.dialogData.data);
      this.isImg = true;
    } else if (this.dialogData.action === 'view') {
      this.action = 'Hiển thị';
      this.dialogAction = 'view';
      this.authorForm.patchValue(this.dialogData.data);
      this.isImg = true;

    }
  }
  handlerSubmit() {
    if (this.dialogAction === 'edit') {
      this.edit();
    } else {
      this.add();
    }
  }
  add() {
    const formData = new FormData();
    let data: Author = this.authorForm.value;
    formData.append('image', this.uploadImage !== undefined ? this.uploadImage : '');
    formData.append('authorDto', JSON.stringify(data));
    this.authorService.insertOne(formData).subscribe((res: any) => {
      this.dialogRef.close();
      this.responseMessage = res.body.data;
      this.onAddAuthor.emit();
      this.snackbarService.open(this.responseMessage, 'success');

    }, (errors: any) => {
      let error = errors.error;
      if (error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
    });
  }
  edit() {
    const formData = new FormData();
    let data: Author = this.authorForm.value;
    formData.append('image', this.uploadImage !== undefined ? this.uploadImage : '');
    formData.append('authorDto', JSON.stringify(data));
    this.authorService.updateOne(formData).subscribe((res: any) => {
      this.dialogRef.close();
      this.onUpdateAuthor.emit();
      this.responseMessage = res.body.data;
      this.snackbarService.open(this.responseMessage, 'success');
    }, (errors: any) => {
      let error = errors.error;
      if (error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
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
