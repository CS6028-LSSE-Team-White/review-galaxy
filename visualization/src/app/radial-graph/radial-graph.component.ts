import { Component, Input, OnInit, ElementRef } from '@angular/core';
import * as d3 from 'd3';

// // Import static feature and review data
// import featuresData from 'src/assets/features.json';
// import reviewsData from 'src/assets/reviews.json';

@Component({
  selector: 'app-radial-graph',
  templateUrl: './radial-graph.component.html',
  styleUrls: ['./radial-graph.component.scss'],
})
export class RadialGraphComponent implements OnInit {
  @Input() productData!: any; // Input property to receive external product data
  features: any[] = []; // Array to store features
  reviews: any[] = []; // Array to store reviews

  constructor(private el: ElementRef) {}

  ngOnInit(): void {
    // Load feature and review data
    this.features = this.productData.features;
    this.reviews = this.productData.reviews;

    // Ensure releases array is initialized
    if (!this.productData.releases) {
      this.productData.releases = [];
    }

    // Build structured product data and draw the visualization
    this.generateProductData();
    this.drawRadialGraph();

    // Handle responsiveness on window resize
    window.addEventListener('resize', this.handleResize.bind(this));
  } // Import static feature and review data
  // import featuresData from 'src/assets/features.json';
  // import reviewsData from 'src/assets/reviews.json';

  // Clears and redraws the graph on window resize
  handleResize(): void {
    d3.select(this.el.nativeElement).select('svg').selectAll('*').remove();
    if (this.productData.releases.length > 0) {
      this.drawRadialGraph();
    }
  }

  // Organizes features and reviews into versioned releases
  generateProductData(): void {
    const versionGroups: { [version: string]: any[] } = {};

    // Group features by their full release version
    this.features.forEach((feature: any) => {
      const version = feature['release_version'];
      if (!versionGroups[version]) {
        versionGroups[version] = [];
      }
      versionGroups[version].push(feature);
    });

    // Add a new product_version key to each review
    // TODO: create logic here to assign product_version based on review date
    this.reviews.forEach((review: any) => {
      review['product_version'] = '5.17.5';
    });

    // Map version groups into structured release objects
    this.productData.releases = Object.keys(versionGroups).map((version) => {
      const features = versionGroups[version];
      const majorVersion = version.split('.').slice(0, 2).join('.');

      return {
        versionId: version,
        majorVersion: majorVersion,
        releaseDate: features[0].releaseDate,
        features: features,
        reviews: this.reviews.filter((review) =>
          review.product_version.startsWith(version)
        ),
      };
    });
  }

  // Renders the full radial graph visualization
  drawRadialGraph(): void {
    const element = this.el.nativeElement;
    const width = window.innerWidth;
    const height = window.innerHeight;
    const centerX = 0;
    const centerY = 0;

    // Select the tooltip container
    const tooltip = d3.select(this.el.nativeElement).select('.tooltip');

    // Initialize the main SVG container
    const svg = d3
      .select(element)
      .select('svg')
      .attr('width', width)
      .attr('height', height)
      .attr('viewBox', `${-width / 2} ${-height / 2} ${width} ${height}`);

    // Extract and sort unique major versions to create rings
    const majorVersions = Array.from(
      new Set(
        this.productData.releases.map(
          (r: { majorVersion: string }) => r.majorVersion
        )
      )
    ).sort();

    // Create a zoomable container layer
    const zoomLayer = svg.append('g');
    let currentTransform = d3.zoomIdentity;

    // Enable scroll-based zoom only (no dragging)
    const zoom = d3
      .zoom()
      .filter((event: any) => {
        return event.type === 'wheel';
      })
      .scaleExtent([0.5, 5])
      .on('zoom', (event) => {
        currentTransform = event.transform;
        zoomLayer.attr('transform', event.transform);
      });

    (svg as any).call(zoom);

    // Handle manual zooming on wheel events
    svg.on('wheel.zoom', (event: WheelEvent) => {
      event.preventDefault();
      const direction = -event.deltaY;
      const zoomAmount = direction > 0 ? 1.1 : 0.9;
      const newScale = Math.max(
        0.5,
        Math.min(5, currentTransform.k * zoomAmount)
      );
      const newTransform = d3.zoomIdentity
        .translate(currentTransform.x, currentTransform.y)
        .scale(newScale);
      currentTransform = newTransform;
      zoomLayer.attr('transform', newTransform.toString());
    });

    // Draw center circle
    svg
      .append('circle')
      .attr('cx', centerX)
      .attr('cy', centerY)
      .attr('r', 20)
      .attr('fill', '#ffcc00')
      .attr('stroke', '#444')
      .attr('class', 'center-circle')
      .append('text')
      .attr('x', centerX)
      .attr('y', centerY)
      .attr('text-anchor', 'middle')
      .attr('dy', '.35em')
      .style('font-size', '20px')
      .style('font-family', 'Open Sans, sans-serif')
      .text('Zoom');

    const baseRadius = 200;
    const ringSpacing = 200;

    // Draw concentric version rings
    majorVersions.forEach((majorVersion, i) => {
      const radius = baseRadius + i * ringSpacing;

      zoomLayer
        .append('circle')
        .attr('cx', centerX)
        .attr('cy', centerY)
        .attr('r', radius)
        .attr('fill', 'none')
        .attr('stroke', '#ccc')
        .attr('stroke-width', 1)
        .attr('stroke-dasharray', '2,4');

      zoomLayer
        .append('text')
        .attr('x', centerX)
        .attr('y', centerY - radius - 10)
        .attr('text-anchor', 'middle')
        .style('fill', '#fff')
        .style('font-size', '12px')
        .text(`Version ${majorVersion}`);
    });

    // Render each version release as a circle on its ring
    this.productData.releases.forEach((release: any, i: number) => {
      const ringIndex = majorVersions.indexOf(release.majorVersion);
      const ringRadius = baseRadius + ringIndex * ringSpacing;

      const angle = (i * 2 * Math.PI) / this.productData.releases.length;
      const x = centerX + ringRadius * Math.cos(angle);
      const y = centerY + ringRadius * Math.sin(angle);

      const circleRadius = 30 + 1.5 * release.features.length;

      const avgRating = release.reviews.length
        ? release.reviews.reduce(
            (acc: number, r: { rating: number }) => acc + r.rating,
            0
          ) / release.reviews.length
        : 0;

      let fillColor = '#ffff00';
      if (avgRating >= 4) fillColor = '#32CD32';
      else if (avgRating <= 2) fillColor = '#e74c3c';

      // Draw version bubble
      zoomLayer
        .append('circle')
        .attr('cx', x)
        .attr('cy', y)
        .attr('r', circleRadius)
        .attr('fill', fillColor)
        .attr('stroke', '#444')
        .attr('class', 'version-circle');

      const fontSizeVersion = circleRadius / 4;
      const fontSizeFeatures = circleRadius / 5;

      // Version label inside the circle
      zoomLayer
        .append('text')
        .attr('x', x)
        .attr('y', y - 5)
        .attr('text-anchor', 'middle')
        .style('font-size', `${fontSizeVersion}px`)
        .style('font-weight', 'bold')
        .style('font-family', 'Open Sans, sans-serif')
        .text(`${release.versionId}`);

      // Feature count label inside the circle
      zoomLayer
        .append('text')
        .attr('x', x)
        .attr('y', y + 10)
        .attr('text-anchor', 'middle')
        .style('font-size', `${fontSizeFeatures}px`)
        .style('font-weight', 'bold')
        .style('font-family', 'Open Sans, sans-serif')
        .text(
          `${release.features.length} Feature${
            release.features.length !== 1 ? 's' : ''
          }`
        );

      // Orbiting review circles
      const reviewOrbitRadius = circleRadius + 25;
      release.reviews.forEach((review: any, j: number) => {
        const angleOffset = (2 * Math.PI * j) / release.reviews.length;
        const rx = x + reviewOrbitRadius * Math.cos(angleOffset);
        const ry = y + reviewOrbitRadius * Math.sin(angleOffset);

        let color = '#32CD32';
        if (review.rating < 2) color = '#e74c3c';
        else if (review.rating < 4) color = '#f1c40f';

        // Draw review dot with tooltip on hover
        zoomLayer
          .append('circle')
          .attr('cx', rx)
          .attr('cy', ry)
          .attr('r', 10)
          .attr('fill', color)
          .on('mouseover', (event) => {
            const stars = '⭐️'.repeat(review.rating);
            tooltip
              .style('opacity', 1)
              .style('left', `${event.pageX + 10}px`)
              .style('top', `${event.pageY - 20}px`).html(`
                <div style="font-family: 'Open Sans', sans-serif;">
                  <div style="font-weight: bold; font-size: 20px;">${review.username}</div>
                  <div style="font-size: 16px;"><em>Version: ${review.product_version}</em></div>
                  <div style="margin: 8px 0;">${stars}</div>
                  <div style="font-size: 16px;">${review.comment}</div>
                </div>
              `);
          })
          .on('mouseout', () => {
            tooltip.style('opacity', 0);
          });
      });
    });
  }
}
