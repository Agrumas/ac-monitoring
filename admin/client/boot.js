import './shim';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserModule} from '@angular/platform-browser';
import {enableProdMode, NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MaterialModule} from '@angular/material';
import {TranslateModule} from 'ng2-translate';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
// import {RestangularModule} from 'ng2-restangular';

import {routes} from './app/app.routes';
import {CORE_PROVIDERS, CORE_DECLARATIONS, AppComponent} from './app/core';
import {AUTH_PROVIDERS, AUTH_DECLARATIONS} from './app/auth';
import {UsersModule} from './app/users/users.module';

if (ENVIRONMENT === 'production') {
  enableProdMode();
}

@NgModule({
  declarations: [
    CORE_DECLARATIONS, AUTH_DECLARATIONS
  ],
  imports: [
    HttpModule, BrowserModule, FormsModule, ReactiveFormsModule,
    MaterialModule.forRoot(), NgxDatatableModule,
    TranslateModule.forRoot(),
    // RestangularModule.forRoot((RestangularProvider) => {
    //   RestangularProvider.setBaseUrl('http://localhost:8000/v1');
    // }),
    RouterModule.forRoot(routes, {
      useHash: true
    }),
    UsersModule,
  ],
  providers: [
    {provide: 'ENVIRONMENT', useValue: ENVIRONMENT},
    {provide: 'API_ENTRY_POINT', useValue: WEBPACK_API_ENTRY_POINT},
    {provide: 'WS_ENTRY_POINT', useValue: WEBPACK_WS_ENTRY_POINT},
    CORE_PROVIDERS, AUTH_PROVIDERS
  ],
  bootstrap: [AppComponent]
})
class AppModule {
}

platformBrowserDynamic().bootstrapModule(AppModule);
