package com.alura.literalura.principal;
import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n*** LiterAlura ***
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            if(teclado.hasNextInt()){
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1 -> buscarLibro();
                    case 2 -> listarLibrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivosPorAnio();
                    case 5 -> listarLibrosPorIdioma();
                    case 0 -> System.out.println("Cerrando la aplicación...");
                    default -> System.out.println("Opción inválida");
                }
            } else {
                System.out.println("Por favor ingrese un número válido.");
                teclado.nextLine();
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String titulo = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "%20"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        DatosLibro datosLibro = datos.resultados().get(0);
        DatosAutor datosAutor = datosLibro.autores().get(0);

        Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

        try {
            Libro libro = new Libro(datosLibro, autor);
            libroRepository.save(libro);
            System.out.println("Libro guardado con éxito: " + libro.getTitulo());
        } catch (Exception e) {
            System.out.println("El libro ya está registrado.");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(l -> System.out.println("Título: " + l.getTitulo() + " | Autor: " + l.getAutor().getNombre()));
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(a -> System.out.println("Autor: " + a.getNombre()));
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        if(teclado.hasNextInt()){
            int anio = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autores = autorRepository.autoresVivosEnUnDeterminadoAnio(anio);
            if(autores.isEmpty()){
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                autores.forEach(a -> System.out.println("Autor: " + a.getNombre()));
            }
        } else {
            System.out.println("Año inválido.");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (ej. 'en' para inglés, 'es' para español):");
        String idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if(libros.isEmpty()){
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(l -> System.out.println("Título: " + l.getTitulo()));
        }
    }
}