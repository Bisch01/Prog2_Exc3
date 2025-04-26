package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exceptionHandling.DatabaseException;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private final Dao<MovieEntity, String> dao;

    public MovieRepository(Dao<MovieEntity, String> dao) {
        this.dao = dao;
    }

    public void addMovie(MovieEntity movie) throws DatabaseException {
        try{

            dao.createIfNotExists(movie);
        } catch (SQLException e){
            throw new DatabaseException("Film konnte nicht hinzugefügt werden", e);
        }
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try{
            return dao.queryForAll();
        } catch (SQLException e){
            throw new DatabaseException("Fehler beim auslesen der Filme", e);
        }
    }
    public void deleteMovie(String apiID) throws DatabaseException {
        try {
            MovieEntity m = dao.queryForId(apiID);
            if (m != null){
                dao.delete(m);
            }
        }catch (SQLException e){
            throw new DatabaseException("Fehler beim Löschen des Films", e);
        }
    }
}
