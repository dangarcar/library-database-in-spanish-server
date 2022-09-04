/*
Creo la tabla principal de los perfiles
*/
CREATE TABLE IF NOT EXISTS "Perfiles" (
	"Nombre"	TEXT NOT NULL,
	"FechaDeNacimiento"	TEXT NOT NULL,
	"Password"	TEXT NOT NULL,
	"CorreoElectronico"	TEXT NOT NULL UNIQUE,
	"Roles"	TEXT NOT NULL,
	"ID"	INTEGER,
	PRIMARY KEY("ID" AUTOINCREMENT)
);

/*
Creo la tabla de busqueda
*/
CREATE VIRTUAL TABLE IF NOT EXISTS BusquedaPerfiles USING fts5(
    "ID",
    "Nombre",
    "CorreoElectronico"
);