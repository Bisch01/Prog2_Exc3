package at.ac.fhcampuswien.fhmdb.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Movie-Watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String apiId; // Verweis auf MovieEntity oder die API-ID

    public WatchlistMovieEntity() {}

    public WatchlistMovieEntity(String apiId) {
        this.apiId = apiId;
    }

    public long getId() { return id; }
    public String getApiId() { return apiId; }
}

