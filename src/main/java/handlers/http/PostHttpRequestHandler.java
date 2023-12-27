package handlers.http;

import lombok.AllArgsConstructor;
import request.HttpRequest;

import java.io.IOException;
import java.net.Socket;

public class PostHttpRequestHandler extends HttpRequestHandler {
    public PostHttpRequestHandler(Socket clientSocket, HttpRequest httpRequest) {
        super(clientSocket, httpRequest);
    }

    @Override
    public void handleHttpRequest() throws IOException {
        clientSocket.getOutputStream().close();
    }
}
