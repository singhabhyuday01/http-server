package handlers.http;

import request.HttpRequest;

import java.io.IOException;
import java.net.Socket;

public class PostHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleHttpRequest(Socket clientSocket, HttpRequest httpRequest) throws IOException {
        clientSocket.getOutputStream().close();
    }
}
