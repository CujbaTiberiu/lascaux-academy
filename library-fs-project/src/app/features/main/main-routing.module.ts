import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PageComponent} from './user/page/page.component';
import {DashboardComponent} from './admin/dashboard/dashboard.component';

const routes: Routes = [
  {
    path: 'user',
    component: PageComponent
  },
  {
    path: 'admin',
    component: DashboardComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
