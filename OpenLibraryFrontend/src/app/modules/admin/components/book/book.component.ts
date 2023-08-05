import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Book } from 'src/app/shared/models/Book';
import { Page } from 'src/app/shared/models/Page';
import { BookService } from 'src/app/shared/services/book/book.service';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit{
  public displayColumns: string[] = ["title", "bookCover" , "categories", "authors" , "isbn", "language", "publisher", "action"];
  public responseMessage: any;
  public page: Page<Book> = new Page(0, 0, 5, []);
  constructor(
    private bookService: BookService,
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
    this.bookService.getPages(this.page).subscribe((res: any) => {
      this.ngxService.stop();
      this.page = res.data;
      console.log(res.data);
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
  enable(book: Book) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " bật " + book.title,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.bookService.enableById(book.id).subscribe((res: any) => {
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
  disable(book: Book) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " xóa " + book.title,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.bookService.disableById(book.id).subscribe((res: any) => {
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
    this.router.navigate(['admin/books/edit']);
  }

  onUpdate(values: Book) {
    this.router.navigate(['admin/books/edit/' + values.id]);
  }
  onView(values: Book) {
    this.router.navigate(['admin/books/view/' + values.id]);
  }

  remove(book: Book) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " xóa vĩnh viễn " + book.title,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.bookService.removeForever(book.id).subscribe((res: any) => {
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
}
