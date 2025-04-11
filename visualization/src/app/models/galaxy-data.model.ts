export type Product = {
  releases: Release[];
  links: Link[];
};

export type Link = {
  sourceNodeId: string;
  targetNodeId: string;
};

export type Release = {
  nodeId: string;
  versionId: string;
  releaseDate: Date;
  features: Feature[];
  reviews: Review[];
};

export type Feature = {
  title: string;
  description: string;
  category: string;
};

export type Review = {
  nodeId: string;
  reviewId: string;
  score: Number;
  reviewer: string;
  reviewDate: Date;
  content: string;
  appVersion: string;
};

let example: Product = {
  releases: [
    {
      nodeId: 'version5',
      versionId: '5.17.5(31030)',
      releaseDate: new Date('2024-01-23'),
      features: [
        {
          title: 'Feature 1',
          description: 'Description of feature 1',
          category: 'General',
        },
        {
          title: 'Feature 2',
          description: 'Description of feature 2',
          category: 'General',
        },
      ],
      reviews: [
        {
          nodeId: 'ver5review1',
          reviewId: 'mockID',
          score: 5,
          reviewer: 'John Doe',
          reviewDate: new Date('2024-01-23'),
          content: 'Sample Content',
          appVersion: '5.17.5(31030)',
        },
        {
          nodeId: 'ver1review2',
          reviewId: 'mockID',
          score: 5,
          reviewer: 'John Doe',
          reviewDate: new Date('2024-01-23'),
          content: 'Sample Content',
          appVersion: '5.17.5(31030)',
        },
      ],
    },
  ],
  links: [
    { sourceNodeId: 'version5', targetNodeId: 'ver5review1' },
    { sourceNodeId: 'version5', targetNodeId: 'ver5review2' },
  ],
};
