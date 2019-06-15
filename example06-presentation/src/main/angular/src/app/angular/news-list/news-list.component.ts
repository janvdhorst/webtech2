import { Component, Input } from '@angular/core';
import { News } from '../../news';

@Component({
  selector: 'news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.sass']
})
export class NewsListComponent {

  @Input()
  public news: News[] = [];

  get reversedNews(): News[] {
    return this.news.slice().reverse();
  }
}
