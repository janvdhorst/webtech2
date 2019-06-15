import { Component } from '@angular/core';
import { CreateNewsComponent } from '../../angular/create-news/create-news.component';
import { AuthNewsService } from '../auth-news.service';

@Component({
  selector: 'create-news-auth',
  templateUrl: '../../angular/create-news/create-news.component.html',
  styleUrls: ['../../angular/create-news/create-news.component.sass']
})
export class CreateNewsAuthComponent extends CreateNewsComponent {

  constructor(newsService: AuthNewsService) {
    super(newsService);
  }
}
