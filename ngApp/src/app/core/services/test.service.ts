import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { Observable, of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { TestQuery } from '../query/test-query.enum';


@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor(
    private apollo: Apollo,
  ) { }
  testConnection(): Observable<any> {
    return this.apollo.watchQuery<any>({
      query: TestQuery.testQuery
    }).valueChanges.pipe(
      //tap(({data})=> console.log(data)),
      switchMap(({ data }) => of(data.test)
      )
    )
  }
}
