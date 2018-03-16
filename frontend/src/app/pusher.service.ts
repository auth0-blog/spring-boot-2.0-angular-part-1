// pusher.service.ts
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import * as Pusher from 'pusher-js';

@Injectable()
export class PusherService {
  pusher: any;

  constructor() {
    this.pusher = new Pusher(environment.pusher.key, {
      cluster: 'us2',
        encrypted: true,
        authEndpoint: 'http://localhost:8080/auth',
        authorizer: (channel) => ({
          authorize: (socketId) => {
            axios.post('http://localhost:8080/auth', {
              socketId: socketId,
              channel: channel.name
            }).then(console.log).catch(console.log);
          }
        })
    });

    const subcription = this.pusher.subscribe('private-collaborative-diagrams');
  }
}
