import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from '../../models/User';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  
  private apiUrl:string = SystemConstraints.API_URL;
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
  getCurrentUser(): Observable<Object> {
    return this.httpClient.get<Object>(`${this.apiUrl}/user/getCurrentUser`, this.httpOptions);
  }
}
