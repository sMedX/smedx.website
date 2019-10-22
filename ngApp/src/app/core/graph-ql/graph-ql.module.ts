import { NgModule } from '@angular/core';
import { Apollo, ApolloModule } from 'apollo-angular';
import { HttpLink } from 'apollo-angular-link-http';
import { WebSocketLink } from 'apollo-link-ws';
import { split } from 'apollo-link';
import { getMainDefinition } from 'apollo-utilities';
import { InMemoryCache } from 'apollo-cache-inmemory';

@NgModule({
  imports: [
    //CommonModule
    ApolloModule,
  ],
  declarations: []
})
export class GraphQlModule {

  constructor(
    apollo: Apollo,
    httpLink: HttpLink,
  ) {

    const http = httpLink.create({
      uri: '/graphql'
    });

    var loc = window.location, new_uri;
    new_uri = loc.protocol === "https:" ? "wss:" : "ws:";
    new_uri += "//" + loc.host;
    new_uri += "/graphql-ws/websocket";

    const ws = new WebSocketLink({
      uri: new_uri,
      options: {
        reconnect: true,
        lazy: true
      }
    })

    const link = split(
      ({ query }) => {
        const { kind, operation } = getMainDefinition(query);
        return kind === 'OperationDefinition' && operation === 'subscription';
      },
      ws,
      http,
    );
    //console.log(link);
    const cache = new InMemoryCache();

    apollo.create({
      link,
      cache: cache
    });
    //console.log(apollo);
  }
}
