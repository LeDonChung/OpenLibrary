import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Page } from 'src/app/shared/models/Page';
import { Quote } from 'src/app/shared/models/Quote';
import { QuoteService } from 'src/app/shared/services/quote/quote.service';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { QuoteDialogComponent } from '../dialog/quote-dialog/quote-dialog.component';

@Component({
  selector: 'app-quote',
  templateUrl: './quote.component.html',
  styleUrls: ['./quote.component.scss']
})
export class QuoteComponent {
  public displayColumns: string[] = ["position", "content", "like", "action"];
  public responseMessage: any;
  public page: Page<Quote> = new Page(0, 0, 5, []);
  constructor(
    private quoteService: QuoteService,
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
    this.quoteService.getPages(this.page).subscribe((res: any) => {
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
  enable(quote: Quote) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " bật " + quote.id,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.quoteService.enableById(quote.id).subscribe((res: any) => {
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
  disable(quote: Quote) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " xóa " + quote.id,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.quoteService.disableById(quote.id).subscribe((res: any) => {
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
    const dialogRef = this.dialog.open(QuoteDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onAddQuote.subscribe((res) => {
      this.loadData(this.page);
    });
  }

  onUpdate(values: Quote) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'edit',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(QuoteDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onUpdateQuote.subscribe((res) => {
      this.loadData(this.page);
    });
  }
  onView(values: Quote) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'view',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(QuoteDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
  }
}
