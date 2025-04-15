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
  loadingState = 0;
  isLoading = true; // Loading state for the component
  productData_zoom: any;
  productData_webex: any; // Variable to store product data
  productData_firefox: any; // Variable to store product data

  export_data: any;
  export_name: any;

  constructor(private apiService: APIService) {}

  loadingStateHandler(): void {
    this.loadingState++;
    if (this.loadingState === 6) {
      this.changeProduct(1); // Set default product to Zoom
      console.log('Zoom product data:', this.productData_zoom); // Log the Zoom product data
      console.log('Webex product data:', this.productData_webex); // Log the Webex product data
      console.log('Firefox product data:', this.productData_firefox); // Log the Firefox product data
      this.isLoading = false;
    }
  }

  changeProduct(productId: number): void {
    switch (productId) {
      case 1:
        this.export_name = 'Zoom';
        this.export_data = this.productData_zoom;
        break;
      case 2:
        this.export_name = 'Webex';
        this.export_data = this.productData_webex;
        break;
      case 3:
        this.export_name = 'Firefox';
        this.export_data = this.productData_firefox;
        break;
      default:
        console.error('Invalid product ID');
    }

    console.log('Current product data:', this.export_data); // Log the current product data
    console.log('Current product name:', this.export_name); // Log the current product name
  }

  ngOnInit(): void {
    this.loadingState = 0; // Reset loading state
    this.isLoading = true; // Reset loading state

    this.productData_zoom = {
      features: [],
      reviews: [],
    };

    this.productData_webex = {
      features: [],
      reviews: [],
    };

    this.productData_firefox = {
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
        this.productData_zoom.features = JSON.parse(response.message); // Store fetched features data
        this.loadingStateHandler(); // Increment loading state
      },
      error: (error: any) => {
        console.error('Error fetching features:', error);
      },
    });

    // Fetch reviews data
    this.apiService.getReviews().subscribe({
      next: (response: any) => {
        this.productData_zoom.reviews = JSON.parse(response.message); // Store fetched reviews data
        this.loadingStateHandler(); // Increment loading state
      },
      error: (error: any) => {
        console.error('Error fetching reviews:', error);
      },
    });

    // Fetch features data for Webex
    this.apiService.getWebexFeatures().subscribe({
      next: (response: any) => {
        this.productData_webex.features = JSON.parse(response.message); // Store fetched features data
        this.loadingStateHandler(); // Increment loading state
      },
      error: (error: any) => {
        console.error('Error fetching features:', error);
      },
    });

    // Fetch reviews data
    this.apiService.getWebexReviews().subscribe({
      next: (response: any) => {
        let reviews = JSON.parse(response.message);
        reviews = reviews.map((review: any) => {
          return {
            ...review,
            release_version: new Date(review.timestamp)
              .toISOString()
              .split('T')[0], // Convert timestamp to YYYY-MM-DD format
          };
        });
        this.productData_webex.reviews = reviews; // Store fetched reviews data
        this.loadingStateHandler(); // Increment loading state
      },
      error: (error: any) => {
        console.error('Error fetching reviews:', error);
      },
    });

    // Fetch features data for Firefox
    // Firefox has no features tagged by release, so we are using the same features as Zoom
    this.apiService.getFeatures().subscribe({
      next: (response: any) => {
        this.productData_firefox.features = JSON.parse(response.message); // Store fetched features data
        this.loadingStateHandler(); // Increment loading state
      },
      error: (error: any) => {
        console.error('Error fetching features:', error);
      },
    });

    // Fetch reviews data
    this.apiService.getFirefoxReviews().subscribe({
      next: (response: any) => {
        this.productData_firefox.reviews = JSON.parse(response.message); // Store fetched reviews data
        this.loadingStateHandler(); // Increment loading state
      },
      error: (error: any) => {
        console.error('Error fetching reviews:', error);
      },
    });
  }
}
