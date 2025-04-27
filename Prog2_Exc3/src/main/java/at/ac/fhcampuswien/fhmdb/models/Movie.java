package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    private String title;
    private String description;
    // done
    private List <String> genres;

    //Mehr Attribute wegen API
    @SerializedName("id")
    private String apiId;

    private List<String> mainCast;
    private List<String> directors;
    private int releaseYear;
    private double rating;

    //leerer Konstruktor für GSON
    public Movie(){}


    //Konstruktor
    public Movie(String title, String description, List<String> genres, String apiId, List<String> mainCast, List<String> directors, int releaseYear, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.apiId = this.apiId;
        this.mainCast = mainCast;
        this.directors = directors;
        this.releaseYear = releaseYear;
        this.rating = rating;

    }

    //Setter Methoden

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    //Getter-Methoden
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {return genres;}

    public String getapiId(){return apiId;}

    public List<String> getMainCast(){return mainCast;}

    public List<String> getDirectors(){return directors;}

    public int getReleaseYear(){return releaseYear;}

    public double getRating(){return rating;}



//    public static List<Movie> initializeMovies(){
//        List<Movie> movies = new ArrayList<>();
//
//        movies.add(new Movie("Interstellar", "Eine Reise durch das Weltall und die Zeit.", new ArrayList<>(List.of("Science_Fiction", "Adventure", "Drama"))));
//        movies.add(new Movie("Joker", "Die düstere Geschichte eines gebrochenen Mannes.", new ArrayList<>(List.of("Crime", "Drama", "Thriller"))));
//        movies.add(new Movie("Parasite", "Eine arme Familie dringt in das Leben einer reichen Familie ein.", new ArrayList<>(List.of("Drama", "Thriller"))));
//        movies.add(new Movie("Mad Max: Fury Road", "Eine postapokalyptische Verfolgungsjagd.", new ArrayList<>(List.of("Action", "Adventure", "Science_Fiction"))));
//        movies.add(new Movie("The Grand Budapest Hotel", "Die Abenteuer eines legendären Hotelportiers.", new ArrayList<>(List.of("Comedy", "Drama"))));
//        movies.add(new Movie("Spirited Away", "Ein magisches Abenteuer in einer geheimnisvollen Welt.", new ArrayList<>(List.of("Animation", "Fantasy", "Adventure"))));
//        movies.add(new Movie("Gladiator", "Ein römischer General wird zum Gladiator.", new ArrayList<>(List.of("Action", "Drama", "History"))));
//        movies.add(new Movie("Schindler’s List", "Die bewegende Geschichte eines Mannes im Zweiten Weltkrieg.", new ArrayList<>(List.of("History", "Drama", "War"))));
//        movies.add(new Movie("Get Out", "Ein unheimlicher Besuch bei den Schwiegereltern.", new ArrayList<>(List.of("Horror", "Thriller", "Mystery"))));
//        movies.add(new Movie("The Social Network", "Die Geschichte hinter der Gründung von Facebook.", new ArrayList<>(List.of("Biography", "Drama"))));
//
//        return movies;
//    }
}
