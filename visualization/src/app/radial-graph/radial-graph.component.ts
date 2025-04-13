import {
  Component,
  ElementRef,
  Input,
  OnInit,
} from '@angular/core';
import * as d3 from 'd3';
import { Product } from '../models/galaxy-data.model'; // Adjust path as needed

@Component({
  selector: 'app-radial-graph',
  templateUrl: './radial-graph.component.html',
  styleUrls: ['./radial-graph.component.scss'],
})
export class RadialGraphComponent implements OnInit {
  @Input() productData!: Product;

  constructor(private el: ElementRef) {}

  ngOnInit(): void {
    this.drawRadialGraph();
  }

  drawRadialGraph(): void {
    const element = this.el.nativeElement;
    const width = 800;
    const height = 800;
    const centerX = width / 2;
    const centerY = height / 2;

    const svg = d3
      .select(element)
      .select('svg')
      .attr('width', width)
      .attr('height', height);

    const tooltip = d3.select(element).select('#tooltip');

    const ringRadii = [100, 200, 300]; // WILL NEED TO BE UPDATED

    // Draw concentric rings
    ringRadii.forEach((r, i) => {
      svg
        .append('circle')
        .attr('cx', centerX)
        .attr('cy', centerY)
        .attr('r', r)
        .attr('stroke', '#444')
        .attr('fill', 'none');

      svg
        .append('text')
        .attr('x', centerX)
        .attr('y', centerY - r + 15)
        .attr('text-anchor', 'middle')
        .attr('fill', '#ccc')
        .style('font-size', '14px')
        .text(`v${i + 1}.0`);
    });

    // Draw center product node
    svg
      .append('circle')
      .attr('cx', centerX)
      .attr('cy', centerY)
      .attr('r', 40)
      .attr('fill', '#7b61ff');

    svg
      .append('text')
      .attr('x', centerX)
      .attr('y', centerY + 5)
      .attr('text-anchor', 'middle')
      .text('Product')
      .attr('fill', 'white')
      .style('font-size', '14px');

    // Arrowhead definition for links
    svg
      .append('defs')
      .append('marker')
      .attr('id', 'arrowhead')
      .attr('viewBox', '0 -5 10 10')
      .attr('refX', 15)
      .attr('refY', 0)
      .attr('markerWidth', 6)
      .attr('markerHeight', 6)
      .attr('orient', 'auto')
      .append('path')
      .attr('d', 'M0,-5L10,0L0,5')
      .attr('fill', '#999');

    // Calculate angle step based on release count
    const angleStep = (2 * Math.PI) / (this.productData.releases.length || 1);

    this.productData.releases.forEach((release, i) => {
      const ringIndex = Math.min(i, ringRadii.length - 1);
      const angle = i * angleStep;
      const releaseRadius = ringRadii[ringIndex];

      const x = centerX + releaseRadius * Math.cos(angle);
      const y = centerY + releaseRadius * Math.sin(angle);
      

      // Draw release node
      svg
        .append('circle')
        .attr('cx', x)
        .attr('cy', y)
        .attr('r', 25)
        .attr('fill', '#ffcc00')
        .attr('class', 'release-node')
        .on('mouseover', (event) => {
          tooltip
            .style('opacity', 1)
            .style('left', `${event.pageX + 10}px`)
            .style('top', `${event.pageY - 20}px`)
            .html(
              `<strong>${release.versionId}</strong><br>${release.features.length} Reviews`
            );
        })
        .on('mouseout', () => {
          tooltip.style('opacity', 0);
        });

        const reviewOrbitRadius = 35; // Distance from the release center

        release.reviews.forEach((review, j) => {
          const angleOffset = (2 * Math.PI * j) / release.reviews.length;

          // Centered around the release node (x, y)
          const rx = x + reviewOrbitRadius * Math.cos(angleOffset);
          const ry = y + reviewOrbitRadius * Math.sin(angleOffset);

          const stars = '⭐'.repeat(review.score);

          let color = '#32CD32'; // green
          if (review.score < 2) {
            color = '#e74c3c'; // red
          } else if (review.score < 4) {
            color = '#f1c40f'; // yellow
          }

          svg.append('circle')
            .attr('cx', rx)
            .attr('cy', ry)
            .attr('r', 6)
            .attr('fill', color)
            .on('mouseover', (event) => {
              tooltip
                .style('opacity', 1)
                .style('left', `${event.pageX + 10}px`)
                .style('top', `${event.pageY - 20}px`)
                .html(`
                  <strong>${review.reviewer}</strong><br/>
                  <em>Version: ${review.appVersion}</em><br/>
                  ${stars}<br/>
                  ${review.content}
                `);
            })
            .on('mouseout', () => {
              tooltip.style('opacity', 0);
            });
        });

        svg.append('text')
        .attr('x', x)
        .attr('y', y + 5) // vertically centered inside the feature circle
        .attr('text-anchor', 'middle')
        .attr('fill', '#222')
        .text(release.features[0]?.title || 'No Title')
        .style('font-size', '12px')
        .style('font-family', 'Open Sans, sans-serif');
        


    });
  }
}
