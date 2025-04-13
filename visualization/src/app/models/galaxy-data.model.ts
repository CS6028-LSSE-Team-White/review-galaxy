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
  score: number; //this needed to be changed from Number to number
  reviewer: string;
  reviewDate: Date;
  content: string;
  appVersion: string;
};

//Longer Mock Data (ChatGPT generated)
export const mockData: Product = {
  releases: [
    {
      nodeId: 'version1',
      versionId: '1.0.0',
      releaseDate: new Date('2023-01-15'),
      features: [
        {
          title: 'Login with Email',
          description: 'Users can now login with their email address.',
          category: 'Authentication',
        },
        {
          title: 'Password Reset',
          description: 'Self-service password reset available via email.',
          category: 'Authentication',
        },
      ],
      reviews: [
        {
          nodeId: 'v1review1',
          reviewId: 'r1',
          score: 3,
          reviewer: 'Alice',
          reviewDate: new Date('2023-01-16'),
          content: 'Login works fine, but password reset took too long.',
          appVersion: '1.0.0',
        },
        {
          nodeId: 'v1review2',
          reviewId: 'r2',
          score: 4,
          reviewer: 'Bob',
          reviewDate: new Date('2023-01-17'),
          content: 'Smooth experience overall!',
          appVersion: '1.0.0',
        },
      ],
    },
    {
      nodeId: 'version2',
      versionId: '2.0.0',
      releaseDate: new Date('2023-05-10'),
      features: [
        {
          title: 'Dark Mode',
          description: 'New dark theme for reduced eye strain.',
          category: 'UI/UX',
        },
        {
          title: 'Profile Editing',
          description: 'Edit name, email, and profile image.',
          category: 'User',
        },
        {
          title: 'Push Notifications',
          description: 'Stay updated with instant alerts.',
          category: 'Communication',
        },
      ],
      reviews: [
        {
          nodeId: 'v2review1',
          reviewId: 'r3',
          score: 5,
          reviewer: 'Charlie',
          reviewDate: new Date('2023-05-11'),
          content: 'Dark mode looks amazing!',
          appVersion: '2.0.0',
        },
        {
          nodeId: 'v2review2',
          reviewId: 'r4',
          score: 2,
          reviewer: 'Dana',
          reviewDate: new Date('2023-05-12'),
          content: 'Notifications are too frequent and can’t be turned off.',
          appVersion: '2.0.0',
        },
        {
          nodeId: 'v2review3',
          reviewId: 'r5',
          score: 4,
          reviewer: 'Eli',
          reviewDate: new Date('2023-05-13'),
          content: 'Good improvements. Would love auto-save in profile editing.',
          appVersion: '2.0.0',
        },
      ],
    },
    {
      nodeId: 'version3',
      versionId: '3.5.2',
      releaseDate: new Date('2024-03-22'),
      features: [
        {
          title: 'AI Recommendations',
          description: 'Get personalized content suggestions.',
          category: 'AI',
        },
        {
          title: 'Accessibility Enhancements',
          description: 'Better support for screen readers.',
          category: 'UI/UX',
        },
      ],
      reviews: [
        {
          nodeId: 'v3review1',
          reviewId: 'r6',
          score: 5,
          reviewer: 'Frank',
          reviewDate: new Date('2024-03-23'),
          content: 'AI recs are spot on!',
          appVersion: '3.5.2',
        },
        {
          nodeId: 'v3review2',
          reviewId: 'r7',
          score: 1,
          reviewer: 'Grace',
          reviewDate: new Date('2024-03-24'),
          content: 'Accessibility still needs work.',
          appVersion: '3.5.2',
        },
        {
          nodeId: 'v3review3',
          reviewId: 'r8',
          score: 4,
          reviewer: 'Hugo',
          reviewDate: new Date('2024-03-25'),
          content: 'Impressive improvements!',
          appVersion: '3.5.2',
        },
        {
          nodeId: 'v3review4',
          reviewId: 'r9',
          score: 3,
          reviewer: 'Isla',
          reviewDate: new Date('2024-03-25'),
          content: 'Could be better with toggle options for AI.',
          appVersion: '3.5.2',
        },
      ],
    },
  ],
  links: [
    { sourceNodeId: 'version1', targetNodeId: 'v1review1' },
    { sourceNodeId: 'version1', targetNodeId: 'v1review2' },
    { sourceNodeId: 'version2', targetNodeId: 'v2review1' },
    { sourceNodeId: 'version2', targetNodeId: 'v2review2' },
    { sourceNodeId: 'version2', targetNodeId: 'v2review3' },
    { sourceNodeId: 'version3', targetNodeId: 'v3review1' },
    { sourceNodeId: 'version3', targetNodeId: 'v3review2' },
    { sourceNodeId: 'version3', targetNodeId: 'v3review3' },
    { sourceNodeId: 'version3', targetNodeId: 'v3review4' },
  ],
};
