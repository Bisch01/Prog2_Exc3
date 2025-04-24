package at.ac.fhcampuswien.fhmdb.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Movie-Watchlist")
public class WatchlistMovieEntity {
    @DatabaseField
    private int id;
}
