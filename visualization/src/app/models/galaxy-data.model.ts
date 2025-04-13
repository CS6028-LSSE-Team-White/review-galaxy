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
      nodeId: "version_5_9_0_3502",
      versionId: "5.9.0.3502",
      releaseDate: new Date("2024-01-21T07:27:55"),
      features: [],
      reviews: [
        {
          nodeId: "review_a9dcc643-7a8b-4ead-866b-13324940ab8b",
          reviewId: "a9dcc643-7a8b-4ead-866b-13324940ab8b",
          score: 1,
          reviewer: "Nadeeshani Hewage",
          reviewDate: new Date("2024-01-21T07:27:55"),
          content: "I cannot update this. I don't know why? It deducts my data but does not update properly.",
          appVersion: "5.9.0.3502"
        },
        {
          nodeId: "review_dba69011-9c53-47d7-aeb3-c79ea8e56e95",
          reviewId: "dba69011-9c53-47d7-aeb3-c79ea8e56e95",
          score: 1,
          reviewer: "Jitendra mishra",
          reviewDate: new Date("2024-01-19T04:38:13"),
          content: "Poor",
          appVersion: "5.9.0.3502"
        }
      ]
    },
    // more releases...
  ],
  links: [
    {
      sourceNodeId: "version_5_9_0_3502",
      targetNodeId: "review_a9dcc643-7a8b-4ead-866b-13324940ab8b"
    },
    {
      sourceNodeId: "version_5_9_0_3502",
      targetNodeId: "review_dba69011-9c53-47d7-aeb3-c79ea8e56e95"
    }
  ]
};
