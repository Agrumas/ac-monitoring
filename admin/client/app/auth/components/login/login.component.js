import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MdSnackBar} from '@angular/material';

import template from './login.template.html';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'login',
  template: template,
  providers: [MdSnackBar]
})
export class LoginComponent {

  constructor(userService: UserService, builder: FormBuilder, router: Router, snackBar: MdSnackBar) {
    this._userService = userService;
    this._router = router;
    this._snackBar = snackBar;

    this.loginForm = builder.group({
      email: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  onSubmit(credentials) {
    this._userService.login(credentials).subscribe(() => {
      this._router.navigate(['']);
    }, (err) => {
      this._snackBar.open(err.errors || 'Invalid credentials!', 'OK');
    });
  }
}
