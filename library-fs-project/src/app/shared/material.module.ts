import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatBadgeModule} from '@angular/material/badge';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTabsModule} from '@angular/material/tabs';



@NgModule({
  declarations: [],
  imports: [
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatBadgeModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatTabsModule
  ],
  exports: [
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatBadgeModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatTabsModule
  ]
})
export class MaterialModule { }
