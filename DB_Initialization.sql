CREATE SCHEMA gameshelf;

CREATE TYPE gameshelf.platform AS ENUM ('PC', 'Xbox', 'Playstation', 'Gamecube', 'Switch');
CREATE TYPE gameshelf.status AS ENUM ('BACKLOG', 'PLAYING', 'COMPLETED', 'DROPPED');

CREATE TABLE gameshelf.games(
id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
title VARCHAR(100) NOT NULL,
platform gameshelf.platform NOT NULL,
status gameshelf.status NOT NULL,
notes TEXT,
date_added DATE NOT NULL DEFAULT CURRENT_DATE,
date_completed DATE
);

CREATE TABLE gameshelf.genres(
id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE,
description TEXT
);

