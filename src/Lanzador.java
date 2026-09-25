import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class Lanzador {

    public void ejecutarnivel1(String numero) {
        ProcessBuilder factor = new ProcessBuilder("factor", numero);
        factor.redirectErrorStream(true);
        Process proceso = null;

        try {
            proceso = factor.start();

            // Leer salida estándar
            try (BufferedReader leer = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                // Corrección de paréntesis
                while ((linea = leer.readLine()) != null) {
                    System.out.println(linea);
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
                        System.out.println(linea);
                    }
                    int exitCode2 = proceso.waitFor();
                    System.out.println("factor: "+ numero+" is not a valid positive integer");
                    System.out.println("Operación completa. Código de salida:" + exitCode2);
                } catch (Exception e) {
                    System.out.println("Error al leer el canal de error: " + e.getMessage());
                }
            }
        }
    }

    public void ejecutarnivel2(String numero) {
        ProcessBuilder factor = new ProcessBuilder("factor", numero);
        factor.redirectErrorStream(true);
        Process proceso = null;

        // Se conecta el BufferedWriter a la consola (System.out)
        try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(System.out))) {

            try {
                proceso = factor.start();

                StringBuilder resultado = new StringBuilder();

                // 1. Leemos todo lo que devuelva la salida normal o de error
                BufferedReader leerNorm = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                BufferedReader leerErr = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));

                String linea;
                while ((linea = leerNorm.readLine()) != null) {
                    resultado.append(linea).append("\n");
                }
                while ((linea = leerErr.readLine()) != null) {
                    resultado.append(linea).append("\n");
                }

                // 2. Esperamos el código de salida
                int exitCode = proceso.waitFor();

                // 3. Definimos si es [OK] o [ERROR]
                String prefijo = (exitCode == 0) ? "[OK] " : "[ERROR] ";

                // 4. Imprimimos el contenido acumulado
                for (String l : resultado.toString().split("\n")) {
                    if (!l.isBlank()) {
                        escritor.write(prefijo + l);
                        escritor.newLine();
                    }
                }

                // 5. Imprimimos la operación completada limpia
                escritor.write("Operación completada. Código de salida: " + exitCode);
                escritor.newLine();

            } catch (Exception error) {
                if (proceso != null) {
                    try (BufferedReader leer2 = new BufferedReader(new InputStreamReader(proceso.getErrorStream()))) {
                        String linea;
                        while ((linea = leer2.readLine()) != null) {
                            escritor.write("[ERROR] " + linea);
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

    public void ejecutarnivel4(String numero) {
        ProcessBuilder factor = new ProcessBuilder("factor", numero);
        factor.redirectErrorStream(true);
        Process proceso = null;

        try {
            proceso = factor.start();

            // Leer salida estándar
            try (BufferedReader leer = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                // Corrección de paréntesis
                while ((linea = leer.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // comprobar si es primp
            try {
                int num = Integer.parseInt(numero);
                boolean esPrimo = true;

                if (num <= 1) {
                    esPrimo = false;
                } else {
                    for (int i = 2; i < num; i++) {
                        if (num % i == 0) {
                            esPrimo = false; // Se puede dividir por otro número, NO es primo
                            break;
                        }
                    }
                }

                if (esPrimo) {
                    System.out.println("El número " + num + " es primo.");
                } else {
                    System.out.println("El número " + num + " NO es primo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("No se pudo comprobar si es primo porque no es un número.");
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
