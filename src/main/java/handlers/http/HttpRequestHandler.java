package handlers.http;

import lombok.AllArgsConstructor;
import request.HttpRequest;
import util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@AllArgsConstructor
public abstract class HttpRequestHandler {
    Socket clientSocket;
    HttpRequest httpRequest;
//    BufferedReader br;

    public abstract void handleHttpRequest() throws IOException;

    protected static void handle404NotFound(OutputStream os) throws IOException {
        os.write(Constants.HTTP_NOT_FOUND_DEFAULT_RESPONSE.getBytes());
        os.write(Constants.DEFAULT_LINE_SEPARATOR.getBytes());
        os.flush();
        os.close();
    }
}
