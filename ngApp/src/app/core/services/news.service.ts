import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { Observable, of } from 'rxjs';
import { Queries } from '../query/queries.enum';
import { tap, switchMap } from 'rxjs/operators';
import { QUERIES } from '@angular/core/src/render3/interfaces/view';
import { Mutations } from '../mutation/mutations.enum';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(
    private apollo: Apollo
  ) { }

  getNews(skip: Number, limit: Number): Observable<any> {
    return this.apollo.watchQuery<any>({
      query: Queries.getAllNews,
      variables: {
        skip: skip,
        limit: limit
      }
    }).valueChanges.pipe(
      tap(({ data }) => console.log(data)),
      switchMap(({ data }) => of(data.getNews))
    )
  }

  getNewsByLink(newsLink: String): Observable<any> {
    return this.apollo.watchQuery<any>({
      query: Queries.getNewsByLink,
      variables: {
        link: newsLink
      }
    }).valueChanges.pipe(
      // tap(({ data }) => console.log(data)),
      switchMap(({ data }) => of(data.newsByLink)
      )
    );
  }

  getNewsForEditor(newsId: Number, userHash: String): Observable<any> {
    return this.apollo.watchQuery<any>({
      query: Queries.GET_NEWS_FOR_EDITOR,
      variables: {
        news_id: newsId,
        user_hash: userHash
      }
    }).valueChanges.pipe(
      tap(({ data }) => console.log(data)),
      switchMap(({ data }) => of(data.getNewsForEditor))
    );
  }

  updateNews(_news: String, _hash: String): Observable<any> {
    console.log('>>>>>>>>>>>>>>>>_news:>>>>>>>>>>>>>>>>\r\n' + _news);
    return this.apollo.mutate(
      {
        mutation: Mutations.UPDATE_NEWS,
        variables: {
          news: _news,
          user_hash: _hash
        }
      }
    ).pipe(
      tap(({ data }) => console.log(data)),
      switchMap(({ data }) => of(data.updateNews))
    )

  }
}
