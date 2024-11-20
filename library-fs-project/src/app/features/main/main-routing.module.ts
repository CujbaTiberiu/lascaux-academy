import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PageComponent} from './user/page/page.component';
import {DashboardComponent} from './admin/dashboard/dashboard.component';
import {AuthGuardAdminService} from '../../core/guards/auth-guard-admin.service';
import {AuthGuardUserService} from '../../core/guards/auth-guard-user.service';

const routes: Routes = [
  {
    path: 'user',
    component: PageComponent,
    canActivate:[AuthGuardUserService]
  },
  {
    path: 'admin',
    component: DashboardComponent,
    canActivate:[AuthGuardAdminService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
