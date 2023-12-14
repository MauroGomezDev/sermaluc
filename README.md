# SERMALUC
Repositorio para desafio Sermaluc

# Proyecto de Microservicio de Creación de Usuarios

Este proyecto de microservicio fue desarrollado para aplicar a una vacante de desarrollador para la empresa Sermaluc.

El proyecto base fue creado en el sitio https://start.spring.io/

## Funcionalidad
Proporciona funcionalidades para la creación, consulta y modificacion de usuarios (GET, POST, PUT, DELETE, PATCH), con validación de datos y manejo de excepciones. 
El servicio persiste los datos en una base de datos H2 en memoria y utiliza tokens JWT para la posterior autenticación.

## Requisitos

- Java 17
- Maven 3.8.1
- Springboot 3.2.0
- Dependencias de Spring Boot (Spring Web, Spring Data JPA, Spring Security lombok, jaxb, jackson, etc)

## Configuración

1. Clona el repositorio desde [GitHub](https://github.com/MauroGomezDev/sermaluc/tree/main) o descarga el código fuente.

2. Asegúrate de tener Java 17 instalado en tu sistema.

3. Edita el archivo `application.properties` para configurar la base de datos y otras propiedades según tus necesidades.
   Actualmente tiene configurada una base de datos H2.

## Ejecución

Para ejecutar el proyecto. Abre una terminal en el directorio raíz del proyecto y ejecuta el siguiente comando:

Terminal cmd: (...\target>)
java -jar desafio-0.0.1-SNAPSHOT.jar


** Si el comando se ejecuta correctamente debe mostrar un los similar al siguiente:
2023-12-04 11:25:17.570  INFO 113148 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2023-12-04 11:25:18.372  WARN 113148 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2023-12-04 11:25:19.154  INFO 113148 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-12-04 11:25:19.598  INFO 113148 --- [           main] c.sermaluc.ejercicio.EjercicioApplication  : Started DesafioApplication in 8.21 seconds (JVM running for 8.97)

La aplicación se ejecutará en http://localhost:8080.

## Endpoints
post http://localhost:8080/api/evaluacion/singup   : Creacion de token de autenticacion

post http://localhost:8080/api/evaluacion/user   : Creacion de usuario

get http://localhost:8080/api/evaluacion/list          : Lista usuarios registrados

get http://localhost:8080/api/evaluacion/usr-by-email {"email": "mgomez@gmail.com"} : Lee un usuario por mail pasado como parametro formato json

put http://localhost:8080/api/evaluacion/update-usr : Modifica un registro de usuario

delete http://localhost:8080/api/evaluacion/delete-usr : elimina un usuario por su correo

patch http://localhost:8080/api/evaluacion/last-login : Modifica un registro del usuario (fecha ultimo login)

## Validaciones
Para la creacion de usuarios, la aplicacion solo permite formato JSON. 
Validará el formato del correo electrónico y la contraseña. 
Para validar email, se toma el valor de la expresion regular desde el archivo de configuracion applicaion.properties
Retorna un usuario con los campos id, created date, modified date, lastLogin, token y isActive.

## Prueba de integración Swagger
Se implemento swagger para la documentacion de la API, pueder entrar en http://localhost:8080/swagger-ui/index.html 
(Esto no esta funcionando ya que tuve probemas con las versiones y no alcance a terminar) 

Ademas, Se agrego en este mismo repositorio la coleccion de endpoints para ser importdado en postman.
Nombre archivo: sermaluc.postman_collection.json

** Ejemplo de solicitud POST para crear un usuario:

POST http://localhost:8080/sign-up
{
  "name": "Lina Gomez",
  "email": "lgomez@gmail.com",
  "password": "Qwerty12",
  "phones": [
    {
      "number": 123456789,
      "citycode": 8320000,
      "countrycode": "SCL"
    }
  ]
}

** Ejemplo de respuesta del servicio: Httpstatus + un json con la siguiente estructura:
{"id":"c12cb258-d9e2-4d99-864c-f817b3f9c014","created":[2023,11,11],"lastLogin":[2023,11,11],"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjMTJjYjI1OC1kOWUyLTRkOTktODY0Yy1mODE3YjNmOWMwMTQiLCJuYW1lIjoiTWF1cm8gR29tZXoiLCJleHAiOjE2OTk2NzE2MDAsImlhdCI6MTY5OTY3MTYwMCwiZW1haWwiOiJtZ29tZXpAZ21haWwuY29tIn0.3HfUrO94V-Jx2-Pmg-cXcKJt97doMRlA1SGR_G_B60Y","active":true}

** En caso de error el json de respuesta es el siguiente:
{"mensaje":"Mensaje de error"}

## Diagramas
Se creo un diagrama de secuencia que encuentra en este mismo repositorio.
Diagrama Secuencia API evaluacion.dr (formato draw-io)

## Contribución
Si deseas contribuir a este proyecto, por favor abre un problema o envía una solicitud de extracción.

## Licencia
N/A

## Script Base de datos
N/A ya que el requerimiento es base de datos en memoria.

## "Pruebas unitarias" 
Se desarrollaron 2 tes unitarios y se utilizo junit y mockito
- EvaluacionControlletTests.java
- UserServiceImplTest.java








