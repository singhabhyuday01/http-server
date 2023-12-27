package handlers.http;

import lombok.AllArgsConstructor;
import request.HttpRequest;

import java.io.IOException;
import java.net.Socket;

@AllArgsConstructor
public abstract class HttpRequestHandler {
    Socket clientSocket;
    HttpRequest httpRequest;
    public abstract void handleHttpRequest() throws IOException;
}
