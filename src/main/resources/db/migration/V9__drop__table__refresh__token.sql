DROP TABLE refresh_token;

CREATE TABLE refresh_token (
                               id UUID PRIMARY KEY,
                               token VARCHAR(512) NOT NULL UNIQUE,

                               player_id UUID,
                               clube_id UUID,

                               expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
                               revoked BOOLEAN NOT NULL DEFAULT FALSE,
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL,

                               CONSTRAINT fk_refresh_token_player
                                   FOREIGN KEY (player_id)
                                       REFERENCES player(id),

                               CONSTRAINT fk_refresh_token_clube
                                   FOREIGN KEY (clube_id)
                                       REFERENCES clube(id),

                               CONSTRAINT chk_refresh_token_owner
                                   CHECK (
                                       (player_id IS NOT NULL AND clube_id IS NULL)
                                           OR
                                       (player_id IS NULL AND clube_id IS NOT NULL)
                                       )
);