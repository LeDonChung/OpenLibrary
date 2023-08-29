import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Quote } from '../../models/Quote';
import { Observable } from 'rxjs';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class QuoteService {
  private apiUrl: string = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Quote[]> {
    return this.httpClient.get<Quote[]>(`${this.apiUrl}/quote/getAll`, this.httpOptions);
  }
  getPages(page: Page<Quote>): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/getPages`, page, this.httpOptions);
  }
  getById(id: number): Observable<Quote> {
    return this.httpClient.get<Quote>(`${this.apiUrl}/quote/findById/${id}`, this.httpOptions);
  }
  insertOne(quote: Quote): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/insert`, quote, this.httpOptions);
  }
  updateOne(quote: Quote): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/update`, quote, this.httpOptions);
  }
  enableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/enable/${id}`, this.httpOptions);
  }
  disableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/disable/${id}`, this.httpOptions);
  }
  likeByid(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/like/${id}`, this.httpOptions);
  }
  unlikeByid(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/quote/unlike/${id}`, this.httpOptions);
  }
  getRand(): Observable<Object>  {
    return this.httpClient.get<Object>(`${this.apiUrl}/quote/getRand`, this.httpOptions);
  }
  
}
