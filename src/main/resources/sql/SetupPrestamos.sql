CREATE TABLE IF NOT EXISTS "Prestamos" (
	"ID" SERIAL PRIMARY KEY,
	"IDContenido" INT NOT NULL,
	"IDPerfil" INT NOT NULL,
	"FechaHoraPrestamo"	VARCHAR(64) NOT NULL,
	"FechaHoraDevolucion" VARCHAR(64),
	"DiasDePrestamo" INT NOT NULL,
	"Devuelto" BOOL NOT NULL
);