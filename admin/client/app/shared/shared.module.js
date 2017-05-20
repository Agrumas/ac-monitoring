import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CustomFormsModule} from 'ng2-validation';
import {MaterialModule} from '@angular/material';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {TranslateModule, TranslateService} from 'ng2-translate';

import {translation} from '../i18n/en';

@NgModule({
  exports: [
    BrowserModule, CommonModule,
    FormsModule, ReactiveFormsModule, CustomFormsModule,
    MaterialModule, NgxDatatableModule,
    TranslateModule
  ]
})
export class SharedModule {
  constructor(translate: TranslateService) {
    translate.setTranslation('en', translation);
    translate.setDefaultLang('en');
    translate.use('en');
  }
}

