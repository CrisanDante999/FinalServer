package servidor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Clase que se encarga de correr los threads de enviar y recibir texto y de
 * crear la interfaz grafica.
 *
 * @author Rafa
 */
public class FinalServer {

    private String campoTexto; //Para mostrar mensajes de los usuarios
    private String areaTexto; //Para ingresar mensaje a enviar
    private static ServerSocket servidor; //
    private static Socket conexion; //Socket para conectarse con el cliente
    private static String ip = "127.0.0.1"; //ip a la cual se conecta

    public static FinalServer main;

    public FinalServer() {
        this.areaTexto = "";
    }

    public void setAreaTexto(String areaTexto) {
        this.areaTexto = areaTexto;
    }

    public String getAreaTexto() {
        return areaTexto;
    }
    
    public static void main(String[] args) {
        FinalServer main = new FinalServer(); //Instanciacion de la clase Principalchat
        ExecutorService executor = Executors.newCachedThreadPool(); //Para correr los threads

        try {
            //main.mostrarMensaje("No se encuentra Servidor");
            servidor = new ServerSocket(10578, 100);
            System.out.println("Esperando Cliente ...");

            //Bucle infinito para esperar conexiones de los clientes
            while (true) {
                try {
                    conexion = servidor.accept();
                    System.out.println("Conectado a : " + conexion.getInetAddress().getHostName());                    //Ejecucion de los threads
                    executor.execute(new ThreadRecibe(conexion, main)); //client
                    executor.execute(new ThreadEnvia(conexion, main));
                } catch (IOException ex) {
                    Logger.getLogger(FinalServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FinalServer.class.getName()).log(Level.SEVERE, null, ex);
        } //Fin del catch //Fin del catch //Fin del catch //Fin del catch
        finally {
        }
        executor.shutdown();
    }
}
