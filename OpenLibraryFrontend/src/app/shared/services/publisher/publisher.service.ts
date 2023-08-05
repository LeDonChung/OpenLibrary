import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Publisher } from '../../models/Publisher';
import { Observable } from 'rxjs';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class PublisherService {

  private apiUrl: string = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Publisher[]> {
    return this.httpClient.get<Publisher[]>(`${this.apiUrl}/publisher/getAll`, this.httpOptions);
  }
  getPages(page: Page<Publisher>): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/publisher/getPages`, page, this.httpOptions);
  }
  getById(id: number): Observable<Publisher> {
    return this.httpClient.get<Publisher>(`${this.apiUrl}/publisher/findById/${id}`, this.httpOptions);
  }
  insertOne(publisher: Publisher): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/publisher/insert`, publisher, this.httpOptions);
  }
  updateOne(publisher: Publisher): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/publisher/update`, publisher, this.httpOptions);
  }
  enableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/publisher/enable/${id}`, this.httpOptions);
  }
  disableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/publisher/disable/${id}`, this.httpOptions);
  }
}
