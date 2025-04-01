import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class APIService {
  private base_url = 'http://localhost:3000/';
  constructor(private httpClient: HttpClient) {}

  performDatabaseHealthCheck(): Observable<any> {
    return this.httpClient.get(this.base_url + 'database-health', {
      responseType: 'json',
    });
  }
}
