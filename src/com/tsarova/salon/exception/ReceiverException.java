package com.tsarova.salon.exception;

/**
 * @author Veronika Tsarova
 */
public class ReceiverException extends Exception {
    public ReceiverException() {
    }

    public ReceiverException(String message) {
        super(message);
    }

    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiverException(Throwable cause) {
        super(cause);
    }
}