import {NgModule} from '@angular/core';
import {TranslateService} from 'ng2-translate';
import {SharedModule} from '../shared/shared.module';
import {UsersService} from './services/users.service';

import {UsersRoutingModule} from './users.routing';

import {UsersListComponent} from './components/users-list/users-list.component';
import {OnlineListComponent} from './components/online-list/online-list.component';
import {ViewUserComponent} from './components/view/view-user.component';

import {ScreenDialog} from './components/screen-dialog/screen-dialog.component';
import {ProcessesDialog} from './components/processes-dialog/processes-dialog.component';

import {users} from '../i18n/en';

@NgModule({
  imports: [SharedModule, UsersRoutingModule],
  declarations: [
    UsersListComponent, OnlineListComponent, ViewUserComponent,
    ScreenDialog, ProcessesDialog
  ],
  providers: [UsersService],
  entryComponents: [ScreenDialog, ProcessesDialog]
})
export class UsersModule {
  constructor(translate: TranslateService) {
    translate.setTranslation('en', {users}, true);
  }
}
