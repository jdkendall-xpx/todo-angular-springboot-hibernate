import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'To-Do Tracker';
  links = [
    {text: 'Home', url: '/'},
    // TODO: Implement archive
    // {text: 'Archive', url: '/archive'},
  ];
}
