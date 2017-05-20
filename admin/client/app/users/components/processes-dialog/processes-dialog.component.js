import {Component, ViewEncapsulation, ViewChild} from '@angular/core';
import {MdDialogRef} from '@angular/material';

import template from './processes-dialog.template.html';

@Component({
  selector: 'processes-dialog',
  template: template
})
export class ProcessesDialog {
  image: null;
  rows = [];
  expanded = {};
  @ViewChild('processesTbl') table;

  constructor(dialogRef: MdDialogRef) {
  }

  setProcesses(processes) {
    this.rows = processes;
  }

  toggleExpandRow(row) {
    this.table.toggleExpandRow(row);
  }
}
