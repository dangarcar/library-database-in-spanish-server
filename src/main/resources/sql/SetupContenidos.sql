/*
Empiezo creando las tablas de detalles, necesarias para las foreign keys de los Contenidos
*/

CREATE TABLE IF NOT EXISTS "Detalles_Audiovisual" (
	"ID" SERIAL PRIMARY KEY,
	"Duracion" REAL,
	"IsVideo" BOOLEAN NOT NULL,
	"EdadRecomendada" INT,
	"Calidad" INT
);

CREATE TABLE IF NOT EXISTS "Detalles_Libros" (
	"ID" SERIAL PRIMARY KEY,
	"Paginas" INT,
	"Editorial" VARCHAR(64),
	"ISBN" VARCHAR(64)
);

/*
Creo la tabla principal de los Contenidos
*/
CREATE TABLE IF NOT EXISTS "Contenidos" (
	"ID" SERIAL PRIMARY KEY,
	"Titulo" VARCHAR(64),
	"Autor"	VARCHAR(64),
	"Descripcion" VARCHAR(64),
	"Year"	INT,
	"Idioma" VARCHAR(64),
	"Soporte" VARCHAR(64),
	"DiasDePrestamo" INT,
	"Prestable"	BOOL NOT NULL,
	"Disponible" BOOL NOT NULL,
	"IDLibro" INT DEFAULT NULL,
	"IDAudiovisual" INT DEFAULT NULL,
	"Imagen" VARCHAR(64) DEFAULT NULL,
	CONSTRAINT "FK_Audiovisual" FOREIGN KEY("IDAudiovisual") REFERENCES "Detalles_Audiovisual"("ID"),
	CONSTRAINT "FK_Libro" FOREIGN KEY("IDLibro") REFERENCES "Detalles_Libros"("ID")
);

/*
Creo el índice para la búsqueda de contenidos
*/
CREATE INDEX IF NOT EXISTS "Contenidos_IDX" ON "Contenidos" USING GIN (to_tsvector('spanish',
	coalesce(cast("ID" as VARCHAR),'') || ' ' ||
	coalesce("Titulo",'') || ' ' ||
	coalesce("Autor",'') || ' ' ||
	coalesce("Descripcion",'')
));