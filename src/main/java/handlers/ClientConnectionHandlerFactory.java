package handlers;

import java.net.Socket;

public class ClientConnectionHandlerFactory {
    public static ClientConnectionHandler getClientConnectionHandler(Socket clientSocket) {
        return getClientConnectionHandler(ClientConnectionHandlerType.DEFAULT, clientSocket);
    }

    public static ClientConnectionHandler getClientConnectionHandler(ClientConnectionHandlerType handlerType, Socket clientSocket) {
        if (handlerType == ClientConnectionHandlerType.DEFAULT) {
            return new DefaultClientConnectionHandler(clientSocket);
        }
        throw new RuntimeException("Unknown handler type");
    }
}
