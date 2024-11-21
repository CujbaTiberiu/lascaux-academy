import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {BookService} from '../../../core/services/book.service';
import {Event} from '@angular/router';
import {Book} from '../../../core/models/Book';

@Component({
  selector: 'app-books-table',
  templateUrl: './books-table.component.html',
  styleUrl: './books-table.component.css'
})
export class BooksTableComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['id', 'title', 'publicationDate', 'categories', 'reviews','vote'];
  dataSource = new MatTableDataSource<Book>();
  #bookService : BookService = inject(BookService);

  @ViewChild(MatPaginator) paginator = null;
  @ViewChild(MatSort) sort = null;

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnInit(): void {
    this.#bookService.booksObs$.subscribe({
      next: (value ) =>{
          console.log(value)
          console.log(value.subscribe(v => console.log(v)))
        value.subscribe(v => this.dataSource.data = v!)
      }
    })
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
