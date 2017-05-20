import {Component, ChangeDetectionStrategy} from '@angular/core';

import template from './online-list.template.html';
import {UsersService} from '../../services/users.service';
import {SyncService} from '../../../core/sync.service';

@Component({
  selector: 'online-list',
  template: template,
  changeDetection: ChangeDetectionStrategy.Detached
})
export class OnlineListComponent {
  constructor(userService: UsersService, sync: SyncService) {
    this.list = [];
    this._sync = sync;
    this._userService = userService;
  }

  ngOnInit() {
    this.fetchOnline();
    this._sync.messages.subscribe(() => {
      this.fetchOnline();
    });
  }

  fetchOnline() {
    this._userService.getOnline().subscribe((users) => {
      this.list = users;
    });
  }
}
