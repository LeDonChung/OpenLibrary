import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { Book } from 'src/app/shared/models/Book';
import { AuthorService } from 'src/app/shared/services/author/author.service';
import { BookService } from 'src/app/shared/services/book/book.service';
import { CategoryService } from 'src/app/shared/services/category/category.service';
import { PublisherService } from 'src/app/shared/services/publisher/publisher.service';
import { SnackbarService } from 'src/app/shared/services/snackbar/snackbar.service';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.scss']
})
export class EditBookComponent implements OnInit {
  public book!: Book;
  public action: string = 'view';
  public id: any;
  public responseMessage: any;
  public bookForm: any = FormGroup;

  public imgUrl: string = "";
  public uploadImage!: File;
  public isImg: boolean = false;

  public contentPdf: string = "";
  public uploadContentPdf!: File;
  public isContentPdf: boolean = false;

  editor = ClassicEditor;
  public categories: any = [];
  public publishers: any = [];
  public authors: any = [];
  constructor(
    private bookService: BookService,
    private publisherService: PublisherService,
    private categoryService: CategoryService,
    private authorService: AuthorService,
    private router: Router,
    private route: ActivatedRoute,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService,
    private formBuilder: FormBuilder) { }
  ngOnInit(): void {
    this.ngxService.start();
    this.createForm();
    this.loadData();
    if (this.router.url.includes('view')) {
      this.action = 'view';
      this.route.params.subscribe(params => {
        if (params['id']) {
          this.id = params['id'];
        }
      });
    } if (this.router.url.includes('edit')) {
      this.route.params.subscribe(params => {
        if (params['id']) {
          this.id = params['id'];
          this.action = 'update';
        } else {
          this.action = 'add';
        }
      })
    }

    if (this.id) {
      this.bookService.getById(this.id).subscribe((res: any) => {
        this.ngxService.stop();
        this.book = res.data;
        if(this.book.bookCover != null) {
          this.isImg = true;
        }
        if(this.book.contentPdf != null) {
          this.isContentPdf = true;
        }
        this.createForm();
        this.book.categories.forEach((value: any) => {
          this.addCategory(value.id);
        });
        this.book.authors.forEach((value: any) => {
          this.addAuthor(value.id);
        });
      }, (errors: any) => {
        this.ngxService.stop();
        let error = errors.error;
        if (error) {
          this.responseMessage = error.data;
        } else {
          this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
        }
        this.snackbarService.open(this.responseMessage, 'error');
        this.router.navigate(['/admin/books']);
      });
    } else {
      this.ngxService.stop();
      this.addAuthor(-1);
      this.addCategory(-1);
    }
  }
  createForm() {
    if (this.book == null) {
      this.bookForm = this.formBuilder.group({
        id: [null],
        title: ['', [Validators.required]],
        isbn: ['', [Validators.required, Validators.pattern(SystemConstraints.REGEX_ISBN)]],
        numberOfPages: ['', [Validators.required]],
        description: ['', [Validators.required]],
        publishDate: ['', [Validators.required]],
        language: ['', [Validators.required]],
        categories: this.formBuilder.array([]),
        authors: this.formBuilder.array([]),
        publisher: ['', [Validators.required]],
      });
    } else {
      this.bookForm = this.formBuilder.group({
        id: [this.book.id],
        title: [this.book.title, [Validators.required]],
        isbn: [this.book.isbn, [Validators.required, Validators.pattern(SystemConstraints.REGEX_ISBN)]],
        numberOfPages: [this.book.numberOfPages, [Validators.required]],
        description: [this.book.description, [Validators.required]],
        publishDate: [this.book.publishDate, [Validators.required]],
        language: [this.book.language, [Validators.required]],
        categories: this.formBuilder.array([]),
        authors: this.formBuilder.array([]),
        publisher: [this.book.publisher.id, [Validators.required]],
      });
    }
  }
  loadData() {
    this.categoryService.getAll().subscribe((res: any) => {
      this.categories = res.data;
    });
    this.publisherService.getAll().subscribe((res: any) => {
      this.publishers = res.data;
    });
    this.authorService.getAll().subscribe((res: any) => {
      this.authors = res.data;
    });
  }

  onBack() {
    this.router.navigate(['/admin/books']);
  }

  get categoryForms() {
    return this.bookForm.controls['categories'];
  }
  addCategory(id: number) {
    const category = this.formBuilder.group({
      id: [id]
    });
    return this.categoryForms.push(category);
  }
  removeCategory(index: number) {
    return this.categoryForms.removeAt(index);
  }
  get authorForms() {
    return this.bookForm.controls['authors'];
  }
  addAuthor(id: number) {
    const author = this.formBuilder.group({
      id: [id]
    });
    return this.authorForms.push(author);
  }
  removeAuthor(index: number) {
    return this.authorForms.removeAt(index);
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
  onFileSelected(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (e: any) => {
        this.contentPdf = event.target.files[0].name;
      }
      reader.readAsDataURL(event.target.files[0]);
      this.uploadContentPdf = event.target.files[0];
      this.isContentPdf = false;
    }
  } 
  handlerAddSubmit() {
    this.ngxService.start();
    const formData = new FormData();
    let book: Book = this.bookForm.value;

    book.categories = book.categories.map((value: any) => {
      return value.id;
    });

    book.authors = book.authors.map((value: any) => {
      return value.id;
    });

    formData.append('bookCover', this.uploadImage !== undefined ? this.uploadImage : '');
    formData.append('contentPdf', this.uploadContentPdf !== undefined ? this.uploadContentPdf : '');
    formData.append('bookDto', JSON.stringify(book));
    this.bookService.insertOne(formData).subscribe((res: any) => {
      this.ngxService.stop();
      this.responseMessage = res.body.data;
      this.snackbarService.open(this.responseMessage, 'success');
      this.router.navigate(['/admin/books']);
    }, (errors: any) => {
      this.ngxService.stop();
      console.log(errors);
      let error = errors.error;
      if (error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, 'error');
    });
  }
  handlerUpdateSubmit() {
    this.ngxService.start();
    const formData = new FormData();
    this.book = this.bookForm.value;
    this.book.categories = this.book.categories.map((value: any) => {
      return value.id;
    });

    this.book.authors = this.book.authors.map((value: any) => {
      return value.id;
    });

    formData.append('contentPdf', this.uploadContentPdf !== undefined ? this.uploadContentPdf : '');
    formData.append('bookCover', this.uploadImage !== undefined ? this.uploadImage : '');
    formData.append('bookDto', JSON.stringify(this.book));

    this.bookService.insertOne(formData).subscribe((res: any) => {
      this.ngxService.stop();
      this.responseMessage = res.body.data;
      this.snackbarService.open(this.responseMessage, 'success');
      this.router.navigate(['/admin/books']);
    }, (errors: any) => {
      this.ngxService.stop();
      console.log(errors);
      let error = errors.error;
      if (error) {
        this.responseMessage = error.data;
      } else {
        this.responseMessage = SystemConstraints.SOMETHING_WENT_WRONG;
      }
      this.snackbarService.open(this.responseMessage, 'error');
    });
  }
  viewContent () {
    window.location.href = this.book.contentPdf;
  }
}

