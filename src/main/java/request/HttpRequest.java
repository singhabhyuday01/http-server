package request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class HttpRequest {
    String httpMethod;
    String path;
    String httpVersion;

    Map<HttpRequestHeaderKeys, String> headers = new HashMap<>();

    public void parseRequestLine(String requestLine) {
        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            System.err.println("Invalid HTTP Request");
            throw new RuntimeException("Invalid HTTP Request Line");
        }
        httpMethod = parts[0];
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
