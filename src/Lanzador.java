import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class Lanzador {

    public void ejecutarnivel1(String numero) {
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

    public void ejecutarnivel2(String numero) {
        ProcessBuilder factor = new ProcessBuilder("factor", numero);
        Process proceso = null;

        // Se conecta el BufferedWriter a la consola (System.out)
        try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(System.out))) {

            try {
                proceso = factor.start();

                // Leer la salida estándar del proceso
                try (BufferedReader leer = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                    String linea;
                    while ((linea = leer.readLine()) != null) {
                        // Se escribe mediante el BufferedWriter
                        escritor.write("[OK] " + numero + ": " + linea);
                        escritor.newLine(); // Salto de línea
                    }
                }

                int exitCode = proceso.waitFor();
                escritor.write("[OK] Operación completa. Código de salida: " + exitCode);
                escritor.newLine();
                escritor.flush(); // Asegura que todo el texto se envíe a la consola

            } catch (Exception error) {
                if (proceso != null) {
                    try (BufferedReader leer2 = new BufferedReader(new InputStreamReader(proceso.getErrorStream()))) {
                        String linea;
                        while ((linea = leer2.readLine()) != null) {
                            escritor.write("[ERROR] " + numero + ": " + linea);
                            escritor.newLine();
                        }
                        int exitCode2 = proceso.waitFor();
                        escritor.write("[ERROR] Operación completa. Código de salida: " + exitCode2);
                        escritor.newLine();
                    } catch (Exception e) {
                        escritor.write("[ERROR] Error al leer el canal de error: " + e.getMessage());
                        escritor.newLine();
                    }
                } else {
                    escritor.write("[ERROR] No se pudo iniciar el proceso: " + error.getMessage());
                    escritor.newLine();
                }
                escritor.flush(); // Muestra los errores por pantalla
            }

        } catch (Exception e) {
            System.err.println("Error en la escritura del buffer: " + e.getMessage());
        }
    }

    public void ejecutarnivel3(String numero) {
        ProcessBuilder factor = new ProcessBuilder("factor", numero);

        // Archivos de salida y error
        File outputFile = new File("factor_output.log");
        File errorFile = new File("factor_error.log");

        // Redirect.appendTo asegura que no se borre lo que ya existía en el fichero
        factor.redirectOutput(Redirect.appendTo(outputFile));
        factor.redirectError(Redirect.appendTo(errorFile));

        try {
            Process proceso = factor.start();

            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();

            // Mostrar ÚNICAMENTE el código de salida por pantalla
            System.out.println("Código de salida: " + exitCode);

        } catch (Exception error) {
            System.err.println("Error al ejecutar el nivel 3: " + error.getMessage());
        }
        }
}
