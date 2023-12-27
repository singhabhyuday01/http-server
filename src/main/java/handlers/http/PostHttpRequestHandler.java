package handlers.http;

import lombok.AllArgsConstructor;
import request.HttpRequest;
import util.State;

import java.io.*;
import java.net.Socket;

import static util.Constants.DEFAULT_LINE_SEPARATOR;
import static util.Constants.HTTP_CREATED_DEFAULT_HEADER_RESPONSE_LINE;

public class PostHttpRequestHandler extends HttpRequestHandler {
    public PostHttpRequestHandler(Socket clientSocket, HttpRequest httpRequest) {
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
            if (parts[1].equals("files")) {
                handlePostFileRequest(parts[2], clientSocket);
                return;
            }
        }

        handle404NotFound(os);
    }

    private void handlePostFileRequest(String fileName, Socket clientSocket) throws IOException {
        File file = new File(State.directory + "/" + fileName);
        file.createNewFile();

        FileWriter fos = new FileWriter(file);

        char[] ch = new char[1024];
        int bytesRead = 0;
        while ((bytesRead = httpRequest.br.read(ch)) > 0) {
            fos.write(ch, 0, bytesRead);
            if (bytesRead < 1024) {
                break;
            }
        }

        fos.close();
//        httpRequest.br.close();

        OutputStream os = clientSocket.getOutputStream();
        os.write(HTTP_CREATED_DEFAULT_HEADER_RESPONSE_LINE.getBytes());
        os.write(DEFAULT_LINE_SEPARATOR.getBytes());
        os.flush();
        os.close();
    }

}
