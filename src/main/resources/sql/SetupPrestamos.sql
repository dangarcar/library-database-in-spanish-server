CREATE TABLE IF NOT EXISTS "Prestamos" (
	"ID"	INTEGER,
	"IDContenido"	INTEGER NOT NULL,
	"IDPerfil"	INTEGER NOT NULL,
	"FechaHoraPrestamo"	TEXT NOT NULL,
	"FechaHoraDevolucion"	TEXT,
	"DiasDePrestamo"	INTEGER NOT NULL,
	"Devuelto"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("ID" AUTOINCREMENT)
);