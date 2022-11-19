/*
Creo la tabla principal de los perfiles
*/
CREATE TABLE IF NOT EXISTS "Perfiles" (
	"ID" SERIAL PRIMARY KEY,
	"Nombre" VARCHAR(64) NOT NULL,
	"FechaDeNacimiento"	VARCHAR(64) NOT NULL,
	"Password" VARCHAR(512) NOT NULL,
	"CorreoElectronico"	VARCHAR(64) NOT NULL UNIQUE,
	"Roles"	VARCHAR(32) NOT NULL
);


/*
Creo el índice para la busqueda de perfiles
*/
CREATE INDEX IF NOT EXISTS "Perfiles_IDX" ON "Perfiles" USING GIN (to_tsvector('spanish',
	coalesce(cast("ID" as VARCHAR),'') || ' ' ||
	coalesce("Nombre",'') || ' ' ||
	coalesce("CorreoElectronico",'')
));