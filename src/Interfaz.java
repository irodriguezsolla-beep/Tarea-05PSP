import java.util.Scanner;
void main() {
    Scanner teclado = new Scanner(System.in);

    System.out.println("Selecciona el nivel(1,2,3,4): ");
    String nivel = teclado.next();
    // Lógica según el nivel seleccionado
    switch (nivel) {
        case "1":
            System.out.println("Has selecionado el nivel: " + nivel);
            System.out.println("Di un número: ");
            String numero1 = teclado.next();
            break;
        case "2":
            System.out.println("Has selecionado el nivel: " + nivel);
            System.out.println("Di un número: ");
            String numero2 = teclado.next();
            break;
        case "3":
            System.out.println("Has selecionado el nivel: " + nivel);
            System.out.println("Di un número: ");
            String numero3 = teclado.next();
            break;
        case "4":
            System.out.println("Has selecionado el nivel: " + nivel);
            System.out.println("Di un número: ");
            String numero4 = teclado.next();
            break;
        default:
            break;
    }
}


