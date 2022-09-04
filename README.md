# Library Database In Spanish (SERVER)

Esta aplicación hecha con [Spring Boot](https://spring.io/projects/spring-boot), Maven y SQLite es el servidor de la para el [cliente](https://github.com/dangarcar/library-database-in-spanish) de esta aplicación

#### Requisitos previos
- Tener [Java](https://www.java.com/en/) instalado (Como mínimo la versión JRE 17)
- Tener [Maven](https://maven.apache.org/) instalado (Como mínimo versión 3.8)
- Tener [Sqlite](https://www.sqlite.org/) instalado (Como mínimo version 3.38)

#### Cómo ejecutarlo
1. Si es la primera vez que ejecutas la aplicación, ejecuta el archivo setupDB.cmd (Windows) o setupDB.sh (Linux) para crear el archivo de la base de datos. Asegúrate de tener sqlite3 en las variables del entorno.

2. En `src/main/resources/application.properties`, cambia el valor de `jwt.issuer` a tu compañía, `jwt.secret` a una string de al menos 256 bits.

3. Abre una terminal en el directorio del proyecto.

4. Ejecuta el siguiente comando para hacer la build de la aplicación:
>```console
>mvn clean package
>```

5. Ejecuta el siguiente comando para ejecutar la aplicación:
>```console 
>java -jar target/database-server-(version).jar
>```

#### Cómo consumir la api
- Primero lógate en `/auth/login` o `/auth/signup` si no está en la base de datos todavía.
- Guarda el access token y el refresh token.
- Cuando hagas una petición, añade el header Authorization con `Bearer {access token}`.
- Si te caduca el access token, debes hacer refresh con el refresh token en `/auth/refresh/token/{refresh_token}`.
- Si te caduca el refresh token, debes logarte de nuevo en `/auth/login`.