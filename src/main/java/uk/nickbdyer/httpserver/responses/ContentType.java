package uk.nickbdyer.httpserver.responses;

public enum ContentType {
    TXT("text/plain"),
    PNG("image/png"),
    GIF("image/gif"),
    JPEG("image/jpeg"),
    JPG("image/jpeg"),
    CSS("text/css");

    public final String type;

    ContentType(String type) {
        this.type = type;
    }
}
