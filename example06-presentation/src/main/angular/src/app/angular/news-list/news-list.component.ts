import { Component, Input, Output } from '@angular/core';
import { News } from '../../news';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { NewsService } from '../news.service';
import { Router } from '@angular/router';

@Component({
  selector: 'news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.sass']
})
export class NewsListComponent {

  constructor(private http: HttpClient, private newsService: NewsService, private router: Router) { }

  @Output()
  public id:string = "";

  @Input()
  public news: News[] = [];

  get reversedNews(): News[] {
    console.log("asd" + this.news);
    return this.news.slice().reverse();
  }

  public deleteNews(news: string): void {
    if(confirm("Are you sure to delete this news?")) {

/* 		this.newsService.deleteNews(news).subscribe(
			() => {
			  this.news.forEach(
					(current, index) => {
						if(news.id === current.id) {
							this.news.splice(index,1);
						}
					});
      }); */

    const body = new HttpParams()
      .set('id', news)
      .set('jwt', sessionStorage.getItem('jwt'));

    const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    this.http.post(`example06/rest/news/delete`, body.toString(), {headers, responseType: 'text'})
    .subscribe(
      data => {
        alert('Die Nachricht wurde erfolgreich gelöscht.');
        window.location.reload();
      },
      error => {console.log(error); }
    );
  }
  }

}
