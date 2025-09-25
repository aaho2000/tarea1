import java.util.HashMap; // como solucionar colosiones
import java.util.Map;
import java.util.Scanner;

public class Calculadora {
    private Map<String, Double> historial;
    private Scanner scanner;

    public Calculadora() {
        this.historial = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("Calculadora del 1 al 4");
        System.out.println("1. Sumar");
        System.out.println("2. Restar");
        System.out.println("3. Multiplicar");
        System.out.println("4. Dividir");
        System.out.println("5. Ver Historial");
        System.out.println("6. Salir");
    }

    public void realizarOperacion(int opcion) {
        double num1, num2, resultado;
        String claveHistorial;

        switch (opcion) {
            case 1:
                System.out.print("Ingrese el primer número: ");
                num1 = scanner.nextDouble();
                System.out.print("Ingrese el segundo número: ");
                num2 = scanner.nextDouble();
                resultado = num1 + num2;
                claveHistorial = num1 + " + " + num2;
                historial.put(claveHistorial, resultado);
                System.out.println("Resultado: " + resultado);
                break;
            case 2:
                System.out.print("Ingrese el primer número: ");
                num1 = scanner.nextDouble();
                System.out.print("Ingrese el segundo número: ");
                num2 = scanner.nextDouble();
                resultado = num1 - num2;
                claveHistorial = num1 + " - " + num2;
                historial.put(claveHistorial, resultado);
                System.out.println("Resultado: " + resultado);
                break;
            case 3:
                System.out.print("Ingrese el primer número: ");
                num1 = scanner.nextDouble();
                System.out.print("Ingrese el segundo número: ");
                num2 = scanner.nextDouble();
                resultado = num1 * num2;
                claveHistorial = num1 + " * " + num2;
                historial.put(claveHistorial, resultado);
                System.out.println("Resultado: " + resultado);
                break;
            case 4:
                System.out.print("Ingrese el primer número: ");
                num1 = scanner.nextDouble();
                System.out.print("Ingrese el segundo número: ");
                num2 = scanner.nextDouble();
                if (num2 != 0) {
                    resultado = num1 / num2;
                    claveHistorial = num1 + " / " + num2;
                    historial.put(claveHistorial, resultado);
                    System.out.println("Resultado: " + resultado);
                } else {
                    System.out.println("Error: No se puede dividir por cero.");
                }
                break;
            case 5:
                System.out.println("Historial de operaciones:");
                for (Map.Entry<String, Double> entry : historial.entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());
                }
                break;
            case 6:
                System.out.println("Saliendo de la calculadora.");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
        }
    }

    public void buscarEnHistorial() {
        System.out.print("Ingrese la operación a buscar (ej. 2 + 3): ");
        String operacion = scanner.next();
        if (historial.containsKey(operacion)) {
            System.out.println("Resultado encontrado: " + historial.get(operacion));
        } else {
            System.out.println("Operación no encontrada en el historial.");
        }
    }

    public void ejecutar() {
        int opcion;
        while (true) {
            mostrarMenu();
            System.out.print("Ingrese una opción (1-6): ");
            opcion = scanner.nextInt();
            realizarOperacion(opcion);
        }
    }

    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        calculadora.ejecutar();
    }
}