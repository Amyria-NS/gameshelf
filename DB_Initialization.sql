CREATE SCHEMA gameshelf;

CREATE TYPE gameshelf.platform AS ENUM ('PC', 'XBOX', 'PLAYSTATION', 'GAMECUBE', 'SWITCH');
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

CREATE TABLE gameshelf.game_genres(
    game_id INTEGER NOT NULL REFERENCES gameshelf.games(id) ON DELETE CASCADE,
    genre_id INTEGER NOT NULL REFERENCES gameshelf.genres(id) ON DELETE CASCADE,
    PRIMARY KEY (game_id, genre_id)
);

CREATE TABLE gameshelf.game_images(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    game_id INTEGER NOT NULL UNIQUE REFERENCES gameshelf.games(id) ON DELETE CASCADE,
    image_path VARCHAR(500) NOT NULL
);