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
    if (this.loadingState === 3) {
      this.changeProduct(1); // Set default product to Zoom
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
        // Fetch reviews data
        this.apiService.getReviews().subscribe({
          next: (response: any) => {
            this.productData_zoom.reviews = JSON.parse(response.message); // Store fetched reviews data
            console.log('Fetched product data (zoom):', this.productData_zoom); // Log the fetched data
            this.loadingStateHandler(); // Increment loading state
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

    // Fetch features data for Webex
    this.apiService.getFeatures().subscribe({
      next: (response: any) => {
        this.productData_webex.features = JSON.parse(response.message); // Store fetched features data
        // Fetch reviews data
        this.apiService.getReviews().subscribe({
          next: (response: any) => {
            this.productData_webex.reviews = JSON.parse(response.message); // Store fetched reviews data
            console.log(
              'Fetched product data: (webex)',
              this.productData_webex
            ); // Log the fetched data
            this.loadingStateHandler(); // Increment loading state
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

    // Fetch features data for Firefox
    this.apiService.getFeatures().subscribe({
      next: (response: any) => {
        this.productData_firefox.features = JSON.parse(response.message); // Store fetched features data
        // Fetch reviews data
        this.apiService.getReviews().subscribe({
          next: (response: any) => {
            this.productData_firefox.reviews = JSON.parse(response.message); // Store fetched reviews data
            console.log(
              'Fetched product data (firefox):',
              this.productData_firefox
            ); // Log the fetched data
            this.loadingStateHandler(); // Increment loading state
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
