import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ArticleService } from 'src/app/core/services/article.service';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/core/entityInterfaces/article';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ArticleComponent implements OnInit {

  selectedArticle: Article;
  isDataLoaded: boolean;
  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    // private location: Location
  ) { }

  ngOnInit() {
    //console.log(this.route.snapshot.paramMap);
    const articleLink = this.route.snapshot.paramMap.get('articlelink');
    //console.log(articleLink);
    this.articleService.getArticleByLink(articleLink).subscribe(
      article => {
        this.selectedArticle = article;
        this.isDataLoaded = this.selectedArticle!=null;
        return this.selectedArticle;
      }
    )
  }

  getDate(article) {
    //var dt=Date.parse(article.added);
    //var result = dt.format("dd MMM yyyy");
    var result = (article.added).substr(0, article.added.length - 2);
    //console.log(result);
    return result;
  }

  prettifyBody(body: String) {
    body = this.replaceAll(body, "\n", "<br>");
    body = this.replaceAll(body, "<a", "<a class=\"a_style\"");
    return body;
  }

  replaceAll = function (target, search, replacement) {
    return target.replace(new RegExp(search, 'g'), replacement);
  }

}
