package handlers.http;

import request.HttpRequest;
import util.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class GetHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleHttpRequest(Socket clientSocket, HttpRequest httpRequest) throws IOException {
        String[] parts = httpRequest.getPath().split("/", 3);

        testEchoRandomStringParser(parts, httpRequest, clientSocket);

    }

    private void testEchoRandomStringParser(String[] parts, HttpRequest httpRequest, Socket clientSocket) throws IOException {
        OutputStream os = clientSocket.getOutputStream();
        if (parts.length > 2 && parts[1].equals("echo")) {
            String randomString = parts[2];
            os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
            os.write(Constants.CONTENT_TYPE_TEXT_HEADER_LINE.getBytes());
            os.write((Constants.CONTENT_LENGTH_HEADER_KEY + randomString.length() + "\r\n").getBytes());
            os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
            os.write(randomString.getBytes());
            os.flush();
            os.close();
            return;
        }
        if (httpRequest.getPath().equals("/")) {
            os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
            os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
            os.flush();
            os.close();
            return;
        }
        os.write(Constants.HTTP_NOT_FOUND_DEFAULT_RESPONSE.getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
        os.flush();
        os.close();
    }

}
