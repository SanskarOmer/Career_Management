package in.sanskar.careerManagement.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Object identifier) {
        super(resource + " not found with identifier: " + identifier);
    }
}