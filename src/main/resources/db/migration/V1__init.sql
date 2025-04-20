CREATE TABLE IF NOT EXISTS history_call_request (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP,
    path_endpoint VARCHAR(255),
    arguments VARCHAR(255),
    response TEXT
);