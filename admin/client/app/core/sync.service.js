import {Injectable} from '@angular/core';
import {WebSocketService} from './websocket.service';

@Injectable()
export class SyncService {
  constructor(wsService: WebSocketService) {

    this.messages = wsService.connect('sync')
      .map((response) => {
        let [type, username] = response.data.split(':');
        return {type, username}
      });
  }
}
