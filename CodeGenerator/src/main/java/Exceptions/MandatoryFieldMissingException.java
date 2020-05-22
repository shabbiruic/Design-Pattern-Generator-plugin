package Exceptions;

public class MandatoryFieldMissingException extends Exception {

    public MandatoryFieldMissingException(String message) {
        super(message + " is Mandatory it can't be null or empty");
    }
}