# Library Database In Spanish (SERVER)

Esta aplicación hecha con Spring Boot, Maven y SQLite es el servidor de la para el [cliente](https://github.com/dangarcar/library-database-in-spanish) de esta aplicación.

Sirve como servidor de una app que controla los préstamos, contenidos y usuarios de una biblioteca, teniendo funcionalidades para usuarios anónimos, clientes de la biblioteca, trabajadores de la biblioteca y administradores del sistema.

## Tecnologías usadas
- [Java](https://www.java.com/en/) 17.0
- [Spring Boot](https://spring.io/projects/spring-boot/) 2.7
- [Spring Security](https://spring.io/projects/spring-security) 5.7
- [Spring Rest Docs](https://spring.io/projects/spring-restdocs/) 2.0
- [Maven](https://maven.apache.org/) 3.8
- [SQLite JDBC](https://github.com/xerial/sqlite-jdbc) 3.39
- [Java JWT](https://github.com/auth0/java-jwt) 4.0

## Empezando a usar Library Database In Spanish (SERVER)
Al empezar a usar el programa, se creará un administrador de base para poder acceder a toda la API, pero se recomienda cambiarlo por uno mismo, el administrador del sitema de la biblioteca.

1. En `+src/main/resources/application.properties+`, cambia el valor de `jwt.issuer` a tu compañía, `jwt.secret` a una string de al menos 256 bits.

2. Abre una terminal en el directorio del proyecto.

3. Ejecuta el siguiente comando para ejecutar la aplicación:
```bash
./mvnw spring-boot:run
```

Esto creará un perfil administrador con las siguientes credenciales si no existe ningún administrador en la base de datos:
- Username: admin
- Password: admin

4. Ahora ve a un cliente REST y envíe una petición *POST* a `/auth/login` incluyendo el siguiente cuerpo de solicitud:
```json
{
	username:admin
	password:admin
}
```

Conseguirá un par de tokens. Guarde el access token.

5. Envíe una petición *POST* a `/perfiles` incluyendo cuerpo de solicitud con la siguiente estructura:
```json
{
  "email" : {Ponga su email},
  "password" : {Ponga su contraseña},
  "nombre" : {Ponga su nombre},
  "fechaNacimiento" : {Ponga su fecha de nacimiento codificada en ISO-8601}
  "role" : "ROLE_ADMIN"
}
```
También hay que incluir el header Authorization con el token "Bearer +{access token}"

6. Envíe una petición *POST* a `/auth/login` incluyendo el siguiente cuerpo de solicitud:
```json
{
	username:{Ponga el email que ha registrado anteriormente}
	password:{Ponga la contraseña que ha registrado anteriormente}
}
```

Guarde los access y refresh tokens para poder acceder a toda la API.

7. Por último, elimine el administrador por defecto de la base de datos enviando una petición a `user/delete` añadiendo el header Authorization con el token "Bearer +{access token(del administrador por defecto)}.

8. Ahora ya podrá navegar por toda la API siendo el administrador y sin el riesgo de que alguien entre con el adminstrador por defecto a su biblioteca.

## Más información
Para más información sobre este proyecto y su API, mire la documentación, situada en docs/index.html.