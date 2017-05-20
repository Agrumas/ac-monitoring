import Rx from 'rxjs/Rx';
import {Injectable, Inject} from '@angular/core';

@Injectable()
export class WebSocketService {
  subject: Rx.Subject<MessageEvent>;

  constructor(@Inject('WS_ENTRY_POINT') apiUrl) {
    this.apiUrl = apiUrl;
  }

  connect(url): Rx.Subject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.create(url);
    }

    return this.subject;
  }

  create(url): Rx.Subject<MessageEvent> {
    let ws = new WebSocket(this.apiUrl + url);

    let observable = Rx.Observable.create((obs: Rx.Observer<MessageEvent>) => {
      ws.onmessage = obs.next.bind(obs);
      ws.onerror = obs.error.bind(obs);
      ws.onclose = obs.complete.bind(obs);

      return ws.close.bind(ws);
    });

    let observer = {
      next: (data: Object) => {
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify(data));
        }
      },
    };

    return Rx.Subject.create(observer, observable);
  }
}
