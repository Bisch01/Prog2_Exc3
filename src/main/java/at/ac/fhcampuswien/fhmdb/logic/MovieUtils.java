package at.ac.fhcampuswien.fhmdb.logic;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieUtils {

    public static String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())       // alle Schauspieler*innen aus allen Filmen
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting())) // zähle, wie oft jeder vorkommt
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())                    // finde den mit den meisten Vorkommen
                .map(Map.Entry::getKey)                               // gib den Namen zurück
                .orElse("Kein Schauspieler gefunden");                // Fallback bei leerer Liste
    }

    public static int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())   // wandle jeden Titel in seine Länge (int) um
                .max()                                          // finde das Maximum
                .orElse(0);                                     // falls Liste leer ist
    }

    public static long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors() != null)                          // sicherstellen, dass es überhaupt Regisseure gibt
                .filter(movie -> movie.getDirectors().stream()
                        .anyMatch(d -> d.equalsIgnoreCase(director)))                  // Regisseur kommt vor (case-insensitive)
                .count();
    }

    public static List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

}
