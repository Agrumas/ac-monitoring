import {StorageService} from './services/storage.service';
import {UserService} from './services/user.service';

import {LoginComponent} from './components/login/login.component';

export {
  StorageService,
  UserService
};

export const AUTH_PROVIDERS = [
  StorageService, UserService
];
export const AUTH_DECLARATIONS = [LoginComponent];
