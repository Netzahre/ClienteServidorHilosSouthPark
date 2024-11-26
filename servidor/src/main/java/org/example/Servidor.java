package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    //El puerto de donde se inicia el servidor
    static final int PUERTO = 69;

    //El inicio del servidor desde nuestro aplicativo
    public static void main(String[] args) {
    try {
        //Esto coge el puerto para hacer el enchufe
        ServerSocket socketServidor = new ServerSocket(PUERTO);

        while (true) {
            //Si el socket es aceptado realiza el hilo de mostrar cliente
            Socket socketCliente = socketServidor.accept();
            new Thread(new hiloServidor(socketCliente)).start();
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    }
}
