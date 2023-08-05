import { SnackbarService } from './../../../../../shared/services/snackbar/snackbar.service';
import { CategoryService } from 'src/app/shared/services/category/category.service';
import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryComponent } from '../../category/category.component';
import { Category } from 'src/app/shared/models/Category';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';

@Component({
  selector: 'app-category-dialog',
  templateUrl: './category-dialog.component.html',
  styleUrls: ['./category-dialog.component.scss']
})
export class CategoryDialogComponent implements OnInit{
  onAddCategory = new EventEmitter();
  onUpdateCategory = new EventEmitter();
  categoryForm: any = FormGroup;
  dialogAction: any = 'add';
  action: any = 'Thêm';
  responseMessage: any;
  
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public dialogData: any,
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    public dialogRef: MatDialogRef<CategoryComponent>,
    private snackbarService: SnackbarService

  ) {}
  ngOnInit(): void {
    this.categoryForm = this.formBuilder.group({
      id: [null],
      name: [null, [Validators.required]],
      code: [null, [Validators.required]]
    });
    if(this.dialogData.action === 'edit') {
      this.action = 'Chỉnh sửa';
      this.dialogAction = 'edit';
      this.categoryForm.patchValue(this.dialogData.data);
    } else if(this.dialogData.action === 'view') {
      this.action = 'Hiển thị';
      this.dialogAction = 'view';
      this.categoryForm.patchValue(this.dialogData.data);
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
    let data: Category = this.categoryForm.value;
    this.categoryService.insertOne(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onAddCategory.emit();
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
    let data: Category = this.categoryForm.value;
    this.categoryService.updateOne(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onUpdateCategory.emit();
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
