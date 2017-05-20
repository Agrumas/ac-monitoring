import {Injectable} from '@angular/core';

import {ApiService} from '../../core/api.service';

@Injectable()
export class UsersService {

  constructor(apiService: ApiService) {
    this._apiService = apiService;
  }

  getOnline() {
    return this._apiService.get('users/online');
  }

  getAll() {
    return this._apiService.get('users');
  }

  register(details) {
    return this._apiService.post('business', details)
      .map((res) => res.message);
  }

  get(id) {
    return this._apiService.get(`users/${id}`);
  }

  ban(id, reason) {
    return this._apiService.post(`users/${id}/ban`, {reason});
  }

  unban(id) {
    return this._apiService.post(`users/${id}/unban`);
  }

  warn(id, reason) {
    return this._apiService.post(`users/${id}/warn`, {reason});
  }

  getScreen(id) {
    return this._apiService.getImage(`users/${id}/screen`);
  }

  getProcesses(id) {
    return this._apiService.get(`users/${id}/processes`);
  }

}
