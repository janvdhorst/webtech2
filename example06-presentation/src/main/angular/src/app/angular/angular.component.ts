import { Component, OnInit } from '@angular/core';
import { NewsService } from './news.service';
import { News } from '../news';

@Component({
  selector: 'app-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [NewsService]
})
export class AngularComponent implements OnInit {

  public latest: News;
  public news: News[] = [];

  constructor(protected newsService: NewsService) {
  }

  ngOnInit() {
    this.load();
  }

  load(): void {
    this.newsService.getNewest().subscribe(
      news => this.latest = news,
      console.error
    );
    this.newsService.getAll().subscribe(
      news => this.news = news,
      console.error
    );
  }
}
