package centene.github.io.centene_enrollee.config;


public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String message) {
        super(message);
    }

    public CustomNotFoundException() {
    }
}
