import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { TestService } from 'src/app/core/services/test.service';
import { News } from 'src/app/core/entityInterfaces/news';
import { NewsService } from 'src/app/core/services/news.service';
import { skip } from 'rxjs/operators';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class NewsComponent implements OnInit {

  testMessage: String;
  allNews: [News];
  skip: Number;
  limit: Number;
  constructor(
    private testService: TestService,
    private newsService: NewsService,
  ) { }

  ngOnInit() {
    this.skip = 0;
    this.limit = 6;
    this.testService.testConnection().subscribe(
      testMessage => this.testMessage = testMessage
    );

    this.newsService.getNews(this.skip, this.limit).subscribe(
      news => this.allNews = news
    );
  }

  next() {
    let nextNews: [News];
    this.skip = +this.skip + +this.limit;
    this.newsService.getNews(this.skip, this.limit).subscribe(
      news => {
        nextNews = news,
          this.allNews.push.apply(this.allNews, nextNews);
        return nextNews;
      }
    );
    this.allNews.push.apply(this.allNews, nextNews);

  }
  getDate(news) {
    // var dt=Date.parse(article.added);
    // var result = dt.format("dd MMM yyyy");
    const result = (news.added).substr(0, news.added.length - 2);
    // console.log(result);
    return result;
  }

  checkTitleSize(news: News) {
    const viewport = this.getViewport();
    if (viewport.width <= 600 && news.title.length >= 40) {
      // console.log(news.title.indexOf(' ', 60));
      // console.log(news.title.substr(0, news.title.indexOf(' ', 40)) + ' ...');
      return news.title.substr(0, news.title.indexOf(' ', 30)) + ' ...';
    } else if (viewport.width <= 960 && news.title.length >= 60 && news.title.indexOf(' ', 60) > -1) {
      // console.log(this.getDate(news) + ': ' + news.title.indexOf(' ', 60));
      // console.log(this.getDate(news) + ': ' + news.title.substr(0, news.title.indexOf(' ', 60)) + ' ...');
      return news.title.substr(0, news.title.indexOf(' ', 60)) + ' ...';
    } else if (viewport.width <= 1024 && news.title.length >= 60 && news.title.indexOf(' ', 60) > -1) {
      // console.log(this.getDate(news) + ': ' + news.title.indexOf(' ', 60));
      // console.log(this.getDate(news) + ': ' + news.title.substr(0, news.title.indexOf(' ', 60)) + ' ...');
      return news.title.substr(0, news.title.indexOf(' ', 60)) + ' ...';
    } else {
      return news.title;
    }
  }

  checkSize(news: News): String {
    // console.log("Title:\r\n" + news.title + "\r\n " + news.title.length);
    // console.log("Description:\r\n" + news.description + "\r\n " + news.description.length);
    const viewport = this.getViewport();
    // if (news.title.length >= 80) {
    //    console.log(" **************************** case 1 ************************");
    //   return '';
    // } else 
    if (viewport.width <= 450 && news.title.length >= 60 && news.description.length >= 40) {
      // console.log(" **************************** case 2 ************************");
      // console.log(news.description.indexOf(" ", 100));
      if (news.description.indexOf('<') > -1 && news.description.indexOf('<') < news.description.indexOf('>')) {
        return news.description.substr(0, news.description.indexOf(' ', news.description.indexOf('>') + 140)) + ' ...';
      } else {
        return news.description.substr(0, news.description.indexOf(' ', 140)) + ' ...';
      }
    } else if (viewport.width <= 1024 && news.title.length >= 60 && news.description.length >= 40) {
      // console.log(" **************************** case 2 ************************");
      // console.log(news.description.indexOf(" ", 100));
      return '';
    } else if (news.title.length >= 60 && news.description.length >= 40) {
      // console.log(" **************************** case 2 ************************");
      // console.log(news.description.indexOf(" ", 100));
      return news.description.substr(0, news.description.indexOf(' ', 100)) + ' ...';
    } else {
      // console.log(" **************************** case 3 ************************");
      return news.description;
    }
  }

  checkCover(coverImg: String): boolean {
    return !(coverImg == null);
  }

  getViewport() {

    let viewPortWidth;
    let viewPortHeight;

    // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
    if (typeof window.innerWidth !== 'undefined') {
      viewPortWidth = window.innerWidth,
        viewPortHeight = window.innerHeight;
    } else if (typeof document.documentElement !== 'undefined'
      && typeof document.documentElement.clientWidth !==
      'undefined' && document.documentElement.clientWidth !== 0) {
      // IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
      viewPortWidth = document.documentElement.clientWidth,
        viewPortHeight = document.documentElement.clientHeight;
    } else {
      // older versions of IE
      viewPortWidth = document.getElementsByTagName('body')[0].clientWidth,
        viewPortHeight = document.getElementsByTagName('body')[0].clientHeight;
    }
    return { width: viewPortWidth, height: viewPortHeight };
  }

}
