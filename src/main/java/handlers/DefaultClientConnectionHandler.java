package handlers;

import handlers.http.GenericHttpRequestHandler;
import handlers.http.HttpRequestHandler;
import request.HttpRequest;
import util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DefaultClientConnectionHandler implements ClientConnectionHandler {
    private HttpRequestHandler httpRequestHandler = new GenericHttpRequestHandler();
    @Override
    public void handleClientConnection(Socket clientSocket) throws IOException {

        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        HttpRequest httpRequest = new HttpRequest();

        parseRequestLine(br, httpRequest);

        parseRequestHeaderKeyValues(br, httpRequest);

        httpRequestHandler.handleHttpRequest(clientSocket, httpRequest);

    }

    private static void parseRequestHeaderKeyValues(BufferedReader br, HttpRequest httpRequest) throws IOException {
        // read the rest of the request header
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Invalid HTTP Request Header");
        }

        try {
            while (line.length() > 0) {
                httpRequest.addRequestHeaderFromLine(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Invalid HTTP Request Header");
        }
    }

    private static void parseRequestLine(BufferedReader br, HttpRequest httpRequest) {
        // Read the first line of the request - the request line.
        String requestLine = null;
        try {
            requestLine = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Missing HTTP Request Line");
        }
        if (requestLine.length() == 0) {
            System.err.println("Received empty request line.");
            throw new RuntimeException("Received empty request line.");
        }

        httpRequest.parseRequestLine(requestLine);
    }
}
