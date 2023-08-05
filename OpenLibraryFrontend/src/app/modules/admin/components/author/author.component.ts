import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Author } from 'src/app/shared/models/Author';
import { Page } from 'src/app/shared/models/Page';
import { AuthorService } from 'src/app/shared/services/author/author.service';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { AuthorDialogComponent } from '../dialog/author-dialog/author-dialog.component';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent implements OnInit{
  public displayColumns: string[] = ["position", "fullName", "image", "action"];
  public responseMessage: any;
  public page: Page<Author> = new Page(0, 0, 5, []);
  constructor(
    private authorService: AuthorService,
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
    this.authorService.getPages(this.page).subscribe((res: any) => {
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
  enable(author: Author) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " bật " + author.fullName,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.authorService.enableById(author.id).subscribe((res: any) => {
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
  disable(author: Author) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " xóa " + author.fullName,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.authorService.disableById(author.id).subscribe((res: any) => {
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
    const dialogRef = this.dialog.open(AuthorDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onAddAuthor.subscribe((res) => {
      this.loadData(this.page);
    });
  }

  onUpdate(values: Author) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'edit',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(AuthorDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onUpdateAuthor.subscribe((res) => {
      this.loadData(this.page);
    });
  }
  onView(values: Author) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'view',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(AuthorDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
  }
}
