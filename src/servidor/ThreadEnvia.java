package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadEnvia implements Runnable {

    private FinalServer main;
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion;
    private Thread tamal;

    public ThreadEnvia(Socket conexion, FinalServer main) {

        this.conexion = conexion;
        this.main = main;
        // salida.flush(); //flush salida a cliente

    }

    //enviar objeto a cliente 
    private void enviarDatos(String mensaje) {
        try {
            salida.writeObject("Servidor>>> " + mensaje);
            main.setAreaTexto("Servidor>>> " + mensaje);
            main.setAreaTexto("");
        } //Fin try
        catch (IOException ioException) {
            main.setAreaTexto("Error escribiendo Mensaje");
        } //Fin catch
    } //Fin methodo enviarDatos
    //manipula areaPantalla en el hilo despachador de eventos

    public void mostrarMensaje(String mensaje) {
        main.setAreaTexto(mensaje);
    }

    public void run() {
        try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            do {
                
                System.out.println("ñññ "+ main.getAreaTexto());
                if(main.getAreaTexto().equals("alma")){
                      enviarDatos("OJK");
                }
              
                //System.out.println("SI envia");
                salida.flush(); //flush salida a cliente
                //salida.close();
            } while (!main.getAreaTexto().equals("kkk"));
            // salida.flush();
        } catch (SocketException ex) {
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }

}
