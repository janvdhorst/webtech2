import { Component, EventEmitter, Output } from '@angular/core';
import { NewsService } from '../news.service';

@Component({
  selector: 'create-news',
  templateUrl: './create-news.component.html',
  styleUrls: ['./create-news.component.sass']
})
export class CreateNewsComponent {

  @Output()
  public created = new EventEmitter();

  public headline: string = "";
  public content: string = "";
  public errorMessage: string;

  constructor(private newsService: NewsService) { }

  public createNews(e: Event): void {
    e.preventDefault();
    this.errorMessage = null;

    if (this.headline.trim() != null && this.content.trim() != null) {
      this.newsService.create(this.headline, this.content).subscribe(
        () => {
          this.created.emit();
          this.headline = "";
          this.content = "";
        },
        () => this.errorMessage = 'Could not create news'
      );
    }
  }

  getCharsLeft(): number {
    return 255 - this.content.length;
  }
}
