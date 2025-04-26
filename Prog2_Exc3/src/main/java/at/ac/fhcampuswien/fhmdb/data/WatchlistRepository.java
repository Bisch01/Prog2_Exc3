package at.ac.fhcampuswien.fhmdb.data;


import at.ac.fhcampuswien.fhmdb.exceptionHandling.DatabaseException;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private final Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository(Dao<WatchlistMovieEntity, Long> dao) {
        this.dao = dao;
    }

    // Watchlist-Eintrag hinzufügen (nur, wenn er noch nicht existiert)
    public boolean addToWatchlist(String apiId) throws DatabaseException {
        try {
            List<WatchlistMovieEntity> entries = dao.queryForEq("apiId", apiId);
            if (entries.isEmpty()) {
                dao.create(new WatchlistMovieEntity(apiId));
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Fehler beim Hinzufügen zur Watchlist", e);
        }
    }

    // Aus Watchlist entfernen
    public void removeFromWatchlist(String apiId) throws DatabaseException {
        try {
            List<WatchlistMovieEntity> entries = dao.queryForEq("apiId", apiId);
            for (WatchlistMovieEntity entry : entries) {
                dao.delete(entry);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fehler beim Entfernen aus der Watchlist", e);
        }
    }

    // Alle Watchlist-Einträge abrufen
    public List<WatchlistMovieEntity> getAllWatchlistEntries() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Fehler beim Auslesen der Watchlist", e);
        }
    }
}
