# Library Database In Spanish (SERVER)

Esta aplicación hecha con Spring Boot, Maven y SQLite es el servidor de la para el [cliente](https://github.com/dangarcar/library-database-in-spanish) de esta aplicación.

Sirve como servidor de una app que controla los préstamos, contenidos y usuarios de una biblioteca, teniendo funcionalidades para usuarios anónimos, clientes de la biblioteca, trabajadores de la biblioteca y administradores del sistema.

## Tecnologías usadas
- [Java](https://www.java.com/en/) 17.0
- [Spring Boot](https://spring.io/projects/spring-boot/) 2.7
- [Spring Security](https://spring.io/projects/spring-security) 5.7
- [Maven](https://maven.apache.org/) 3.8
- [SQLite JDBC](https://github.com/xerial/sqlite-jdbc) 3.39
- [Java JWT](https://github.com/auth0/java-jwt) 4.0

## Cómo ejecutarlo
1. En `src/main/resources/application.properties`, cambia el valor de `jwt.issuer` a tu compañía, `jwt.secret` a una string de al menos 256 bits.

2. Abre una terminal en el directorio del proyecto.

3. Ejecuta el siguiente comando para ejecutar la aplicación:
>```console 
>./mvnw spring-boot:run
>```

## Cómo consumir la api
- Primero lógate en `/auth/login` o `/auth/signup` si no está en la base de datos todavía.
- Guarda el access token y el refresh token.
- Cuando hagas una petición, añade el header Authorization con `Bearer {access token}`.
- Si te caduca el access token, debes hacer refresh con el refresh token en `/auth/refresh/token/{refresh_token}`.
- Si te caduca el refresh token, debes logarte de nuevo en `/auth/login`.
