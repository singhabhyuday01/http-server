package request;

public enum HttpRequestHeaderKeys {
    HOST("Host"),
    CONNECTION("Connection"),
    CACHE_CONTROL("Cache-Control"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language");

    private final String key;

    HttpRequestHeaderKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
