import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Page } from 'src/app/shared/models/Page';
import { Publisher } from 'src/app/shared/models/Publisher';
import { PublisherService } from 'src/app/shared/services/publisher/publisher.service';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { PublisherDialogComponent } from '../dialog/publisher-dialog/publisher-dialog.component';

@Component({
  selector: 'app-publisher',
  templateUrl: './publisher.component.html',
  styleUrls: ['./publisher.component.scss']
})
export class PublisherComponent implements OnInit{
  public displayColumns: string[] = ["position", "name", "address", "action"];
  public responseMessage: any;
  public page: Page<Publisher> = new Page(0, 0, 5, []);
  constructor(
    private publisherService: PublisherService,
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
    this.publisherService.getPages(this.page).subscribe((res: any) => {
      this.ngxService.stop();
      this.page = res.data;
      console.log(this.page);
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
  enable(publisher: Publisher) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " bật " + publisher.name,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.publisherService.enableById(publisher.id).subscribe((res: any) => {
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
  disable(publisher: Publisher) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: " xóa " + publisher.name,
      confirmation: true
    }
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((res: any) => {
      dialogRef.close();
      this.ngxService.start();
      this.publisherService.disableById(publisher.id).subscribe((res: any) => {
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
    const dialogRef = this.dialog.open(PublisherDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onAddPublisher.subscribe((res) => {
      this.loadData(this.page);
    });
  }

  onUpdate(values: Publisher) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'edit',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(PublisherDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    const sub = dialogRef.componentInstance.onUpdatePublisher.subscribe((res) => {
      this.loadData(this.page);
    });
  }
  onView(values: Publisher) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'view',
      data: values
    }
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(PublisherDialogComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
  }
}
