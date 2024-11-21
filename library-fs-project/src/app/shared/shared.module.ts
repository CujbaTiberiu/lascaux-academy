import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from './material.module';
import {ReactiveFormsModule} from '@angular/forms';
import { NavbarComponent } from './components/navbar/navbar.component';
import {RouterLink} from '@angular/router';
import { BooksTableComponent } from './components/books-table/books-table.component';



@NgModule({
  declarations: [
    NavbarComponent,
    BooksTableComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterLink,
  ],
  exports: [MaterialModule, NavbarComponent, BooksTableComponent]
})
export class SharedModule { }
