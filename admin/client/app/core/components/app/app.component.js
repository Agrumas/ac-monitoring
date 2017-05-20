import {Component, Inject} from '@angular/core'; // eslint-disable-line no-unused-vars

import template from './app.template.html';

@Component({
  selector: 'app',
  template: template
})
export class AppComponent {

  constructor(@Inject('ENVIRONMENT') environment) {
    this.environment = environment;
  }
}
