import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static final int PORT = 2200;
    public static void main(String[] args) {

        try {

            ServerSocket server = new ServerSocket(PORT);

            //Aceptar conexion del cliente
            Socket socket = server.accept();
            System.out.println("Cliente conectado");


            //recibir info
            DataInputStream in = new DataInputStream(socket.getInputStream());

            //enviar info
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            //Threads para enviar y recibir sincronicamente


            //Enviar mensajes
            Thread sender = new Thread(new Runnable() {

                private String message;
                @Override
                public void run() {

                    try {

                        //Siempre que haya un cliente conectado
                        while (true) {

                            message = scanner.nextLine();

                            //Enviar mensaje
                            out.writeUTF(message);

                        }


                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            });



            //Mostrar mensajes recibidos
            Thread writer = new Thread(new Runnable() {
                private String message;
                @Override
                public void run() {
                    try {
                        message = in.readUTF();

                        while (message != null) {
                            System.out.println(message);
                            message = in.readUTF();
                        }


                    }catch (Exception e) {
                        System.out.println("Cliente desconectado");
                        System.out.println(e.getMessage());
                    }
                }
            });

            sender.start();
            writer.start();

        } catch (IOException e) {
            System.out.println(e);
        }




    }
}