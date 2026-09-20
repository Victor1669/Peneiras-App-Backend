CREATE TABLE refresh_token (
                               id UUID PRIMARY KEY,
                               token VARCHAR(512) NOT NULL UNIQUE,
                               player_id UUID NOT NULL,
                               expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
                               revoked BOOLEAN NOT NULL DEFAULT FALSE,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL,

                               CONSTRAINT fk_refresh_token_player
                                   FOREIGN KEY (player_id)
                                       REFERENCES player(id)
);