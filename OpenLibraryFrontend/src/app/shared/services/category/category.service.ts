import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Category } from '../../models/Category';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  
  
  private apiUrl: string = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(`${this.apiUrl}/category/getAll`, this.httpOptions);
  }
  getPages(page: Page<Category>): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/category/getPages`, page, this.httpOptions);
  }
  getById(id: number): Observable<Category> {
    return this.httpClient.get<Category>(`${this.apiUrl}/category/findById/${id}`, this.httpOptions);
  }
  insertOne(category: Category): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/category/insert`, category, this.httpOptions);
  }
  updateOne(category: Category): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/category/update`, category, this.httpOptions);
  }
  enableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/category/enable/${id}`, this.httpOptions);
  }
  disableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/category/disable/${id}`, this.httpOptions);
  }

  // user
  getAllByActivated(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(`${this.apiUrl}/category/getAllByActivated`, this.httpOptions);
  }

  getByCode(code: string): Observable<Category> {
    return this.httpClient.get<Category>(`${this.apiUrl}/category/findByCode/${code}`, this.httpOptions);
  }
}
