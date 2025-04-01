import { AfterViewInit, Component } from '@angular/core';
import { APIService } from './services/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements AfterViewInit {
  title = 'visualization';

  constructor(private apiService: APIService) {}

  ngAfterViewInit() {
    // Perform database health check
    this.apiService.performDatabaseHealthCheck().subscribe({
      next: (response) => {
        console.log('Database health check response:', response);
      },
      error: (error) => {
        console.error('Error performing database health check:', error);
      },
      complete: () => {
        console.log('Database health check completed');
      },
    });
  }
}
