package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "Movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    //@DatabaseField
    //private String apiID;

    @DatabaseField(columnName = "title")
    private String title;

    public MovieEntity() {

    }
    public MovieEntity(String title) {
        this.title = title;
    }
    /*
    @DatabaseField
    private String description;
    @DatabaseField
    private String genres; //nicht sicher ob List<>genres übergeben werden soll, kann noch geändert werden
    /*@DatabaseField
    private List<String> genres;
    @DatabaseField
    private List<String> mainCast;
    @DatabaseField
    private List<String> directors;
     //
    @DatabaseField
    private int releaseYear;
    @DatabaseField
    private String imgURL;
    @DatabaseField
    private int lengthInMinutes;
    @DatabaseField
    private double rating;
    */
}
