package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieAPITest {
    MovieAPI api = new MovieAPI();

    @Test
    void fetchAllMovies_returnsMovieList() throws IOException {
        List<Movie> movies = api.fetchMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        System.out.println("Anzahl der Filme: " + movies.size());
    }
    @Test
    void fetchMovies_withQuery_returnsMatchingMovies() throws IOException {
        List<Movie> movies = api.fetchMoviesWithParams("Matrix",null,null,null);
        assertNotNull(movies);
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().toLowerCase().contains("matrix")));
        System.out.println("Gefundene Filme mit 'Matrix': " + movies.size());
    }

    @Test
    void fetchMovies_withGenre_returnsOnlyThatGenre() throws IOException {
        List<Movie> movies = api.fetchMoviesWithParams(null,"Drama",null,null);
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        System.out.println("Filme mit Genre 'Drama': " + movies.size());
    }
    @Test
    void fetchMovies_withReleaseYear_andRating() throws IOException {
        String expectedYear = "2020";
        double expectedRating = 5.0;
        List<Movie> movies = api.fetchMoviesWithParams(null,null,expectedYear,String.valueOf(expectedRating));
        assertNotNull(movies);
        if (movies.isEmpty()){
            System.out.println("Es gibt keine Filme mit den Angaben releaseYear: " + expectedYear + " und ratingFrom: " + expectedRating);
        }else {
            System.out.println("Filme gefunden: ");
            for (Movie m : movies){
                System.out.println(m.getTitle() + " Year:" + m.getReleaseYear() + " Rating:" + m.getRating());
            }
        }
    }
    @Test
    void fetchMovies_withNoMatch_returnsEmptyList() throws IOException {
        List<Movie> movies = api.fetchMoviesWithParams("xyz01",null,null,null);
        assertNotNull(movies);
        assertTrue(movies.isEmpty());
        System.out.println("Found no match. Returns Empty List");
    }
}