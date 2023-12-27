package handlers.http;

import lombok.AllArgsConstructor;
import request.HttpRequest;
import request.HttpRequestMethod;

import java.io.IOException;
import java.net.Socket;

public class GenericHttpRequestHandler extends HttpRequestHandler {

    public GenericHttpRequestHandler(Socket clientSocket, HttpRequest httpRequest) {
        super(clientSocket, httpRequest);
    }

    @Override
    public void handleHttpRequest() throws IOException {
        if (httpRequest.getHttpMethod() == HttpRequestMethod.GET) {
            new GetHttpRequestHandler(clientSocket, httpRequest).handleHttpRequest();
        } else if (httpRequest.getHttpMethod() == HttpRequestMethod.POST) {
            new PostHttpRequestHandler(clientSocket, httpRequest).handleHttpRequest();
        } else {
            throw new RuntimeException("Unsupported HTTP Method");
        }
    }
}
