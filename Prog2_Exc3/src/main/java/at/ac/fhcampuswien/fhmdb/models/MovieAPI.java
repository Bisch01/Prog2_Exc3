package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.exceptionHandling.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;



public class MovieAPI {

    public static final String URL = "https://prog2.fh-campuswien.ac.at/movies";
    // Initialisierung OkHttp Client
    private final OkHttpClient client = new OkHttpClient();
    // Initialisierung Gson
    private final Gson gson = new Gson();

    // Methode run bekommt eine URL übergeben
    // Sendet HTTP-GET Anfrage an URL
    public List<Movie> fetchMoviesWithParams(String query, String genre,String releaseYear, String ratingFrom) throws MovieApiException { // Hauptmethode um die URL aufzubauen
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
        addQueryParam(urlBuilder, query);
        addGenreParam(urlBuilder,genre);
        addReleaseYearParam(urlBuilder, releaseYear);
        addRatingFromParam(urlBuilder, ratingFrom);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent","http.agent")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()){
                throw new MovieApiException("API-Aufruf Fehler: " + response);
            }
            String json = response.body().string();
            Type movieListType = new TypeToken<List<Movie>>() {}.getType();
            return gson.fromJson(json, movieListType);
        } catch (IOException e) {
            throw new MovieApiException("Verbindungsfehler mit der API-Anfrage" + e);
        }

    }

    public List<Movie> fetchMovies() throws MovieApiException {

        Request request = new Request.Builder()
                .url(URL)
                .addHeader("User-Agent", "http.agent")
                .build();

        //Ausführung der HTTP-Anfrage und Antwrtverarbeitung
        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()){
                throw new MovieApiException("Unexpected code " + response);
            }
            String json = response.body().string();
            Type movieListType = new TypeToken<List<Movie>>() {}.getType();
            return gson.fromJson(json, movieListType);
        } catch (IOException e) {
            throw new MovieApiException("Verbindungsfehler zur API", e);
        }
    }
    // Hilfsmethode für die Hauptmethode damit man diese leichter testen kann
    private void addQueryParam(HttpUrl.Builder urlBuilder, String query) {
        if(query != null && !query.isBlank()){
            urlBuilder.addQueryParameter("query",query);
        }
    }
    private void addGenreParam(HttpUrl.Builder urlBuilder, String genre) {
        if(genre != null && !genre.equalsIgnoreCase("Alle Genres")){
            urlBuilder.addQueryParameter("genre",genre);
        }
    }
    private void addReleaseYearParam(HttpUrl.Builder urlBuilder, String releaseYear) {
        if(releaseYear != null && !releaseYear.isBlank()){
            urlBuilder.addQueryParameter("releaseYear",releaseYear);
        }
    }
    private void addRatingFromParam(HttpUrl.Builder urlBuilder, String ratingFrom) {
        if(ratingFrom != null && !ratingFrom.isBlank()){
            urlBuilder.addQueryParameter("ratingFrom",ratingFrom);
        }
    }
}
