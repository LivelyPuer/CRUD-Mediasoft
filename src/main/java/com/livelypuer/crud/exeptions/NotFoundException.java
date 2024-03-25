package com.livelypuer.crud.exeptions;

/**
 * This class is used to throw exceptions when a resource is not found.
 *
 * @author <NAME>
 */
public class NotFoundException extends Exception {

    /**
     * Constructs a new NotFoundException with the specified error message.
     *
     * @param errorMessage the error message
     */
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
