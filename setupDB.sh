#!/bin/bash

sqlite3 database.db ".read src/main/resources/sql/SetupPrestamos.sql"
echo "Se creó la base de datos de los préstamos"

sqlite3 database.db ".read src/main/resources/sql/SetupPerfiles.sql"
echo "Se creó la base de datos de los perfiles"

sqlite3 database.db ".read src/main/resources/sql/SetupContenidos.sql"
echo "Se creó la base de datos de los contenidos"

sqlite3 tokens.db ".read src/main/resources/sql/SetupTokens.sql"
echo "Se creó la base de datos de los tokens"