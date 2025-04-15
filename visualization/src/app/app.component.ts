import { Component } from '@angular/core';
import { mockData } from './models/galaxy-data.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  productData = mockData;
}
