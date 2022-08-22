CREATE TABLE "Detalles_Audiovisual" (
	"ID"	INTEGER,
	"Duracion"	REAL,
	"IsVideo"	INTEGER NOT NULL,
	"EdadRecomendada"	INTEGER,
	"Calidad"	INTEGER,
	CONSTRAINT "PK_Detalles_Audiovisual" PRIMARY KEY("ID" AUTOINCREMENT)
)