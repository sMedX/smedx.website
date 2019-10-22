import { Component, OnInit } from '@angular/core';
import { NewsService } from 'src/app/core/services/news.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-news-editor',
  templateUrl: './news-editor.component.html',
  styleUrls: ['./news-editor.component.css']
})
export class NewsEditorComponent implements OnInit {

  news: any;
  hash: String;

  constructor(
    private newsService: NewsService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
        const newsId = params['newsId'];
        const userHash = params['hash'];
        console.log('newsId: ' + newsId + ' hash: ' + userHash);
        this.newsService.getNewsForEditor(+newsId, userHash).subscribe(
          _news => {
            if (_news) {
              console.log(_news);
              this.news = _news;
              this.hash = userHash;
              this.init();
            } else {
              console.log('No news founded: ');
              // this.router.navigate(['/']);
            }

          },
          error => {
            console.error(error);
            // this.router.navigate(['/']);
          }
        );
      });
  }

  init() {
    console.log('News should be loaded');
  }

  update(news) {
    console.log(news);
    this.newsService.updateNews(JSON.stringify(news), this.hash).subscribe(
      data => {
        //
        //
        console.log('Success');
        const route = '/newsblog/' + news.link;
        this.router.navigate([route]);
      },
      error => {
        console.log('Unexpected error:\r\n' + error);
      }
    );
  }

}
