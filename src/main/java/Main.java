import handlers.ClientConnectionHandlerFactory;
import util.State;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    private static int PORT = 4221;

    private static final LinkedList<Thread> threads = new LinkedList<>();

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("--directory")) {
                 State.directory = args[1];
            } else {
                PORT = Integer.parseInt(args[0]);
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                try {
                    System.out.println("accepted new connection");
                    Thread t = new Thread(ClientConnectionHandlerFactory.getClientConnectionHandler(clientSocket));
                    threads.add(t);
                    t.start();
                } catch (Exception e) {
                    clientSocket.getOutputStream().write("HTTP/1.1 500 Internal Server Error\r\n\r\n".getBytes());
                    clientSocket.getOutputStream().close();
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            cleanupThreads();
        }
    }
    private static void cleanupThreads() {
        Iterator<Thread> threadIterator = threads.iterator();
        while (threadIterator.hasNext()) {
            Thread t = threadIterator.next();
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception: " + e.getMessage());
            } finally {
                threadIterator.remove();
            }
        }
    }
}
