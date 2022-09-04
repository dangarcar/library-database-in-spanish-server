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
	"Año"	INTEGER,
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

/*
Creo los triggers para que cuando haga UPDATE, DELETE o INSERT en Contenidos se actualice la tabla de BusquedaContenidos
*/
CREATE TRIGGER IF NOT EXISTS TContenidosDelete
	AFTER DELETE
	ON Contenidos
BEGIN 
	DELETE FROM BusquedaContenidos WHERE old.ID = ID;
END;


CREATE TRIGGER IF NOT EXISTS TContenidosInsert
	AFTER INSERT ON Contenidos
BEGIN 
	INSERT INTO BusquedaContenidos
	SELECT ID,Titulo,Autor,Descripcion
	FROM Contenidos
	WHERE Contenidos.ID = NEW.ID;
END;


CREATE TRIGGER IF NOT EXISTS TContenidosUpdate
	AFTER UPDATE ON Contenidos
BEGIN 
	DELETE FROM BusquedaContenidos WHERE ID = NEW.ID;
	
	INSERT INTO BusquedaContenidos
	SELECT ID,Titulo,Autor,Descripcion
	FROM Contenidos
	WHERE ID = NEW.ID;
END;