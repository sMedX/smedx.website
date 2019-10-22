import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { News } from 'src/app/core/entityInterfaces/news';
import { NewsService } from 'src/app/core/services/news.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-newsdetails',
  templateUrl: './newsdetails.component.html',
  styleUrls: ['./newsdetails.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class NewsdetailsComponent implements OnInit {

  selectedNews: News;
  isDataLoaded: boolean;

  constructor(
    private route: ActivatedRoute,
    private newsService: NewsService
  ) { }

  ngOnInit() {
    const newsLink = this.route.snapshot.paramMap.get('newslink');
    this.newsService.getNewsByLink(newsLink).subscribe(
      article => {
        this.selectedNews = article;
        this.isDataLoaded = this.selectedNews != null;
        return this.selectedNews;
      }
    );
  }

  getDate(news) {
    // var dt=Date.parse(article.added);
    // var result = dt.format("dd MMM yyyy");
    const result = (news.added).substr(0, news.added.length - 2);
    // console.log(result);
    return result;
  }

  prettifyBody(body: String) {
     body = this.replaceAll(body, '\n\n\n', '');
    body = this.replaceAll(body, '\n', '<br/>');
    // body = this.replaceAll(body, '<br><br><br>', '<br/>');
    // body = this.replaceAll(body, "<a", "<a class=\"a_style\"");
    return body;
  }

  replaceAll = function (target, search, replacement) {
    return target.replace(new RegExp(search, 'g'), replacement);
  };


}
