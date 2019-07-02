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
	public editId: number = 0;

	constructor(
	  private http: HttpClient, 
	  private newsService: NewsService, 
	  private router: Router,
	  private Token: TokenService
) { }

  @Output()
  public id:string = "";
  public content: string = "";
  public newContent: string = "";

  @Input()
  public news: News[] = [];

  get reversedNews(): News[] {
    console.log("asd" + this.news);
    return this.news.slice().reverse();
  }

  public currentUser() {
	  return this.Token.getUsername();
  }

  public isAdmin() {
    return this.Token.getAdmin();
  }


  public deleteNews(news: string): void {
		if(confirm("Are you sure to delete this news?")) {

			const body = new HttpParams()
			.set('id', news)
			.set('jwt', this.Token.get());

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

    public editNews(id: number): void {
	 	this.onEdit = true; 
		this.editId = id;
	}

	public saveChanges(news: string): void {
		if(confirm("Save changes?")) {			
			this.onEdit = false;

			const body = new HttpParams()
			.set('id', news)
			.set('jwt', this.Token.get())
			.set('newContent', this.newContent);

			const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
			this.http.post(`example06/rest/news/update`, body.toString(), {headers, responseType: 'text'})
			.subscribe(
			data => {
				window.location.reload();
			},
			error => {console.log(error); }
			);
		}
	}

	public cancelChanges() {
	 	this.onEdit = false; 
	}
}
