import {AuthService} from './auth.service';
import {inject} from '@angular/core';
import {HttpHandlerFn, HttpRequest} from '@angular/common/http';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const token = inject(AuthService).token;
  console.log("interceptor token: ",token);
  const newRequest = req.clone({
    headers: req.headers.append('Authorization', 'Bearer ' + token)
  })
  return next(newRequest);
}
