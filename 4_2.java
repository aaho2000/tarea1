import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

    // mismo metodo para calcular las areas
    public static double calcularArea(String figura, double... parametros) {
        switch (figura.toLowerCase()) {
            case "circulo":
                double radio = parametros[0];
                return Math.PI * radio * radio;
            case "cuadrado":
                double lado = parametros[0];
                return lado * lado;
            case "triangulo":
                double base = parametros[0];
                double altura = parametros[1];
                return 0.5 * base * altura;
            default:
                throw new IllegalArgumentException("Figura no reconocida");
        }
    }
//.....................................................................................................................................................
//Método sort
    // Método para la ordenación externa copia los métodos de la respuesta anterior
    public static void sort(String inputFile, String outputFile) throws IOException {
        String tempFile1 = "temp1.txt"; //  nombre del primer archivo temporal
        String tempFile2 = "temp2.txt";// nombre del segundo archivo temporal
        int runSize = 1; inicializa la secuencia ordenada a 1

        boolean sorted = false;                                               //para controlar el bucle de ordenación
        while (!sorted) {                                                     // Bucle repite hasta que el archivo esté completamente ordenado
            splitFile(inputFile, tempFile1, tempFile2, runSize);              // Llama al método para dividir el archivo
            sorted = mergeFiles(tempFile1, tempFile2, outputFile, runSize);    // Llama al método para fusionar los archivos y verifica si el proceso ha terminado
            inputFile = outputFile;                                            // Prepara la próxima iteración, el archivo de salida se convierte en el de entrada
            runSize *= 2;                                                     // Duplica el tamaño de la secuencia para la siguiente ronda de mezcla
        }

        new File(tempFile1).delete();                                        // Elimina el primer archivo temporal

        new File(tempFile2).delete();                                       // Elimina el segundo archivo temporal

    }
///...................................................................................................................................................
//Método splitFile

  private static void splitFile(String source, String dest1, String dest2, int runSize) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(source)); // Abre un lector para el archivo de origen
         BufferedWriter writer1 = new BufferedWriter(new FileWriter(dest1)); // Abre un escritor para el primer archivo de destino
         BufferedWriter writer2 = new BufferedWriter(new FileWriter(dest2))) { // Abre un escritor para el segundo archivo de destino

        String line; // Variable para almacenar cada línea leída
        int fileCounter = 0; // Contador para rastrear en qué archivo escribir
        while ((line = reader.readLine()) != null) { // Lee el archivo línea por línea hasta el final
            if (fileCounter < runSize) { // Si el contador es menor que el tamaño de la secuencia...
                writer1.write(line + "\n"); // ...escribe la línea en el primer archivo
            } else { // Si no...
                writer2.write(line + "\n"); // ...escribe la línea en el segundo archivo
            }
            fileCounter = (fileCounter + 1) % (2 * runSize); // Reinicia el contador para alternar entre archivos después de cada secuencia de tamaño `runSize`
        }
    }
}
//.....................................................................................................................................
// Método mergeFiles


   private static boolean mergeFiles(String source1, String source2, String dest, int runSize) throws IOException {
    try (BufferedReader reader1 = new BufferedReader(new FileReader(source1)); // Abre un lector para el primer archivo de origen
         BufferedReader reader2 = new BufferedReader(new BufferedReader(new FileReader(source2))); // Abre un lector para el segundo archivo de origen
         BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) { // Abre un escritor para el archivo de destino
        
        String line1 = reader1.readLine(); // Lee la primera línea del archivo 1
        String line2 = reader2.readLine(); // Lee la primera línea del archivo 2
        int run1Count = 0; // Contador para los elementos en la secuencia actual del archivo 1
        int run2Count = 0; // Contador para los elementos en la secuencia actual del archivo 2
        
        while (line1 != null || line2 != null) { // Bucle principal de mezcla, continúa mientras haya datos en al menos un archivo
            if (line1 != null && (line2 == null || Double.parseDouble(line1) <= Double.parseDouble(line2)) && run1Count < runSize) { 
                // Condición para escribir desde el archivo 1:
                // - Hay una línea en el archivo 1.
                // - No hay más líneas en el archivo 2 O la línea del archivo 1 es menor o igual a la del archivo 2.
                // - El contador de la secuencia 1 no ha alcanzado el límite.
                writer.write(line1 + "\n"); // Escribe la línea del archivo 1 en el archivo de destino
                line1 = reader1.readLine(); // Lee la siguiente línea del archivo 1
                run1Count++; // Incrementa el contador de la secuencia 1
            } else if (line2 != null && (line1 == null || Double.parseDouble(line2) < Double.parseDouble(line1)) && run2Count < runSize) { 
                // Condición para escribir desde el archivo 2:
                // - Hay una línea en el archivo 2.
                // - No hay más líneas en el archivo 1 O la línea del archivo 2 es estrictamente menor a la del archivo 1.
                // - El contador de la secuencia 2 no ha alcanzado el límite.
                writer.write(line2 + "\n"); // Escribe la línea del archivo 2 en el archivo de destino
                line2 = reader2.readLine(); // Lee la siguiente línea del archivo 2
                run2Count++; // Incrementa el contador de la secuencia 2
            } else {
                // Esta es la parte crítica del algoritmo de fusión, donde se maneja el final de una secuencia.
                writer.flush(); // Asegura que cualquier dato pendiente se escriba en el archivo
                if (run1Count == runSize) { // Si la secuencia 1 ha terminado...
                    run1Count = 0; // ...reinicia su contador para el próximo bloque
                }
                if (run2Count == runSize) { // Si la secuencia 2 ha terminado...
                    run2Count = 0; // ...reinicia su contador para el próximo bloque
                }
            }
        }
        
        // Esta línea contiene un error lógico en el código original.
        // La comparación del tamaño de los archivos no es una forma confiable de determinar
        // si el proceso de ordenación ha terminado.
        return (runSize * 2) >= (new File(source1).length() + new File(source2).length()); 
    }
}
//.........................................................................................................................................

   

// Método principal para la entrada por teclado y la ejecución
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> areas = new ArrayList<>();
        String inputFile = "areas_input.txt";
        String outputFile = "areas_sorted.txt";

        System.out.println("Ingrese las figuras y sus parámetros para calcular el área.");
        System.out.println("Escriba 'fin' para terminar la entrada de datos.");

        while (true) {
            System.out.print("\nIngrese la figura (circulo, cuadrado, triangulo) o 'fin': ");
            String figura = scanner.nextLine();

            if (figura.equalsIgnoreCase("fin")) {
                break;
            }

            try {
                double area;
                switch (figura.toLowerCase()) {
                    case "circulo":
                        System.out.print("Ingrese el radio: ");
                        double radio = scanner.nextDouble();
                        area = calcularArea(figura, radio);
                        break;
                    case "cuadrado":
                        System.out.print("Ingrese el lado: ");
                        double lado = scanner.nextDouble();
                        area = calcularArea(figura, lado);
                        break;
                    case "triangulo":
                        System.out.print("Ingrese la base: ");
                        double base = scanner.nextDouble();
                        System.out.print("Ingrese la altura: ");
                        double altura = scanner.nextDouble();
                        area = calcularArea(figura, base, altura);
                        break;
                    default:
                        System.out.println("Figura no reconocida. Intente de nuevo.");
                        continue;
                }
                areas.add(area);
                System.out.printf("Área calculada: %.2f\n", area);
            } catch (Exception e) {
                System.out.println("Error en la entrada. Asegúrese de ingresar números válidos.");
            } finally {
                scanner.nextLine(); // Consumir el salto de línea
            }
        }
        
        scanner.close();

        // 2. Escribir las áreas en un archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (Double area : areas) {
                writer.write(area.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Ejecutar la ordenación externa
        try {
            System.out.println("\nIniciando la ordenación externa...");
            sort(inputFile, outputFile);
            System.out.println("Ordenación completada. El resultado está en " + outputFile);
            
            // 4. Leer y mostrar el archivo ordenado
            System.out.println("\nContenido del archivo ordenado:");
            try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
