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

/*
Creo los triggers para que al hacer INSERT, UPDATE o DELETE en Perfiles se actualice BusquedaPerfiles también
*/
CREATE TRIGGER IF NOT EXISTS TPerfilesDelete
	AFTER DELETE
	ON Perfiles
BEGIN 
	DELETE FROM BusquedaPerfiles WHERE old.ID = ID;
END;

CREATE TRIGGER IF NOT EXISTS TPerfilesInsert
	AFTER INSERT
	ON Perfiles
BEGIN 
	INSERT INTO BusquedaPerfiles
	SELECT ID, Nombre, CorreoElectronico
	FROM Perfiles
	WHERE Perfiles.ID = NEW.ID;
END;

CREATE TRIGGER IF NOT EXISTS TPerfilesUpdate
	AFTER UPDATE ON Perfiles
BEGIN 
	DELETE FROM BusquedaPerfiles WHERE ID = NEW.ID;
	
	INSERT INTO BusquedaPerfiles
	SELECT ID,Nombre,CorreoElectronico
	FROM Perfiles
	WHERE ID = NEW.ID;
END;