import { Component } from '@angular/core';
import { CreateNewsComponent } from '../../angular/create-news/create-news.component';
import { SecurityNewsService } from '../security-news.service';

@Component({
  selector: 'create-news-security',
  templateUrl: '../../angular/create-news/create-news.component.html',
  styleUrls: ['../../angular/create-news/create-news.component.sass']
})
export class CreateNewsSecurityComponent extends CreateNewsComponent {

  constructor(newsService: SecurityNewsService) {
    super(newsService);
  }
}
