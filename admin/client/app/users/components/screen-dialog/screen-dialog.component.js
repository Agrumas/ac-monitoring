import {Component} from '@angular/core';
import {MdDialogRef} from '@angular/material';

@Component({
  selector: 'screen-dialog',
  template: `<md-dialog-content><img id="ItemPreview" src="" /></md-dialog-content>`
})
export class ScreenDialog {
  image: null;

  constructor(dialogRef: MdDialogRef) {
  }

  setImage(image) {
    document.getElementById("ItemPreview").src = image;
  }
}
