# Tarea 05
## Auditoría Cósmica: El Detector de Primos
### Clase Interfaz

Creo una clase que se encarga de controlar el menú interactivo por consola y gestionar qué hace la aplicación según lo que pida el usuario.
![](fotos/Captura%20de%20pantalla%202026-09-26%20165846.png)

1. **Preparo las herramientas**: Creo el objeto `Scanner` para leer lo que escribo por teclado y la instancia de `Lanzador` para controlar la ejecución de los niveles.
2. **Entro en el bucle**: Utilizo un `while (true)` para mantener el programa corriendo de forma indefinida y no tener que reiniciarlo tras cada consulta.
3. **Pido y valido el nivel**: Solicito la opción por consola e intento pasarla a entero. Si escribo letras o un formato incorrecto, el bloque `catch` captura la excepción `NumberFormatException`, me avisa del error y vuelve a pedirme el dato sin cerrar la aplicación.
4. **Pido el número o cierro**: Pido el número a procesar y compruebo con `equalsIgnoreCase("salir")` si quiero finalizar. Si escribo "salir", muestro un mensaje de despedida y uso un `break` para romper el bucle.
5. **Ejecuto el nivel**: Uso una estructura `switch` para evaluar el nivel (1, 2, 3 o 4) y llamar al método correspondiente de mi objeto `Lanzador` pasándole el número. Si introduzco una opción distinta, la rama `default` me avisa de que el nivel no es válido.

Ejemplo de salida correcta:

![Captura desde 2026-09-25 08-31-54.png](fotos/Captura%20desde%202026-09-25%2008-31-54.png)

Ejemplo de salida con un dato incorrecto:

![Captura desde 2026-09-25 08-56-36.png](fotos/Captura%20desde%202026-09-25%2008-56-36.png)

### Clase Lanzador

En esta clase he metido todas las funciones que ejecutan el comando `factor` de Linux usando `ProcessBuilder`. Recibo el número que el usuario puso en la interfaz, lanzo el proceso y controlo lo que sale según el nivel que se haya elegido:

* **Nivel 1**: Lanzo `factor` con el número, muestro la respuesta tal cual por consola y devuelvo el código de salida.
* **Nivel 2**: Separo la salida normal de los errores para ponerle la etiqueta `[OK]` si el número era válido o `[ERROR]` si falló.
* **Nivel 3**: Guardo la salida en `factor_output.log` o `factor_error.log` sin borrar lo anterior, y por pantalla solo enseño el código de salida.
* **Nivel 4**: Reviso lo que devuelve `factor` para comprobar si el número es primo o no y ponerlo en la consola.

#### Nivel 1

Este método ejecuta el comando `factor` en el sistema y muestra el resultado tal cual por la consola.

1. **Configuro el proceso**: Creo el `ProcessBuilder` indicándole que ejecute el comando `factor` pasando el número como parámetro. Con `redirectErrorStream(true)` me aseguro de que tanto la salida normal como los errores se puedan leer en un mismo flujo.
2. **Arranco el comando**: Uso `factor.start()` para iniciar el proceso dentro del bloque `try`.
3. **Leo la salida por pantalla**: Con un `BufferedReader` recorro todas las líneas que genera el comando y las voy imprimiendo directamente en la consola con un bucle `while`.
4. **Capturo el código de salida**: Espero a que el proceso termine usando `proceso.waitFor()` y muestro en pantalla el código de salida final (`0` si fue bien o `1` si dio error).
5. **Control de errores**: Si salta alguna excepción durante la ejecución, capturo el fallo con el bloque `catch` e intento leer el canal de error para mostrar el mensaje de que el número no es válido y devolver el código de fallo.

#### Pruebas

| Valor | Salida de factor                               | Código de salida |
| :--- |:-----------------------------------------------|:----------------|
| **360** | 360: 2 2 2 3 3 5                               | 0               |
| **1** | 1:                                             | 0               |
| **17** | 17: 17                                         | 0               |
| **hola** | factor: 'hola' no es un entero positivo válido | 1               |
| **-5** | opción inválida -- '5'                         | 1                |

##### Pruebas gráficas
![Captura desde 2026-09-25 08-33-00.png](fotos/Captura%20desde%202026-09-25%2008-33-00.png)
![Captura desde 2026-09-25 08-56-07.png](fotos/Captura%20desde%202026-09-25%2008-56-07.png)
![Captura desde 2026-09-25 08-32-29.png](fotos/Captura%20desde%202026-09-25%2008-32-29.png)
##### Error

He puesto este bloque `catch` para controlar qué pasa cuando el usuario mete algo que `factor` no entiende, como letras o números negativos:

```java
} catch (Exception error) {
    if (proceso != null) {
        try (BufferedReader leer2 = new BufferedReader(new InputStreamReader(proceso.getErrorStream()))) {
            String linea;
            while ((linea = leer2.readLine()) != null) {
                System.out.println(linea);
            }
            int exitCode2 = proceso.waitFor();
            System.out.println("factor: " + numero + " is not a valid positive integer");
            System.out.println("Operación completa. Código de salida:" + exitCode2);
        } catch (Exception e) {
            System.out.println("Error al leer el canal de error: " + e.getMessage());
        }
    }
}
```
Básicamente lo metí para que la aplicación no pete si le pasas un dato malo. Si salta un error al ejecutar, entra aquí, lee lo que ha fallado por el canal de error, te muestra el mensaje de que el número no vale y saca el código de salida correspondiente sin cerrarse de golpe.

### Nivel 2

En este nivel la idea es formatear la salida agregando el prefijo `[OK]` o `[ERROR]` en función de si el comando ha ido bien o no, usando además un `BufferedWriter` para escribir en la consola.

1. **Preparo el escritor**: Abro un `BufferedWriter` conectado a `System.out` para volcar luego todos los datos formateados de golpe por pantalla.
2. **Arranco el proceso**: Configuro el `ProcessBuilder` con el número recibido, activo `redirectErrorStream(true)` y ejecuto el comando con `factor.start()`.
3. **Guardo la salida**: Leo tanto el flujo de entrada como el de error con dos `BufferedReader` y voy acumulando las líneas en un `StringBuilder`.
4. **Compruebo el resultado**: Con `proceso.waitFor()` saco el código de salida. Si da `0`, le asigno el prefijo `[OK]`, y si devuelve cualquier otro código, le asigno `[ERROR]`.
5. **Muestro el contenido**: Separo el texto acumulado línea por línea, le añado el prefijo que corresponda (`[OK]` o `[ERROR]`) y lo escribo en la consola con el `BufferedWriter`.
6. **Gestión de fallos**: Si salta alguna excepción durante la ejecución, entro en el `catch` y utilizo el canal de error para pintar las líneas acompañadas directamente de la etiqueta `[ERROR]`.

#### Pruebas gráficas
Con [OK]
![Captura desde 2026-09-25 08-59-23.png](fotos/Captura%20desde%202026-09-25%2008-59-23.png)
Con [ERROR]
![Captura desde 2026-09-25 09-55-21.png](fotos/Captura%20desde%202026-09-25%2009-55-21.png)


### Nivel 3

En el nivel 3 cambio la forma de mostrar la información: en lugar de imprimir el resultado de `factor` por la pantalla, redirijo las salidas para que se guarden en ficheros `.log` y dejo la consola limpia.

1. **Defino los ficheros**: Creo dos objetos `File` para gestionar los archivos de registro: `factor_output.log` (para las salidas correctas) y `factor_error.log` (para los errores).
2. **Redirijo los flujos**: Uso `Redirect.appendTo()` tanto en `redirectOutput` como en `redirectError`. Esto es clave porque me asegura que si ejecuto el comando varias veces, los datos se añaden al final del archivo sin borrar lo que ya estuviera guardado.
3. **Lanzamiento y espera**: Arranco el proceso con `factor.start()` y espero a que termine con `proceso.waitFor()`.
4. **Respuesta por pantalla**: Muestro por la consola únicamente el código de salida (`0` o `1`), ya que todo el detalle del resultado se ha volcado directamente en los archivos.
5. **Control de excepciones**: Si ocurre algún fallo al intentar crear los ficheros o lanzar el comando, lo capturo en el `catch` e imprimo el mensaje de error por la salida de error estándar.

factor_output.log
![Captura desde 2026-09-25 10-03-28.png](fotos/Captura%20desde%202026-09-25%2010-03-28.png)
factor_error.log
![Captura desde 2026-09-25 10-03-47.png](fotos/Captura%20desde%202026-09-25%2010-03-47.png)


### Nivel 4
En este nivel ejecuto el comando `factor` para ver su descomposición y, además, añado una lógica propia en Java para comprobar manualmente si el número introducido es primo o no.

1. **Ejecuto y leo la salida**: Creo el `ProcessBuilder`, unifico los flujos con `redirectErrorStream(true)` y ejecuto el comando. Uso un `BufferedReader` para imprimir por consola lo que devuelve `factor`.
2. **Validación de número**: Convierto el parámetro a un entero con `Integer.parseInt(numero)` dentro de un bloque `try-catch`. Si el usuario ha metido texto en lugar de un número, capturo la excepción `NumberFormatException` para avisar de que no se puede hacer la comprobación.
3. **Algoritmo de número primo**:
    * Si el número es menor o igual a `1`, directamente marco que **no es primo**.
    * Si es mayor que `1`, recorro con un bucle `for` desde el `2` hasta el número anterior. Si encuentro algún divisor con resto `0` (`num % i == 0`), rompo el bucle (`break`) porque ya sé que no es primo.
4. **Muestro la conclusión**: Imprimo por pantalla si el número es primo o no según el resultado de la comprobación.
5. **Finalización y control de errores**: Muestro el código de salida devuelto por `proceso.waitFor()`. Si ocurre algún fallo general en la ejecución, en el bloque `catch` leo el canal de error para mostrar el mensaje de fallo correspondiente.

#### Pruebas gráficas
Par

![Captura desde 2026-09-25 10-06-09.png](fotos/Captura%20desde%202026-09-25%2010-06-09.png)

Impar
![Captura desde 2026-09-25 10-09-51.png](fotos/Captura%20desde%202026-09-25%2010-09-51.png)