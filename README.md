# 📚 LiterAlura - Catálogo de Libros

¡Bienvenida a mi **LiterAlura**! Este es un proyecto parte del Challenge de Java de Alura Latam. Es una aplicación de consola que permite gestionar un catálogo de libros consumiendo datos reales de la API [Gutendex](https://gutendex.com/).

---

## 🚀 Funcionalidades
El sistema ofrece las siguientes opciones:

1.  **Buscar libro por título:** Obtiene datos directamente de la API de Gutendex y los registra en la base de datos.
2.  **Listar libros registrados:** Muestra todos los libros que has guardado localmente.
3.  **Listar autores registrados:** Muestra la lista de autores de los libros guardados.
4.  **Listar autores vivos en un determinado año:** Filtra autores según un año específico proporcionado por el usuario.
5.  **Listar libros por idioma:** Permite buscar libros guardados filtrando por siglas de idioma (es, en, fr, pt).

## 📦 Configuración del Proyecto
Para ejecutar este proyecto localmente, asegúrate de tener configurado tu archivo `application.properties` con las credenciales de tu PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```
## 📸 Captura de pantalla
<img width="1301" height="777" alt="image" src="https://github.com/user-attachments/assets/73b7474e-86e0-40d4-bd70-5617b7a2165d" />
