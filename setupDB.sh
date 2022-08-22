#!/bin/bash

sqlite3 database.db ".read src/main/resources/sql/SetupPrestamos.sql"

sqlite3 database.db ".read src/main/resources/sql/SetupPerfiles.sql"

sqlite3 database.db ".read src/main/resources/sql/SetupContenidos.sql"
