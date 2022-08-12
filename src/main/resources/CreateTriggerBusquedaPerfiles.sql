CREATE TRIGGER TPerfilesDelete
	AFTER DELETE
	ON Perfiles
BEGIN 
	DELETE FROM BusquedaPerfiles WHERE old.ID = ID;
END

CREATE TRIGGER TPerfilesInsert
	AFTER INSERT
	ON Perfiles
BEGIN 
	INSERT INTO BusquedaPerfiles
	SELECT ID, Nombre, CorreoElectronico
	FROM Perfiles
	WHERE Perfiles.ID = NEW.ID;
END

CREATE TRIGGER TPerfilesUpdate
	AFTER UPDATE ON Perfiles
BEGIN 
	DELETE FROM BusquedaPerfiles WHERE ID = NEW.ID;
	
	INSERT INTO BusquedaPerfiles
	SELECT ID,Nombre,CorreoElectronico
	FROM Perfiles
	WHERE ID = NEW.ID;
END