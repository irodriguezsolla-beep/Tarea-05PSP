import java.util.Scanner;
void main() {
    Scanner teclado = new Scanner(System.in);
    Lanzador lanzador = new Lanzador();

    while (true){
        System.out.println("Selecciona el nivel(1,2,3,4): ");
        String nivelTexto = teclado.next();

        try {
            int nivel = Integer.parseInt(nivelTexto);
            System.out.println("Di un número o salir para terminar: ");
            String numero = teclado.next();
            //si escribes salir sales
            //nivel.equalsIgnoreCase("salir") sirve para ignorar mayusculas
            if (numero.equalsIgnoreCase("salir")) {
                System.out.println("¡Saliendo del programa!");
                break;
            }
            //Lista de niveles
            switch (nivel) {
                case 1:
                    System.out.println("Nivel "+ nivel);
                    System.out.println("Numero " + numero);
                    lanzador.ejecutarnivel1(numero);
                    break;
                case 2:
                    System.out.println("Nivel "+ nivel);
                    System.out.println("Numero " + numero);
                    lanzador.ejecutarnivel2(numero);
                    break;
                case 3:
                    System.out.println("Nivel "+ nivel);
                    System.out.println("Numero " + numero);
                    lanzador.ejecutarnivel3(numero);
                    break;
                case 4:
                    System.out.println("Nivel "+ nivel);
                    System.out.println("Numero " + numero);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona 1, 2, 3, 4 o salir.");
                    break;
            }
        } catch (NumberFormatException Error){
            System.out.println("Error: '" + nivelTexto + "' no es un nivel válido.");
        }
    }
}


