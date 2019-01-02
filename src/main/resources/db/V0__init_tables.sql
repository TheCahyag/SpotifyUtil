CREATE TABLE spotify.tracks
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  name VARCHAR(200),
  href VARCHAR(1000),
  uri VARCHAR(50),
  explicit BOOLEAN,
  type VARCHAR(32),
  popularity INT,
  duration_ms INT,
  disc_number INT,
  is_playable BOOLEAN,
  preview_url VARCHAR(1000),
  track_number INT,
  album_id VARCHAR(32),
  artist_ids VARCHAR(1000)
);
CREATE UNIQUE INDEX table_name_id_uindex ON spotify.tracks (id);
