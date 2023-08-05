import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Book } from '../../models/Book';
import { Observable } from 'rxjs';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  private apiUrl: string = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Book[]> {
    return this.httpClient.get<Book[]>(`${this.apiUrl}/book/getAll`, this.httpOptions);
  }
  getPages(page: Page<Book>): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/book/getPages`, page, this.httpOptions);
  }
  getById(id: number): Observable<Book> {
    return this.httpClient.get<Book>(`${this.apiUrl}/book/findById/${id}`, this.httpOptions);
  }
  insertOne(formData: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/book/insert`, formData, {
      observe: 'response'
    });
  }
  updateOne(formData: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/book/update`, formData, {
      observe: 'response'
    });
  }
  enableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/book/enable/${id}`, this.httpOptions);
  }
  disableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/book/disable/${id}`, this.httpOptions);
  }
  removeForever(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/book/delete/${id}`, this.httpOptions);

  }
}
