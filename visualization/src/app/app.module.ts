import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RadialGraphComponent } from './radial-graph/radial-graph.component';  // Import RadialGraphComponent

@NgModule({
  declarations: [
    AppComponent,
    RadialGraphComponent,  // Ensure RadialGraphComponent is declared here
  ],
  imports: [
    BrowserModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
