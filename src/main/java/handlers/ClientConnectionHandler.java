package handlers;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.Socket;

@AllArgsConstructor
public abstract class ClientConnectionHandler implements Runnable {
    Socket clientSocket;
    public abstract void handleClientConnection() throws IOException;
}
