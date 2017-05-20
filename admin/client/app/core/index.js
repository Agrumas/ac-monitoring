import {LoggedInGuard} from './guards/logged-in.guard';
import {LoggedOutGuard} from './guards/logged-out.guard';
import {ApiService} from './api.service';
import {WebSocketService} from './websocket.service';
import {SyncService} from './sync.service';

import {HomeComponent} from './components/home/home.component';
import {StaticPageComponent} from './components/static-page/static-page.component';
import {MenuComponent} from './components/menu/menu.component';
import {AppComponent} from './components/app/app.component';

export {AppComponent};
export const CORE_PROVIDERS = [LoggedInGuard, LoggedOutGuard, ApiService, WebSocketService, SyncService];
export const CORE_DECLARATIONS = [HomeComponent, StaticPageComponent, MenuComponent, AppComponent];
