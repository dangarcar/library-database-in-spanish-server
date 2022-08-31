CREATE TRIGGER IF NOT EXISTS TContenidosDelete
	AFTER DELETE
	ON Contenidos
BEGIN 
	DELETE FROM BusquedaContenidos WHERE old.ID = ID;
END

CREATE TRIGGER IF NOT EXISTS TContenidosInsert
	AFTER INSERT ON Contenidos
BEGIN 
	INSERT INTO BusquedaContenidos
	SELECT ID,Titulo,Autor,Descripcion
	FROM Contenidos
	WHERE Contenidos.ID = NEW.ID;
END

CREATE TRIGGER IF NOT EXISTS TContenidosUpdate
	AFTER UPDATE ON Contenidos
BEGIN 
	DELETE FROM BusquedaContenidos WHERE ID = NEW.ID;
	
	INSERT INTO BusquedaContenidos
	SELECT ID,Titulo,Autor,Descripcion
	FROM Contenidos
	WHERE ID = NEW.ID;
END