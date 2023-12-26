package handlers;

import java.io.IOException;
import java.net.Socket;

public interface ClientConnectionHandler {
    void handleClientConnection(Socket clientSocket) throws IOException;
}
