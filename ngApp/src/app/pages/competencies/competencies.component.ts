import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ArticleService } from 'src/app/core/services/article.service';
import { Article } from 'src/app/core/entityInterfaces/article';

@Component({
  selector: 'app-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class CompetenciesComponent implements OnInit {

  articles: Article[] = [];
  selectedArticle: Article;
  
  constructor(
    private articlesService: ArticleService,
  ) { }

  ngOnInit() {
    this.articlesService.getArticles().subscribe(
      articles => {
        //console.log(articles);
        this.articles = articles;
        return this.articles;

      }
    )
  }

  onSelect(article: Article): void {
    console.log("Selecting article");
    console.log(article);
    this.selectedArticle = article;
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
