import {Component, ChangeDetectionStrategy} from '@angular/core';

import template from './users-list.template.html';
import {UsersService} from '../../services/users.service';

@Component({
  selector: 'online-list',
  template: template,
  changeDetection: ChangeDetectionStrategy.Detached
})
export class UsersListComponent {
  constructor(userService: UsersService) {
    this._userService = userService;
    this.list = [];
  }

  ngOnInit() {
    this._userService.getAll().subscribe((users) => {
      this.list = users;
    });
  }
}
