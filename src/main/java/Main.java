import handlers.ClientConnectionHandlerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static int PORT = 4221;

    public static void main(String[] args) {
        if (args.length > 0) {
            PORT = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("accepted new connection");
                    ClientConnectionHandlerFactory.getClientConnectionHandler().handleClientConnection(clientSocket);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
