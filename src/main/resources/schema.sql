CREATE TABLE todo (
    id INTEGER
    ,title TEXT NOT NULL
    ,details TEXT
    ,finished BOOLEAN NOT NULL
);

CREATE SEQUENCE seq_todo;
