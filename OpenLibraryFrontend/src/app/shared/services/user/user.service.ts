import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemConstraints } from 'src/app/shared/SystemConstraints';
import { User } from '../../models/User';

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

}
