import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Page } from '../../models/Page';
import { FeedBack } from '../../models/FeedBack';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private apiUrl = environment.apiUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  constructor(private httpClient: HttpClient) { }

  getPages(page: Page<FeedBack>): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/feedback/getPages`, page, this.httpOptions);
  }
  getById(id: number): Observable<FeedBack> {
    return this.httpClient.get<FeedBack>(`${this.apiUrl}/feedback/findById/${id}`, this.httpOptions);
  }
  sendEmail(id: number, formData: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/feedback/sendResponseFeedback/${id}`, formData, {
      observe: 'response'
    });
  }
}
