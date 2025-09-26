public int busquedaSecuencial(int[] arr, int objetivo) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == objetivo) {
            return i; // Elemento encontrado
        }
    }
    return -1; // Elemento no encontrado
}
///////////////
public int busquedaBinaria(int[] arr, int objetivo) {
    int izquierda = 0;
    int derecha = arr.length - 1;
    while (izquierda <= derecha) {
        int medio = izquierda + (derecha - izquierda) / 2;
        if (arr[medio] == objetivo) {
            return medio;
        } else if (arr[medio] < objetivo) {
            izquierda = medio + 1;
        } else {
            derecha = medio - 1;
        }
    }
    return -1; // Elemento no encontrado
}