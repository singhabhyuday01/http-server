package handlers.http;

import lombok.AllArgsConstructor;
import request.HttpRequest;
import request.HttpRequestHeaderKeys;
import util.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class GetHttpRequestHandler extends HttpRequestHandler {
    public GetHttpRequestHandler(Socket clientSocket, HttpRequest httpRequest) {
        super(clientSocket, httpRequest);
    }

    @Override
    public void handleHttpRequest() throws IOException {
        String[] parts = httpRequest.getPath().split("/", 3);

        testEchoRandomStringParser(parts, httpRequest, clientSocket);

    }

    private void testEchoRandomStringParser(String[] parts, HttpRequest httpRequest, Socket clientSocket) throws IOException {
        OutputStream os = clientSocket.getOutputStream();
        if (parts.length >= 2) {
            if (parts[1].equals("echo")) {
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
            if (parts[1].equals("user-agent")) {
                os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
                os.write(Constants.CONTENT_TYPE_TEXT_HEADER_LINE.getBytes());
                os.write((Constants.CONTENT_LENGTH_HEADER_KEY + httpRequest.getHeaders().get(HttpRequestHeaderKeys.USER_AGENT).length() + "\r\n").getBytes());
                os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
                os.write(httpRequest.getHeaders().get(HttpRequestHeaderKeys.USER_AGENT).getBytes());
                os.flush();
                os.close();
                return;
            }
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
