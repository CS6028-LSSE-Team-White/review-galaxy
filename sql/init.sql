CREATE DATABASE review_galaxy_db;
GRANT ALL ON DATABASE review_galaxy_db TO review_galaxy_user;

\connect review_galaxy_db;

-- SET TIMEZONE (America/New_York)
ALTER DATABASE review_galaxy_db SET timezone TO 'UTC';

-- TABLE DEFINITIONS

-- INIT. DATASET