CREATE TABLE IF NOT EXISTS "Tokens" (
	"Token"	TEXT,
	"ExpDate"	INTEGER NOT NULL,
	"Username"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("Token")
) WITHOUT ROWID;