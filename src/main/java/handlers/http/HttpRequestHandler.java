package handlers.http;

import request.HttpRequest;

import java.io.IOException;
import java.net.Socket;

public interface HttpRequestHandler {
    void handleHttpRequest(Socket clientSocket, HttpRequest httpRequest) throws IOException;
}
