package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exceptionHandling.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private final Dao<MovieEntity, Long> dao;

    public MovieRepository(Dao<MovieEntity, Long> dao) {
        this.dao = dao;
    }

    public void addMovie(MovieEntity movie) throws DatabaseException {
        try{
            List<MovieEntity> existingMovies = dao.queryForEq("apiId", movie.getApiId());
            if (existingMovies.isEmpty()) {
                dao.create(movie);
            }else {
                MovieEntity existingMovie = existingMovies.get(0);
                movie.setApiId(existingMovie.getApiId());
                dao.update(movie);
            }

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
            MovieEntity m = dao.queryForId(Long.valueOf(apiID));
            if (m != null){
                dao.delete(m);
            }
        }catch (SQLException e){
            throw new DatabaseException("Fehler beim Löschen des Films", e);
        }
    }
}
