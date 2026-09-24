import java.sql.SQLOutput;
import java.util.Scanner;
void main() {
    Scanner teclado = new Scanner(System.in);

    System.out.println("Selecciona el nivel(1,2,3,4): ");
    String nivel = teclado.next();

    System.out.println("Di un número: ");
    String numero = teclado.next();
    // Lógica según el nivel seleccionado
    switch (nivel) {
        case "1":
            System.out.println("Nivel "+ nivel);
            System.out.println("Numero " + numero);

            break;
        case "2":
            System.out.println("Nivel "+ nivel);
            System.out.println("Numero " + numero);
            break;
        case "3":
            System.out.println("Nivel "+ nivel);
            System.out.println("Numero " + numero);
            break;
        case "4":
            System.out.println("Nivel "+ nivel);
            System.out.println("Numero " + numero);
            break;
        default:
            break;
    }
}


