import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ArticleService } from '../core/services/article.service';
import { delay } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ArticleGuard implements CanActivate {

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    console.log(next.paramMap.get('articlelink'));
    return this.checkDataloaded(next.paramMap.get('articlelink'));
  }

  checkDataloaded(url: String): boolean {
    // console.log(url);
    // var selectedArticle;
    // this.articleService.getArticleByLink(url).subscribe(
    //   article => {
    //     delay(1000),
    //     selectedArticle = article,
    //     console.log(selectedArticle);
    //     return selectedArticle;
    //   }
    // )
    // console.log(selectedArticle);
    // return selectedArticle != null;
    return true;
  }
}
