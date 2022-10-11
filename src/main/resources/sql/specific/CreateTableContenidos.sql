CREATE TABLE IF NOT EXISTS "Contenidos" (
	"ID"	INTEGER,
	"Titulo"	REAL NOT NULL,
	"Autor"	TEXT,
	"Descripcion"	TEXT,
	"Year"	INTEGER,
	"Idioma"	TEXT,
	"Soporte"	TEXT,
	"DiasDePrestamo"	INTEGER NOT NULL,
	"Prestable"	INTEGER NOT NULL,
	"Disponible"	INTEGER DEFAULT 0,
	"IDLibro"	INTEGER DEFAULT NULL,
	"IDAudiovisual"	INTEGER DEFAULT NULL,
	"Imagen"	TEXT DEFAULT NULL,
	CONSTRAINT "FK_Audiovisual" FOREIGN KEY("IDAudiovisual") REFERENCES "Detalles_Audiovisual"("ID") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "FK_Libro" FOREIGN KEY("IDLibro") REFERENCES "Detalles_Libros"("ID") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "PK_ID" PRIMARY KEY("ID" AUTOINCREMENT)
);