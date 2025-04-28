package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "movies")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, unique = true)
    private String apiId; // API- oder externe ID

    @DatabaseField
    private String title;

    @DatabaseField
    private String genres; // Kommagetrennter String, z.B. "Action,Drama,Comedy"

    @DatabaseField
    private String description;

    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private String imgUrl;

    @DatabaseField
    private int lengthInMinutes;

    @DatabaseField
    private double rating;

    public MovieEntity() {}

    public MovieEntity(String apiId, String title, String genres,
                       String description, int releaseYear, String imgUrl,
                       int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.genres = genres;
        this.description = description;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public long getId() { return id; }
    public String getApiId() { return apiId; }
    public String getTitle() { return title; }
    public String getGenres() { return genres; }

    public void setApiId(String apiId) { this.apiId = apiId; }
    public void setTitle(String title) { this.title = title; }
    public void setGenres(String genres) { this.genres = genres; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
