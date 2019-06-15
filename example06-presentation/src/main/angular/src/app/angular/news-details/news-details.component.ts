import { Component, Input } from '@angular/core';
import { News } from '../../news';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'news-details',
  templateUrl: './news-details.component.html',
  styleUrls: ['./news-details.component.sass']
})
export class NewsDetailsComponent {

  @Input()
  public news: News;

  @Input()
  public allowHtmlContent: boolean;

  constructor(private domSanitizer: DomSanitizer) {
    this.allowHtmlContent = false;
  }

  getTrustedHtml(value: string): SafeHtml {
    return this.domSanitizer.bypassSecurityTrustHtml(value);
  }
}
