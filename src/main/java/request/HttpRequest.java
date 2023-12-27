package request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class HttpRequest {
    HttpRequestMethod httpMethod;
    String path;
    String httpVersion;
    public BufferedReader br;

    Map<HttpRequestHeaderKeys, String> headers = new HashMap<>();

    public void parseRequestLine(String requestLine) {
        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            System.err.println("Invalid HTTP Request");
            throw new RuntimeException("Invalid HTTP Request Line");
        }
        httpMethod = HttpRequestMethod.valueOf(parts[0]);
        path = parts[1];
        httpVersion = parts[2];
    }

    public void addRequestHeaderFromLine(String headerLine) {
        String[] parts = headerLine.split(": *", 2);
        if (parts.length != 2) {
            throw new RuntimeException("Invalid HTTP Request Header");
        }
        headers.put(HttpRequestHeaderKeys.valueOf(parts[0].toUpperCase().replace("-", "_")), parts[1]);
    }

}
