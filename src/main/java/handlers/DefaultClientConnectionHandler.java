package handlers;

import handlers.http.GenericHttpRequestHandler;
import request.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DefaultClientConnectionHandler extends ClientConnectionHandler {

    public DefaultClientConnectionHandler(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void handleClientConnection() throws IOException {

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        parseRequestLine(httpRequest.br, httpRequest);

        parseRequestHeaderKeyValues(httpRequest.br, httpRequest);

        new GenericHttpRequestHandler(clientSocket, httpRequest).handleHttpRequest();

    }

    private static void parseRequestHeaderKeyValues(BufferedReader br, HttpRequest httpRequest) throws IOException {
        // read the rest of the request header
        String line;
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

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
