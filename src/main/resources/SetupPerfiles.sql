/*
Creo la tabla principal de los perfiles
*/
CREATE TABLE "Perfiles" (
	"Nombre"	TEXT NOT NULL,
	"FechaDeNacimiento"	TEXT NOT NULL,
	"Password"	TEXT NOT NULL,
	"CorreoElectronico"	TEXT NOT NULL UNIQUE,
	"Admin"	INTEGER NOT NULL DEFAULT 0,
	"ID"	INTEGER,
	PRIMARY KEY("ID" AUTOINCREMENT)
);

/*
Creo la tabla de busqueda
*/
CREATE VIRTUAL TABLE BusquedaPerfiles USING fts5(
    "ID",
    "Nombre",
    "CorreoElectronico"
);

/*
Creo los triggers para que al hacer INSERT, UPDATE o DELETE en Perfiles se actualice BusquedaPerfiles también
*/
CREATE TRIGGER TPerfilesDelete
	AFTER DELETE
	ON Perfiles
BEGIN 
	DELETE FROM BusquedaPerfiles WHERE old.ID = ID;
END;

CREATE TRIGGER TPerfilesInsert
	AFTER INSERT
	ON Perfiles
BEGIN 
	INSERT INTO BusquedaPerfiles
	SELECT ID, Nombre, CorreoElectronico
	FROM Perfiles
	WHERE Perfiles.ID = NEW.ID;
END;

CREATE TRIGGER TPerfilesUpdate
	AFTER UPDATE ON Perfiles
BEGIN 
	DELETE FROM BusquedaPerfiles WHERE ID = NEW.ID;
	
	INSERT INTO BusquedaPerfiles
	SELECT ID,Nombre,CorreoElectronico
	FROM Perfiles
	WHERE ID = NEW.ID;
END;