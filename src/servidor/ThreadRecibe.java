package servidor;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadRecibe implements Runnable {

    private final FinalServer main;

    private ObjectInputStream entrada;
    private Socket cliente;

    //Inicializar chatServer y configurar GUI
    public ThreadRecibe(Socket cliente, FinalServer main) {
        this.cliente = cliente;
        this.main = main;
    }

    public void run() {
        try {
            entrada = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ThreadRecibe.class.getName()).log(Level.SEVERE, null, ex);
        }
        do { //procesa los mensajes enviados dsd el servidor
            try {//leer el mensaje y mostrarlo 
                main.setAreaTexto((String) entrada.readObject()); //leer nuevo mensaj
                //entrada.reset();

            } //fin try
            catch (SocketException ex) {
            } catch (EOFException eofException) {
                main.setAreaTexto("Fin de la conexion");
                System.out.println(main.getAreaTexto());
                break;
            } //fin catch
            catch (IOException ex) {
                Logger.getLogger(ThreadRecibe.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException classNotFoundException) {
                main.setAreaTexto("Objeto desconocido");
                System.out.println(main.getAreaTexto());
            } //fin catch               

        } while (!main.getAreaTexto().equals("Servidor>>> TERMINATE")); //Ejecuta hasta que el server escriba TERMINATE

        try {
            entrada.close(); //cierra input Stream
            cliente.close(); //cieraa Socket
        } //Fin try
        catch (IOException ioException) {
            ioException.printStackTrace();
        } //fin catch
        main.setAreaTexto("Fin de la conexion");
        System.out.println(main.getAreaTexto());
        System.exit(0);
    }
}
