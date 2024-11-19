import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Login} from '../models/login';
import {Register} from '../models/register';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
#http: HttpClient = inject(HttpClient);
baseUrl: string = 'http://localhost:8080';
headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor() { }

  register(registerData : Register){
    return this.#http.post(
      `${this.baseUrl}/auth/signup`,
      {
      ...registerData
      },
      {
        headers: this.headers,
        responseType: "text"
      }
      )
  }

  login(loginData : Login){
    return this.#http.post(`${this.baseUrl}/auth/signin`,
      {
      ...loginData
    },
      {
        headers: this.headers,
      })
  }
}
