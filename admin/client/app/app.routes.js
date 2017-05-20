import {StaticPageComponent} from './core/components/static-page/static-page.component';
import {HomeComponent} from './core/components/home/home.component';
import {LoginComponent} from './auth/components/login/login.component';
import {LoggedOutGuard} from './core/guards/logged-out.guard';

export const routes = [
  {path: '', component: HomeComponent, pathMatch: 'full'},
  {path: 'static-page', component: StaticPageComponent},
  {path: 'login', component: LoginComponent, canActivate: [LoggedOutGuard]}
  // {path: 'logout', canActivate: [LoggedInGuard]}
];
