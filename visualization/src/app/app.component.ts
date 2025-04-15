import { Component, OnInit } from '@angular/core';
// import featuresData from 'src/assets/features.json'; // Import the entire features JSON (default export)
// import reviewsData from 'src/assets/reviews.json'; // Import the entire reviews JSON (default export)
import { APIService } from './services/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  isLoading = true; // Loading state for the component
  productData: any;

  constructor(private apiService: APIService) {}

  ngOnInit(): void {
    this.productData = {
      features: [],
      reviews: [],
    };

    // Perform middleware health check
    this.apiService.performMiddlewareHealthCheck().subscribe({
      next: (response: any) => {
        console.log('Middleware health check response:', response);
      },
      error: (error: any) => {
        console.error('Error performing middleware health check:', error);
      },
    });

    // Fetch features data
    this.apiService.getFeatures().subscribe({
      next: (response: any) => {
        this.productData.features = JSON.parse(response.message); // Store fetched features data
        // Fetch reviews data
        this.apiService.getReviews().subscribe({
          next: (response: any) => {
            this.productData.reviews = JSON.parse(response.message).slice(0,1000); // Store fetched reviews data
            this.isLoading = false; // Set loading state to false after fetching data
            console.log('Fetched product data:', this.productData); // Log the fetched data
          },
          error: (error: any) => {
            console.error('Error fetching reviews:', error);
          },
        });
      },
      error: (error: any) => {
        console.error('Error fetching features:', error);
      },
    });
  }
}
