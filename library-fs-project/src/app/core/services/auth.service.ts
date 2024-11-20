import {inject, Injectable} from '@angular/core';
import {HttpService} from './http.service';
import {Register} from '../models/register';
import {SnackbarService} from './snackbar.service';
import {Router} from '@angular/router';
import {Login} from '../models/login';
import {User} from '../models/User';
import {BehaviorSubject, from, observable} from 'rxjs';
import {HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
#httpService: HttpService = inject(HttpService);
#snackbarService: SnackbarService = inject(SnackbarService);
#router : Router = inject(Router);
#user: BehaviorSubject<User | null> = new BehaviorSubject<User | null> (null);
userObserver$ = from(this.#user);

  constructor() { }

  saveUser(user : User) : void {
    localStorage.setItem('user', JSON.stringify(user));
    this.#user.next(user);
  }

  logout(){
    localStorage.clear();
    this.#user.next(null);
  }

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
        let user: User = data;
        console.log(data);
        this.saveUser(user);
        this.#snackbarService.openSuccessSnackbar(
          "Logged in successfully",
        );
        if(user.role === "ROLE_ADMIN"){
          this.#router.navigate(["main/admin"])
        } else {
          this.#router.navigate(["main/user"])
        }
      },
      error: (error) => {
        console.log(error);
        this.#snackbarService.openErrorSnackbar(
          "Something went wrong!"
        )
      }
    })
  }
}
