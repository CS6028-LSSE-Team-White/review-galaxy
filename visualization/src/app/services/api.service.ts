import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class APIService {
  private base_url = 'http://localhost:3000/';
  constructor(private httpClient: HttpClient) {}

  performMiddlewareHealthCheck(): Observable<any> {
    return this.httpClient.get(this.base_url + 'middleware-health', {
      responseType: 'json',
    });
  }

  getReviews(): Observable<any> {
    return this.httpClient.get(this.base_url + 'reviews', {
      responseType: 'json',
    });
  }

  getReviewById(id: number): Observable<any> {
    return this.httpClient.get(this.base_url + `review/${id}`, {
      responseType: 'json',
    });
  }

  getReviewsByProductVersion(version: string): Observable<any> {
    return this.httpClient.get(this.base_url + `reviews/version/${version}`, {
      responseType: 'json',
    });
  }

  getFeatures(): Observable<any> {
    return this.httpClient.get(this.base_url + 'features', {
      responseType: 'json',
    });
  }

  getFeatureById(id: number): Observable<any> {
    return this.httpClient.get(this.base_url + `feature/${id}`, {
      responseType: 'json',
    });
  }
}
