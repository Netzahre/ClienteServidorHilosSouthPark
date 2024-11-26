package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Cliente {
    //definimos la ip en host y el puerto
    private static final String HOST = "localhost";
    private static final int PORT = 69;

    public static void main(String[] args) {
        //Declaramos cosas
        Socket socketServidor;
        Scanner teclado;

        try {
            //Inicializamos el socket con el host y puerto y se conecta
            socketServidor = new Socket(HOST, PORT);

            //Mensaje de exito de conexion
            System.out.println("Me he conectado a " + HOST + ":" + PORT);

            //Para poder escribir datos al servidor y leer datos del servidor
            PrintWriter entradaDatosCliente = new PrintWriter(socketServidor.getOutputStream(), true);
            BufferedReader salidaDatosCliente = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));

            teclado = new Scanner(new InputStreamReader(System.in));
            //Esto lo tenemos por el tema del menu que si lo hacemos en el while no deja el tema de escribir la opción
            System.out.println(salidaDatosCliente.readLine());
            System.out.println(salidaDatosCliente.readLine());
            System.out.println(salidaDatosCliente.readLine());

            int eleccionUsuario = Integer.parseInt(teclado.nextLine());

            entradaDatosCliente.println(eleccionUsuario);
            System.out.println(salidaDatosCliente.readLine());
            System.out.println(salidaDatosCliente.readLine());

            if (eleccionUsuario == 1) {
                entradaDatosCliente.println(teclado.nextLine());
                String linea;
                //Te lee todas las lineas awesome :D
                while ((linea = salidaDatosCliente.readLine()) != null) {
                    System.out.println(linea);
                }
            } else if (eleccionUsuario == 2) {
                entradaDatosCliente.println(teclado.nextLine());

                System.out.println(salidaDatosCliente.readLine());
                entradaDatosCliente.println(teclado.nextLine());
                System.out.println(salidaDatosCliente.readLine());


            }

            socketServidor.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}