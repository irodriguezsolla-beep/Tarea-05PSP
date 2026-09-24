import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Lanzador {

    public void ejecutar(String numero) {
        ProcessBuilder factor = new ProcessBuilder("factor", numero);
        Process proceso = null;

        try {
            proceso = factor.start();

            // Leer salida estándar
            try (BufferedReader leer = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                // Corrección de paréntesis
                while ((linea = leer.readLine()) != null) {
                    System.out.println(numero + ": " + linea);
                }
            }

            int exitCode = proceso.waitFor();
            System.out.println("Operación completa. Código de salida:" + exitCode);

        } catch (Exception error) {
            // Se usa el mismo proceso para leer el errorStream
            if (proceso != null) {
                try (BufferedReader leer2 = new BufferedReader(new InputStreamReader(proceso.getErrorStream()))) {
                    String linea;
                    // Corrección de paréntesis
                    while ((linea = leer2.readLine()) != null) {
                        System.out.println(numero + ": " + linea);
                    }
                    int exitCode2 = proceso.waitFor();
                    System.out.println("Operación completa. Código de salida:" + exitCode2);
                } catch (Exception e) {
                    System.out.println("Error al leer el canal de error: " + e.getMessage());
                }
            }
        }
    }
}
