import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {LoggedInGuard} from '../core/guards/logged-in.guard';

import {UsersListComponent} from './components/users-list/users-list.component';
import {OnlineListComponent} from './components/online-list/online-list.component';
import {ViewUserComponent} from './components/view/view-user.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'users', component: UsersListComponent, canActivate: [LoggedInGuard]},
      {path: 'users/online', component: OnlineListComponent, canActivate: [LoggedInGuard]},
      {path: 'users/:id', component: ViewUserComponent, canActivate: [LoggedInGuard]},
    ])
  ],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
