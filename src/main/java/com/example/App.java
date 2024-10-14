package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        // Determinar la frecuencia de ocurrencia de una palabra en un array 
        // o lista de palabras

        List<String> palabras = Arrays.asList("Antonio", "Antonio", "Juan", "Antonio", "Marcos", "Ruben");

        Map<String, Long> m = new HashMap<>();

        for (String palabra : palabras) {
            Long ocurrencia = m.get(palabra);

            m.put(palabra, ocurrencia == null ? 1L : ++ocurrencia);
        }

        System.out.println(m);

        /* Lo anterior esta bien pero es antiguo, actualmente se puede obtener el mismo resultado
            * con OPERACIONES DE AGREGADO (Metodos de la clase Stream, tuberias, lambdas, metodos
            * por referencia, en fin, PROGRAMACION FUNCIONAL)
         */
        Map<String, Long> m2 = palabras.stream()
                .collect(Collectors.groupingBy(palabra -> palabra, Collectors.counting()));
        System.out.println(m2);

        List<Persona> personas = Persona.getPersonas();
        Map<Genero, List<Persona>> personasPorGenero = personas.stream()
                .collect(Collectors.groupingBy(persona -> persona.getGenero()));

        System.out.println(personasPorGenero);

        Map<Genero, String> nombresPorGenero = personas.stream()
                .collect(Collectors.groupingBy(Persona::getGenero,
                        Collectors.mapping(Persona::getNombre, Collectors.joining(";"))));

        System.out.println(nombresPorGenero);

        for (Map.Entry<Genero, List<Persona>> entry : personasPorGenero.entrySet()) {
            Genero key = entry.getKey();
            List<Persona> value = entry.getValue();

            System.out.println("Del genero " + key);
            System.out.println(" Personas con salario superior a 4000 ");

            for (Persona persona : value) {
                if (persona.getSalario() > 4000) {
                    System.out.println(persona);
                }
            }
        }

        personasPorGenero.entrySet().stream().forEach(entry -> {
            System.out.println("Del genero " + entry.getKey());
            System.out.println("Personas con salario suprior a 4000");

            entry.getValue().stream().filter(p -> p.getSalario() > 4000).forEach(System.out::println);
        });

        /* Ejercicio:
            * 
            * Crear una coleccion que agrupe Personas por Genero y Edad de la persona.
            * 
            * Recorrer la coleccion obtenida y mostras solamente las personas del Genero HOMBRE, que tengan un salario superior 
            * a la media.
         */

 /* Map<Genero, Map<Double, List<Persona>>> personasPorGeneroYSalario = personas.stream()
        .collect(Collectors.groupingBy(persona -> persona.getGenero(), Collectors.groupingBy(persona -> persona.getSalario())));

        personasPorGeneroYSalario.entrySet().stream().forEach(entry -> {
            System.out.println("Del genero " + entry.getKey());
            System.out.println("Personas con salario suprior a la media");

            // entry.getValue().entrySet().stream().mapToDouble(p -> p.getValue().stream().forEach(p1 -> p1.getSalario())).average().getAsDouble();
            entry.getValue().entrySet().stream().forEach(p -> p.getValue().stream().mapToDouble(p1 -> p1.getSalario()).average().getAsDouble());
        });

        System.out.println(personasPorGeneroYSalario); */
        Map<Genero, Map<Long, List<Persona>>> personasGeneroEdad = personas.stream()
                .collect(Collectors.groupingBy(Persona::getGenero, Collectors.groupingBy(Persona::edad)));

        //final Double salarioMedio = personas.stream().mapToDouble(Persona::getSalario).average().orElse(89.67);
        // Recuperar el salario promedio
        final Double salarioMedio = personas.stream().mapToDouble(Persona::getSalario)
                .average().orElseThrow(() -> new RuntimeException());

        personasGeneroEdad.entrySet().stream().forEach(entry1 -> {
            //entry1.getKey() -> tiene el genero
            Map<Long, List<Persona>> entry2 = entry1.getValue();

            entry2.entrySet().stream().forEach(entry -> {
                //entry.getKey() tiene la edad;

                entry.getValue().stream().filter(p -> p.getGenero().equals(Genero.HOMBRE) && p.getSalario() > salarioMedio).forEach(System.out::println);

            });
        });

        // Recorrer la coleccion personasGeneroEdad
        personasGeneroEdad.entrySet().stream().forEach(entry1 -> {

            Genero genero = entry1.getKey();
            System.out.println("Genero: " + genero);

            Map<Long, List<Persona>> entry2 = entry1.getValue();

            // Collection<List<Persona>> persons = entry2.values();
            // persons.stream().flatMap(lista -> lista.stream())
            // .filter(persona -> persona.getGenero().equals(Genero.HOMBRE) &&
            // persona.getSalario() > salarioMedio)
            // .forEach(System.out::println);
            entry2.entrySet().stream().forEach(entry -> {
                List<Persona> persons = entry.getValue();
                persons.stream().filter(p -> p.getGenero().equals(Genero.HOMBRE)
                        && p.getSalario() > salarioMedio)
                        .forEach(System.out::println);
            });

        });
    }
}
