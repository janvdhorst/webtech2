import { Component } from '@angular/core';
import { SecurityNewsService } from './security-news.service';
import { AngularComponent } from '../angular/angular.component';

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.sass'],
  providers: [SecurityNewsService]
})
export class SecurityComponent extends AngularComponent {

  constructor(newsService: SecurityNewsService) {
    super(newsService);
  }
}
