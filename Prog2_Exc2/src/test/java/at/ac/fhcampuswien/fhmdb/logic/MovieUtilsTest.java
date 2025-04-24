package at.ac.fhcampuswien.fhmdb.logic;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieUtilsTest {

    // Liste mit Beispiel-Filmen, die in allen Tests verwendet wird
    private List<Movie> testMovies;

    // Wird vor jedem Test ausgeführt, um die Ausgangsdaten zu initialisieren
    @BeforeEach
    void setUp() {
        testMovies = List.of(
                new Movie("Whispers in the Fog", "A mysterious drama set in 1940s London",
                        List.of("Mystery", "Drama"), "wif001",
                        List.of("Iris Vale", "Theo Marsh"), List.of("Clara Holt"), 2016, 7.6),

                new Movie("Echoes of Tomorrow", "A time-travel thriller with political undertones",
                        List.of("Sci-Fi", "Thriller"), "eot002",
                        List.of("Iris Vale", "Sam Raines"), List.of("Clara Holt"), 2021, 8.3),

                new Movie("Canvas of the Mind", "A psychological art-house film about identity",
                        List.of("Drama", "Indie"), "cotm003",
                        List.of("Theo Marsh", "Aria Quinn"), List.of("Leo Brenn"), 2013, 6.9)
        );
    }

    // Testet, ob der beliebteste Schauspieler korrekt erkannt wird (häufigster im mainCast)
    @Test
    void testGetMostPopularActor() {
        String mostPopular = MovieUtils.getMostPopularActor(testMovies);
        assertEquals("Iris Vale", mostPopular);
    }

    // Testet, ob die Länge des längsten Filmtitels korrekt zurückgegeben wird
    @Test
    void testGetLongestMovieTitle() {
        int length = MovieUtils.getLongestMovieTitle(testMovies);
        assertEquals("Whispers in the Fog".length(), length);
    }

    // Testet, ob die Anzahl der Filme eines bestimmten Regisseurs korrekt gezählt wird
    @Test
    void testCountMoviesFrom() {
        long count = MovieUtils.countMoviesFrom(testMovies, "Clara Holt");
        assertEquals(2, count);
    }

    // Testet, ob Filme korrekt nach Erscheinungsjahr gefiltert werden
    @Test
    void testGetMoviesBetweenYears() {
        List<Movie> filtered = MovieUtils.getMoviesBetweenYears(testMovies, 2015, 2021);
        assertEquals(2, filtered.size());

        // Zusätzliche Absicherung: jeder gefundene Film muss im gesuchten Jahrbereich liegen
        assertTrue(filtered.stream().allMatch(m ->
                m.getReleaseYear() >= 2015 && m.getReleaseYear() <= 2021));
    }
}
