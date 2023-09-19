import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from '../../models/User';
import { Page } from '../../models/Page';
import { environment } from 'src/environments/environment';
import { ChangePassword } from '../../models/ChangePassword';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  refeshToken(body: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/refesh`, body, this.httpOptions);
  }
  
  private apiUrl:string = environment.apiUrl;
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }
  
  register(user: User): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/register`, user, this.httpOptions);
  }

  login(user: User): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/login`, user, this.httpOptions);
  }
  update(user: User): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/update`, user, this.httpOptions);
  }
  uploadImage(id: number, image: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/${id}/uploadImage`, image, {
        observe: 'response'
    });
  }
  getAllUser(page: Page<User>): Observable<User[]> {
    return this.httpClient.post<User[]>(`${this.apiUrl}/user/getAllUser`, page, this.httpOptions);
  }
  enableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/enable/${id}`, this.httpOptions);
  }
  disableById(id: number): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/disable/${id}`, this.httpOptions);
  } 
  getAllQuote(): Observable<Object> {
    return this.httpClient.get<Object>(`${this.apiUrl}/user/getAllQuotes`, this.httpOptions);
  }
  getCurrentUser(): Observable<Object> {
    return this.httpClient.get<Object>(`${this.apiUrl}/user/getCurrentUser`, this.httpOptions);
  }
  existsQuote(quoteId: number): Observable<Object> {
    return this.httpClient.get<Object>(`${this.apiUrl}/user/existsQuote?quoteId=${quoteId}`, this.httpOptions);
  }

  updateProfile(formData: any): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/updateProfile`, formData, {
      observe: 'response'
    });
  }
  changePassword(data: ChangePassword): Observable<Object> {
    return this.httpClient.post<Object>(`${this.apiUrl}/user/change-password`, data, this.httpOptions);
  }
}
