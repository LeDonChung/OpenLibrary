import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Author } from '../../models/Author';
import { Observable } from 'rxjs';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiUrl: string = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Author[]> {
    return this.httpClient.get<Author[]>(`${this.apiUrl}/author/getAll`, this.httpOptions);
  }
  getPages(page: Page<Author>): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/author/getPages`, page, this.httpOptions);
  }
  getById(id: number): Observable<Author> {
    return this.httpClient.get<Author>(`${this.apiUrl}/author/findById/${id}`, this.httpOptions);
  }
  insertOne(formData: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/author/insert`, formData, {
      observe: 'response'
    });
  }
  updateOne(formData: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/author/update`, formData, {
      observe: 'response'
    });
  }
  enableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/author/enable/${id}`, this.httpOptions);
  }
  disableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/author/disable/${id}`, this.httpOptions);
  }
}
