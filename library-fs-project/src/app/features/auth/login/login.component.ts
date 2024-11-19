import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  #formBuilder = inject(FormBuilder);
  #authService: AuthService = inject(AuthService);
  loginForm = this.#formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(6)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  })

  get password(){
    return this.loginForm.controls['password'];
  }

  get username(){
    return this.loginForm.controls['username'];
  }

  login(){

  }
}
