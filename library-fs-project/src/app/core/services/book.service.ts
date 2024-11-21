import {inject, Injectable} from '@angular/core';
import {HttpService} from './http.service';
import {SnackbarService} from './snackbar.service';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {Book} from '../models/Book';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  #httpService: HttpService = inject(HttpService);
  #snackbarService: SnackbarService = inject(SnackbarService);
  #books : BehaviorSubject<Book[] | null> = new BehaviorSubject<Book[] | null>(null);
  booksObs$ = of(this.#books);
  constructor() {
    this.#httpService.getAllBooks().subscribe({
      next: (result:Book[]) => {
        this.#books.next(result);
      },
      error: (err:any) => {
        console.log(err);
        this.#snackbarService.openErrorSnackbar("Something went wrong!");
      }
    })
  }
}
