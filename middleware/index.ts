import express, { Request, Response } from "express";
import { Client } from "pg";

// Create a new express application instance
const app = express();

// Enable CORS
import cors from "cors";
app.use(cors());

// Set the network port
const port = process.env.PORT || 3000;

// Define the root path with a greeting message
app.get("/", (req: Request, res: Response) => {
  res.json({ message: "200 OK" });
});

app.get("/database-health", (req: Request, res: Response) => {
  client
    .query("SELECT NOW()")
    .then((result) => {
      res.json({ message: "Database is healthy", time: result.rows[0].now });
    })
    .catch((err) => {
      console.error("Database query error", err);
      res.status(500).json({ message: "Database is not healthy" });
    });
});

// Database connection details
const client = new Client({
  user: process.env.DB_USER || "review_galaxy_user",
  host: process.env.DB_HOST || "172.20.0.4",
  database: process.env.DB_NAME || "review_galaxy_db",
  password: process.env.POSTGRES_PASSWORD,
  port: parseInt(process.env.DB_PORT || "5432", 10),
});

// Start the Express server
app.listen(port, () => {
  console.log(`The server is running at http://localhost:${port}`);

  client
    .connect()
    .then(() => console.log("Connected to the PostgreSQL database"))
    .catch((err) => console.error("Connection error", err));
});
