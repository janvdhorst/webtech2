import { Component, Input, Output } from '@angular/core';
import { News } from '../../news';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { NewsService } from '../news.service';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.sass']
})
export class NewsListComponent {

	public onEdit = false;

	constructor(
	  private http: HttpClient, 
	  private newsService: NewsService, 
	  private router: Router,
	  private Token: TokenService
) { }

  @Output()
  public id:string = "";
  public content: string = "";

  @Input()
  public news: News[] = [];

  get reversedNews(): News[] {
    console.log("asd" + this.news);
    return this.news.slice().reverse();
  }

  public currentUser() {
	  return this.Token.getUsername();

  }


  public deleteNews(news: string): void {
		if(confirm("Are you sure to delete this news?")) {

			const body = new HttpParams()
			.set('id', news)
			.set('jwt', sessionStorage.getItem('token'));

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

    public editNews(news: string): void {
/*		if(confirm("Are you sure to delete this news?")) {

			const body = new HttpParams()
			.set('id', news)
			.set('jwt', sessionStorage.getItem('token'));

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
	*/ 	this.onEdit = true; 

	}

	public saveChanges() {
		this.onEdit = false;
	}

}
