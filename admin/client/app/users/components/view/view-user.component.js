import _ from 'lodash';
import {Component, ChangeDetectionStrategy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MdSnackBar, MdDialogRef, MdDialog} from '@angular/material';

import {UsersService} from '../../services/users.service';
import {SyncService} from '../../../core/sync.service';

import template from './view-user.template.html';
import {ScreenDialog} from '../screen-dialog/screen-dialog.component';
import {ProcessesDialog} from '../processes-dialog/processes-dialog.component';

@Component({
  selector: 'view-user',
  template: template,
  changeDetection: ChangeDetectionStrategy.Detached
})
export class ViewUserComponent {
  user = {};
  dialogRef: MdDialogRef;

  constructor(userService: UsersService, route: ActivatedRoute, snackBar: MdSnackBar, dialog: MdDialog, sync: SyncService) {
    this._userService = userService;
    this._route = route;
    this._dialog = dialog;
    this._snackBar = snackBar;
    this._sync = sync;
  }

  ngOnInit() {
    this._route.params.subscribe((params) => {
      this.loadUser(params.id);
    });
    this._sync.messages.subscribe(msg => {
      if(this.user && this.user.name == msg.username){
        this.refresh();
      }
    });
  }

  loadUser(userId) {
    return this._userService.get(userId).subscribe((user) => {
      user.lastLogin = user.lastLogin * 1000;
      this.user = user;
    });
  }

  refresh() {
    return this.loadUser(this.user.id);
  }

  unban() {
    this._userService.unban(this.user.id).subscribe(() => {
      this.refresh();
      this._snackBar.open("User have been unbanned", 'OK');
    });
  }

  ban() {
    let reason = prompt("Please enter your name", "Cheating");
    if (reason == null) {
      return;
    }
    this._userService.ban(this.user.id, reason).subscribe(() => {
      this.refresh();
      this._snackBar.open("User have been banned", 'OK');
    });
  }

  warn() {
    let reason = prompt("Please enter your name", "Cheating");
    if (reason == null) {
      return;
    }
    this._userService.warn(this.user.id, reason).subscribe(() => {
      this.refresh();
      this._snackBar.open("User have been warned", 'OK');
    });
  }

  getScreen() {
    this._userService.getScreen(this.user.id).subscribe((screenshot) => {
      this.dialogRef = this._dialog.open(ScreenDialog);
      this.dialogRef.componentInstance.setImage(screenshot);
    });
  }

  getProcesses() {
    this._userService.getProcesses(this.user.id).subscribe((processList) => {
      this.dialogRef = this._dialog.open(ProcessesDialog);
      this.dialogRef.componentInstance.setProcesses(_.values(processList));
    });
  }
}
