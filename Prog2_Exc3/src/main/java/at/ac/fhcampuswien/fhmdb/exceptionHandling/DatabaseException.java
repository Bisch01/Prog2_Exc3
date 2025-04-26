package at.ac.fhcampuswien.fhmdb.exceptionHandling;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
