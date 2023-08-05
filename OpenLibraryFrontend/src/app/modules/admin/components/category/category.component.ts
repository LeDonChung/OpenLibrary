import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from './../../../../shared/services/snackbar/snackbar.service';
import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/shared/services/category/category.service';
import { Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Category } from 'src/app/shared/models/Category';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { Page } from 'src/app/shared/models/Page';
import { CategoryDialogComponent } from '../dialog/category-dialog/category-dialog.component';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit{
  public displayColumns: string[] = ["position", "name", "code", "action"];
  public responseMessage: any;
  public page: Page<Category> = new Page(0, 0, 5, []);
  constructor(
    private categoryService: CategoryService,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService,
    private router: Router,
    private dialog: MatDialog
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
    this.categoryService.getPages(this.page).subscribe((res: any) => {
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
  enable(category: Category) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " bật " + category.name,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.categoryService.enableById(category.id).subscribe((res: any) => {
        this.ngxService.stop();
        this.snackbarService.open(res.data, 'success');
        this.loadData(this.page);
      }, (errors: any) => {
        let error = errors.error;
        if (error) {
          this.responseMessage = error.data;
        } else {
          this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
        }
        this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
      });
    });

  }
  disable(category: Category) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " xóa " + category.name,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.categoryService.disableById(category.id).subscribe((res: any) => {
        this.ngxService.stop();
        this.snackbarService.open(res.data, 'success');
        this.loadData(this.page);
      }, (errors: any) => {
        let error = errors.error; 
        if (error) {
          this.responseMessage = error.data;
        } else {
          this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
        }
        this.snackbarService.open(this.responseMessage, SystemConstraints.ERROR);
      });
    });
  }
  onAdd() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'add'
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(CategoryDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onAddCategory.subscribe((res) => {
      this.loadData(this.page);
    });
  }

  onUpdate(values: Category) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'edit',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(CategoryDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onUpdateCategory.subscribe((res) => {
      this.loadData(this.page);
    });
  }
  onView(values: Category) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'view',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(CategoryDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
  }
  
}
