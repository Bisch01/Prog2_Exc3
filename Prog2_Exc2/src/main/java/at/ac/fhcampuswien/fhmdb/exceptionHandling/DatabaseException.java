package at.ac.fhcampuswien.fhmdb.exceptionHandling;

import java.sql.SQLException;

public class DatabaseException extends Throwable {
    public DatabaseException(String s, SQLException e) {
    }
}
