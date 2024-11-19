import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MainRoutingModule } from './main-routing.module';
import {PageComponent} from './user/page/page.component';
import {DashboardComponent} from './admin/dashboard/dashboard.component';
import {SharedModule} from '../../shared/shared.module';


@NgModule({
  declarations: [PageComponent, DashboardComponent],
  imports: [
    CommonModule,
    MainRoutingModule,
    SharedModule
  ]
})
export class MainModule { }
