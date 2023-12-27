package handlers.http;

import request.HttpRequest;
import request.HttpRequestMethod;

import java.io.IOException;
import java.net.Socket;

public class GenericHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleHttpRequest(Socket clientSocket, HttpRequest httpRequest) throws IOException {
        if (httpRequest.getHttpMethod() == HttpRequestMethod.GET) {
            new GetHttpRequestHandler().handleHttpRequest(clientSocket, httpRequest);
        } else if (httpRequest.getHttpMethod() == HttpRequestMethod.POST) {
            new PostHttpRequestHandler().handleHttpRequest(clientSocket, httpRequest);
        } else {
            throw new RuntimeException("Unsupported HTTP Method");
        }
    }
}
