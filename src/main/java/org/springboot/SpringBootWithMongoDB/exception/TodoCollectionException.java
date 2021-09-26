package org.springboot.SpringBootWithMongoDB.exception;

public class TodoCollectionException extends Exception {

    private static final long serialVersionUID = 1;

    public TodoCollectionException(String message) {
        super(message);
    }

    public static String notFoundException(String id) {

        return "Todo with id " + id + " not found";
    }

    public static String todoAlreadyExists() {

        return "Todo with given name already exists";
    }
}
