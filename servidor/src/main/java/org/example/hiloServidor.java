package org.example;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class hiloServidor implements Runnable {
    //Variables a usar
    final static private String RUTA_CAPITULO = "southpark.txt";
    private Socket socket; //Usamos los sockets como enchufes
    // Establece los flujos de salida y entrada (desde y hacia el cliente, respectivamente)
    private static PrintWriter entradaSocketCliente; //Con esto pasamos lo que el cliente quiere escribir
    private static BufferedReader salidaSocketCliente; //Esto es lo que obtiene de salida
    ReentrantLock lock = new ReentrantLock();

    public hiloServidor(Socket socket) {
        try {
            this.socket = socket;
            entradaSocketCliente = new PrintWriter(socket.getOutputStream(), true);
            salidaSocketCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        //Entradas que ve el cliente en pantalla
        entradaSocketCliente.println("Bienvenido a 'leer South park de gratis en ingles' :D");
        entradaSocketCliente.println("Tenemos un capitulo, para su uso y disfrute ¿Desea leerlo o escribir nuevas lineas?");
        entradaSocketCliente.println("Pulsa 1 si deseas leerlo o 2 para escribir nuevas lineas?");
        int eleccionCliente;
        try {
            eleccionCliente = Integer.parseInt(salidaSocketCliente.readLine());

            switch (eleccionCliente) {
                //Si elige el 1 lee todas las frases del personaje
                case 1 -> {
                    entradaSocketCliente.println("Con que has elegido el camino de leer las sagradas escrituras sureñas...");
                    entradaSocketCliente.println("Entonces elige de cual profeta deseas leer su palabra");
                    leerFraseDelProfeta(salidaSocketCliente.readLine()); //Esto no lo sabia ta interesante
                }
                //Si elige el 2 escribe una nueva
                case 2 -> {
                    entradaSocketCliente.println("Con que has elegido el camino de escribir las es escrituras sureñas...");
                    entradaSocketCliente.println("Entonces elige el nombre del profeta al que le vas a dar su palabra");
                    escribirFraseDelProfeta(salidaSocketCliente.readLine());
                }
                default ->  {
                    entradaSocketCliente.println("Has elegido un camino incorrecto pequeño saltamontes");
                }
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo para leer las frases del personaje seleccionado
    private static void leerFraseDelProfeta(String profeta){
        //Para leer el archivo
        try (BufferedReader lectorFrase = new BufferedReader(new FileReader(RUTA_CAPITULO))){
            int contador = 0;
            boolean existe = false;
            while (lectorFrase.readLine()!=null) {
                //Esta cosa fea es para solo imprimirlo una vez y no tener que ver varios nombres
                if (lectorFrase.readLine().equalsIgnoreCase(profeta)) {
                    if (contador == 0) {
                        entradaSocketCliente.println("El/La/Le/Lx profeta/s " + profeta + " dijo: ");
                        contador++;
                        existe = true;
                    }
                    entradaSocketCliente.println(lectorFrase.readLine());
                }
            }
            if (!existe) entradaSocketCliente.println("¡El/La/Le/Lx profeta/s que has dicho no existe/n, hereje!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para escribir frases en el txt
    private void escribirFraseDelProfeta(String profeta) throws IOException {
        lock.lock(); //Er lock
        BufferedWriter escribirCliente = new BufferedWriter(new FileWriter(RUTA_CAPITULO, true)); //Para leer el archivo
        //Esto escribe la frase y la registra en el txt
        try {
            escribirCliente.newLine();
            escribirCliente.write(profeta);
            escribirCliente.newLine();
            entradaSocketCliente.println("Ahora escribe las sagradas escrituras");
            String sagradasLineas = salidaSocketCliente.readLine();
            escribirCliente.write(sagradasLineas);
            escribirCliente.newLine();
            escribirCliente.close();
            entradaSocketCliente.println("QUEDA REGISTRADO EN LAS SAGRADAS ESCRITURAS");
        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            lock.unlock();
        }
    }
}
