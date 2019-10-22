import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { Queries } from '../query/queries.enum';
import { Observable, of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(
    private apollo: Apollo
  ) { }

  getArticles(): Observable<any> {
    return this.apollo.watchQuery<any>({
      query: Queries.getAllArticles
    }).valueChanges.pipe(
      tap(({ data }) => console.log(data)),
      switchMap(({ data }) => of(data.allArticles)
      )
    )
  }

  getArticleByLink(articleLink: String): Observable<any> {
    return this.apollo.watchQuery<any>({
      query: Queries.getArticlesByLink,
      variables: {
        link: articleLink
      }
    }).valueChanges.pipe(
      //tap(({ data }) => console.log(data)),
      switchMap(({ data }) => of(data.articleByLink)
      )
    )
  }
}
