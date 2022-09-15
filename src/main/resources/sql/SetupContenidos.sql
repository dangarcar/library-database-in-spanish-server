/*
Empiezo creando las tablas de detalles, necesarias para las foreign keys de los Contenidos
*/
CREATE TABLE IF NOT EXISTS "Detalles_Audiovisual" (
	"ID"	INTEGER,
	"Duracion"	REAL,
	"IsVideo"	INTEGER NOT NULL,
	"EdadRecomendada"	INTEGER,
	"Calidad"	INTEGER,
	CONSTRAINT "PK_Detalles_Audiovisual" PRIMARY KEY("ID" AUTOINCREMENT)
);


CREATE TABLE IF NOT EXISTS "Detalles_Libros" (
	"ID"	INTEGER NOT NULL,
	"Paginas"	INTEGER,
	"Editorial"	TEXT,
	"ISBN"	TEXT,
	CONSTRAINT "PK_Detalles_Libros" PRIMARY KEY("ID" AUTOINCREMENT)
);

/*
Creo la tabla principal de los Contenidos
*/
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
	"FechaDisponibilidad"	TEXT,
	"Disponible"	INTEGER DEFAULT 0,
	"IDLibro"	INTEGER DEFAULT NULL,
	"IDAudiovisual"	INTEGER DEFAULT NULL,
	CONSTRAINT "FK_Audiovisual" FOREIGN KEY("IDAudiovisual") REFERENCES "Detalles_Audiovisual"("ID") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "FK_Libro" FOREIGN KEY("IDLibro") REFERENCES "Detalles_Libros"("ID") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT "PK_ID" PRIMARY KEY("ID" AUTOINCREMENT)
);

/*
Creo la tabla para la busqueda de contenidos
*/
CREATE VIRTUAL TABLE IF NOT EXISTS "BusquedaContenidos" USING fts5(
    "ID",
    "Titulo",
    "Autor",
    "Descripcion"
);