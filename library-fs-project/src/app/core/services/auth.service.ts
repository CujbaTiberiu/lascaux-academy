import {inject, Injectable} from '@angular/core';
import {HttpService} from './http.service';
import {Register} from '../models/register';
import {SnackbarService} from './snackbar.service';
import {Router} from '@angular/router';
import {Login} from '../models/login';
import {User} from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
#httpService: HttpService = inject(HttpService);
#snackbarService: SnackbarService = inject(SnackbarService);
#router : Router = inject(Router);

  constructor() { }

  register(registerData : Register){
    this.#httpService.register(registerData).subscribe({
      next: (data: string) => {
        console.log(data);
        this.#snackbarService.openSuccessSnackbar(
          "Registered successfully",
        )
        this.#router.navigate(["/auth/login"])
      },
      error: (error) => {
        console.log(error);
        this.#snackbarService.openErrorSnackbar(
          "Something went wrong!"
        )
      }
    });
  }

  login(loginData: Login){
    this.#httpService.login(loginData).subscribe({
      next: (data: any) => {
        console.log(data);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
