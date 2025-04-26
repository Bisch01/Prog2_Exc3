package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "Movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String apiId; // API- oder externe ID

    @DatabaseField
    private String title;

    @DatabaseField
    private String genres; // Kommagetrennter String, z.B. "Action,Drama,Comedy"

    // ggf. weitere Felder nach Bedarf...

    public MovieEntity() {}

    public MovieEntity(String apiId, String title, String genres) {
        this.apiId = apiId;
        this.title = title;
        this.genres = genres;
    }

    public long getId() { return id; }
    public String getApiId() { return apiId; }
    public String getTitle() { return title; }
    public String getGenres() { return genres; }

    public void setApiId(String apiId) { this.apiId = apiId; }
    public void setTitle(String title) { this.title = title; }
    public void setGenres(String genres) { this.genres = genres; }
}
