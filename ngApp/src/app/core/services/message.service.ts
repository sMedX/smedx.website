import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { Message } from '../entityInterfaces/message';
import { Mutations } from '../mutation/mutations.enum';
import { tap, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(
    private apollo: Apollo,
  ) { }

  sendMessage(name: String, email: String, message: String) {
    console.log('Sending message to backend');
    //console.log(message);
    this.apollo.mutate<any>({
      mutation: Mutations.messageMuttation,
      variables: {
        name: name,
        email: email,
        body: message
      }
    }).subscribe(data => {
      console.log(data.data.sendMessage),
        of(data.data.sendMessage)
    })

  }
}
