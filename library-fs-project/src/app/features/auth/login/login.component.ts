import {ChangeDetectionStrategy, Component, inject, signal} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../core/services/auth.service';
import {Login} from '../../../core/models/login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  #formBuilder = inject(FormBuilder);
  #authService: AuthService = inject(AuthService);
  loginForm = this.#formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(5)]],
  })
  hide = signal(true);

  get password(){
    return this.loginForm.controls['password'];
  }

  get username(){
    return this.loginForm.controls['username'];
  }

  login(){
  this.#authService.login(this.loginForm.value as Login)
  }

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }
}
