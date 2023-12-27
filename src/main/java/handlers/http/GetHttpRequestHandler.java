package handlers.http;

import request.HttpRequest;
import request.HttpRequestHeaderKeys;
import util.Constants;
import util.State;

import java.io.File;
import java.io.FileInputStream;
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

        pathHandlers(parts, httpRequest, clientSocket);

    }

    private void pathHandlers(String[] parts, HttpRequest httpRequest, Socket clientSocket) throws IOException {
        OutputStream os = clientSocket.getOutputStream();
        if (parts.length >= 2) {
            if (parts[1].equals("echo")) {
                handleEchoRequest(parts, os);
                return;
            }
            if (parts[1].equals("user-agent")) {
                handleUserAgentRequest(httpRequest, os);
                return;
            }
            if (parts[1].equals("files")) {
                handleFilesRequest(parts[2], os);
                return;
            }
        }
        if (httpRequest.getPath().equals("/")) {
            handleEmptyPath(os);
            return;
        }
        handle404NotFound(os);
    }

    private void handleFilesRequest(String fileName, OutputStream os) throws IOException {

        File file = new File(State.directory + "/" + fileName);

        if (!file.exists() || file.isDirectory()) {
            handle404NotFound(os);
        }

        os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
        os.write(Constants.CONTENT_TYPE_OCTET_HEADER_LINE.getBytes());
        os.write((Constants.CONTENT_LENGTH_HEADER_KEY + file.length() + "\r\n").getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());

        // read from file and write to output stream

        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(file);

        while (fis.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();

    }

    private static void handleEmptyPath(OutputStream os) throws IOException {
        os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
        os.flush();
        os.close();
    }

    private static void handle404NotFound(OutputStream os) throws IOException {
        os.write(Constants.HTTP_NOT_FOUND_DEFAULT_RESPONSE.getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
        os.flush();
        os.close();
    }

    private static void handleUserAgentRequest(HttpRequest httpRequest, OutputStream os) throws IOException {
        os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
        os.write(Constants.CONTENT_TYPE_TEXT_HEADER_LINE.getBytes());
        os.write((Constants.CONTENT_LENGTH_HEADER_KEY + httpRequest.getHeaders().get(HttpRequestHeaderKeys.USER_AGENT).length() + "\r\n").getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
        os.write(httpRequest.getHeaders().get(HttpRequestHeaderKeys.USER_AGENT).getBytes());
        os.flush();
        os.close();
    }

    private static void handleEchoRequest(String[] parts, OutputStream os) throws IOException {
        String randomString = parts[2];
        os.write(Constants.HTTP_OK_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
        os.write(Constants.CONTENT_TYPE_TEXT_HEADER_LINE.getBytes());
        os.write((Constants.CONTENT_LENGTH_HEADER_KEY + randomString.length() + "\r\n").getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
        os.write(randomString.getBytes());
        os.flush();
        os.close();
    }

}
