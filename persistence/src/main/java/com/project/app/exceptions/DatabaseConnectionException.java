package com.project.app.exceptions;

public class DatabaseConnectionException extends RuntimeException {

    private static final String FAILED_TO_GET_FROM_DS = "Could not retrieve connection! Failed to get from DataSource";

    private DatabaseConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static DatabaseConnectionException withFailedToRetrieveFromDatasource(final Throwable cause) {
        return new DatabaseConnectionException(FAILED_TO_GET_FROM_DS, cause);
    }

}
