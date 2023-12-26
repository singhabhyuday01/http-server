package handlers;

import request.HttpRequest;
import util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DefaultClientConnectionHandler implements ClientConnectionHandler {
    @Override
    public void handleClientConnection(Socket clientSocket) throws IOException {

        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        HttpRequest httpRequest = new HttpRequest();

        parseRequestLine(br, httpRequest);

        parseRequestHeaderKeyValues(br, httpRequest);

        OutputStream os = clientSocket.getOutputStream();

        if (httpRequest.getPath().equals("/")) {
            os.write(Constants.HTTP_OK_DEFAULT_RESPONSE.getBytes());
            os.flush();
            os.close();
            return;
        }
        os.write(Constants.HTTP_NOT_FOUND_DEFAULT_RESPONSE.getBytes());
        os.flush();
        os.close();
    }

    private static void parseRequestHeaderKeyValues(BufferedReader br, HttpRequest httpRequest) {
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
        String requestLine;
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
