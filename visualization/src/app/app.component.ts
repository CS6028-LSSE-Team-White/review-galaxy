import { Component } from '@angular/core';
import featuresData from 'src/assets/features.json';  // Import the entire features JSON (default export)
import reviewsData from 'src/assets/reviews.json';    // Import the entire reviews JSON (default export)

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  // Declare productData and initialize it with imported JSON data
  productData: any;

  constructor() {
    // Initialize the productData with your features and reviews
    this.productData = {
      features: featuresData,  // Access 'message' key in the features JSON
      reviews: reviewsData      // Access 'message' key in the reviews JSON
    };
  }
}
