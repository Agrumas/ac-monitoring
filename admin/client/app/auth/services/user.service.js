import {Injectable} from '@angular/core';
// import {Restangular} from 'ng2-restangular';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import {StorageService} from './storage.service';
import {ApiService} from '../../core/api.service';

@Injectable()
export class UserService {

  _loggedIn = new BehaviorSubject(false);

  constructor(apiService: ApiService, storage: StorageService) {
    this._apiService = apiService;
    this._storage = storage;

    if (!!this._storage.getAuthToken()) {
      this._loggedIn.next(true);
    }
  }

  login({email, password}) {
    return this._apiService.post('auth/login', {name: email, password})
      .map((res) => {
        this._storage.setAuthToken(res.token);
        this._loggedIn.next(true);
        return res;
      });
  }

  register(credentials) {
    return this._apiService.post('register', credentials)
      .map((res) => res.message);
  }

  logout() {
    this._storage.removeAuthToken();
    this._loggedIn.next(false);
  }

  isLoggedIn() {
    return this._loggedIn.getValue();
  }

  getLoggedIn() {
    return this._loggedIn;
  }
}
