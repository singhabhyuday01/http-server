package handlers;

public class ClientConnectionHandlerFactory {
    public static ClientConnectionHandler getClientConnectionHandler() {
        return getClientConnectionHandler(ClientConnectionHandlerType.DEFAULT);
    }

    public static ClientConnectionHandler getClientConnectionHandler(ClientConnectionHandlerType handlerType) {
        if (handlerType == ClientConnectionHandlerType.DEFAULT) {
            return new DefaultClientConnectionHandler();
        }
        throw new RuntimeException("Unknown handler type");
    }
}
