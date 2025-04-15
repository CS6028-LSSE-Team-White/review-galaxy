import {
  Component,
  ElementRef,
  Input,
  OnInit,
} from '@angular/core';
import * as d3 from 'd3';
import { Product } from '../models/galaxy-data.model';

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

    window.addEventListener('resize', () => {
      d3.select(this.el.nativeElement).select('svg').selectAll('*').remove();
      this.drawRadialGraph();
    });
  }

  drawRadialGraph(): void {
    const element = this.el.nativeElement;
    const width = window.innerWidth;
    const height = window.innerHeight;
    const centerX = 0;
    const centerY = 0;

    const svg = d3
      .select(element)
      .select('svg')
      .attr('width', width)
      .attr('height', height)
      .attr('viewBox', `${-width / 2} ${-height / 2} ${width} ${height}`);

    const tooltip = d3.select(element).select('#tooltip');

    // Create zoomable layer
    const zoomLayer = svg.append('g');

    let currentTransform = d3.zoomIdentity;

    // Pan handling
    const zoom = d3.zoom()
      .scaleExtent([0.5, 5])
      .on('zoom', (event) => {
        currentTransform = event.transform;
        zoomLayer.attr('transform', event.transform);
      });

      (svg as any).call(zoom);


    // Force zooming to be centered (override mouse-based zoom)
    svg.on('wheel.zoom', (event: WheelEvent) => {
      event.preventDefault();

      const direction = -event.deltaY;
      const zoomAmount = direction > 0 ? 1.1 : 0.9;

      const newScale = Math.max(0.5, Math.min(5, currentTransform.k * zoomAmount));

      const newTransform = d3.zoomIdentity
        .translate(currentTransform.x, currentTransform.y)
        .scale(newScale);

      currentTransform = newTransform;
      zoomLayer.attr('transform', newTransform.toString());
    });

    const maxRingRadius = Math.min(width, height) / 2.5;
    const ringRadii = [
      maxRingRadius * 0.33,
      maxRingRadius * 0.66,
      maxRingRadius,
    ];

    // Draw rings
    ringRadii.forEach((r, i) => {
      zoomLayer.append('circle')
        .attr('cx', centerX)
        .attr('cy', centerY)
        .attr('r', r)
        .attr('stroke', '#444')
        .attr('fill', 'none');

      zoomLayer.append('text')
        .attr('x', centerX)
        .attr('y', centerY - r + 15)
        .attr('text-anchor', 'middle')
        .attr('fill', '#ccc')
        .style('font-size', '14px')
        .text(`v${i + 1}.0`);
    });

    // Center node
    zoomLayer.append('circle')
      .attr('cx', centerX)
      .attr('cy', centerY)
      .attr('r', 40)
      .attr('fill', '#7b61ff');

    zoomLayer.append('text')
      .attr('x', centerX)
      .attr('y', centerY + 5)
      .attr('text-anchor', 'middle')
      .text('Product')
      .attr('fill', 'white')
      .style('font-size', '14px');

    // Arrowhead marker
    svg.append('defs')
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

    // Position releases
    const angleStep = (2 * Math.PI) / (this.productData.releases.length || 1);

    this.productData.releases.forEach((release, i) => {
      const ringIndex = Math.min(i, ringRadii.length - 1);
      const angle = i * angleStep;
      const releaseRadius = ringRadii[ringIndex];

      const x = centerX + releaseRadius * Math.cos(angle);
      const y = centerY + releaseRadius * Math.sin(angle);

      // Release node
      zoomLayer.append('circle')
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

      // Reviews orbiting the release
      const reviewOrbitRadius = 35;
      release.reviews.forEach((review, j) => {
        const angleOffset = (2 * Math.PI * j) / release.reviews.length;
        const rx = x + reviewOrbitRadius * Math.cos(angleOffset);
        const ry = y + reviewOrbitRadius * Math.sin(angleOffset);

        const stars = '⭐'.repeat(review.score);

        let color = '#32CD32';
        if (review.score < 2) color = '#e74c3c';
        else if (review.score < 4) color = '#f1c40f';

        zoomLayer.append('circle')
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

      // Feature label
      zoomLayer.append('text')
        .attr('x', x)
        .attr('y', y + 5)
        .attr('text-anchor', 'middle')
        .attr('fill', '#222')
        .text(release.features[0]?.title || 'No Title')
        .style('font-size', '12px')
        .style('font-family', 'Open Sans, sans-serif');
    });
  }
}
