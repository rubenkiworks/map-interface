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
    }
}
