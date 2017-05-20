import _ from 'lodash';
import {Injectable, Inject, Sanitizer} from '@angular/core';
import {Http, Headers, URLSearchParams, ResponseContentType} from '@angular/http';

function unmarshal(httpObservable) {
  return httpObservable.map((res) => {
    if (res.status === 204) {
      return null;
    }
    return res.json();
  }).catch((error) => {
    const body = error.json();
    let msg = body.message;
    if (typeof body.errors === 'object' && msg === 'Validation Failed') {
      msg = _(body.errors).values().flatten().value().join(' ');
    }
    const err = _.defaults(new Error(msg), body);
    err._response = error;
    err._body = body;
    throw err;
  });
}

@Injectable()
export class ApiService {
  constructor(http: Http, @Inject('API_ENTRY_POINT') apiUrl, sanitizer: Sanitizer) {
    this.http = http;
    this.apiUrl = apiUrl;
    this.sanitizer = sanitizer;
  }

  setDefaultHeader(headers: Headers, opt = {}) {
    headers.append('Content-Type', 'application/json');
    if (!opt.skipAuth) {
      const authToken = localStorage.getItem('auth_token');
      if (authToken) {
        headers.append('Authorization', `Bearer ${authToken}`);
      }
    }
  }

  get(url, query) {
    const cfg = {
      headers: new Headers()
    };
    if (query) {
      const params = new URLSearchParams();
      _.forOwn(query, (value, key) => {
        params.set(key, value);
      });
      cfg.search = params;
    }
    this.setDefaultHeader(cfg.headers);
    return unmarshal(this.http.get(this.apiUrl + url, cfg));
  }

  post(url, data, options = {}) {
    let headers = new Headers();
    this.setDefaultHeader(headers, options);
    const opt = _.omit(options, ['skipAuth']);
    return unmarshal(this.http.post(this.apiUrl + url, JSON.stringify(data), _.defaults({
      headers: headers
    }, opt)));
  }

  delete(url, options = {}) {
    let headers = new Headers();
    this.setDefaultHeader(headers, options);
    const opt = _.omit(options, ['skipAuth']);
    return unmarshal(this.http.delete(this.apiUrl + url, _.defaults({
      headers: headers
    }, opt)));
  }

  put(url, data, options = {}) {
    let headers = new Headers();
    this.setDefaultHeader(headers, options);
    const opt = _.omit(options, ['skipAuth']);
    return unmarshal(this.http.put(this.apiUrl + url, JSON.stringify(data), _.defaults({
      headers: headers
    }, opt)));
  }

  getImage(url) {
    let headers = {'Content-Type': 'image/png'};
    const authToken = localStorage.getItem('auth_token');
    if (authToken) {
      headers.Authorization = `Bearer ${authToken}`;
    }
    return this.http.get(this.apiUrl + url, {
      headers,
      responseType: ResponseContentType.Blob
    })
      .map(res => {
        return new Blob([res._body], {
          type: res.headers.get("Content-Type")
        });
      })
      .map(blob => {
        let urlCreator = window.URL || window.webkitURL;
        return urlCreator.createObjectURL(blob);
      })
  }
}
