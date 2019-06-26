import { Component, Input, Output } from '@angular/core';
import { News } from '../../news';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { NewsService } from '../news.service';

@Component({
  selector: 'news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.sass']
})
export class NewsListComponent {

  constructor(private newsService: NewsService) { }

  @Output()
  public id:string = "";

  @Input()
  public news: News[] = [];

  get reversedNews(): News[] {
    console.log("asd" + this.news);
    return this.news.slice().reverse();
  }

  
  public deleteNews(news: News): void {
    if(confirm("Are you sure to delete this news?")) {

		this.newsService.delete(news).subscribe(
			() => {
				this.news.forEach(
					(current, index) => {
						if(news.id === current.id) {
							this.news.splice(index,1);
						}
					});
			});
	}
  }

}
