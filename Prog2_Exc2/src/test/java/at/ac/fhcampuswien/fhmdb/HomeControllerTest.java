package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class HomeControllerTest {
    private HomeController homeController;
    private List<Movie> movies;
    @BeforeAll
    static void setupJavaFX(){ //Methode um JavaFX Simulation zu initialisieren
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUpInstance(){                           //Methode wird vor jedem Test (before each) aufgerufen, um Instanz zu erstellen
        homeController = new HomeController();
        homeController.genreComboBox = new JFXComboBox<>(); //JFX methoden manuell initialisieren um JavaFX Umgebung zu simulieren
        homeController.searchField = new JFXTextField();
        homeController.movieListView = new JFXListView<>();
        homeController.searchBtn = new JFXButton();
        homeController.sortBtn = new JFXButton();


        //Liste mit Testdaten befüllen
        try {
            homeController.allMovies = new at.ac.fhcampuswien.fhmdb.models.MovieAPI().fetchMovies();
        } catch (Exception e){
            fail("API Fehlgeschlagen" + e.getMessage());
        }
    }

    @Test
    void testInitializeMovieList() {
        HomeController homeController = new HomeController();
    }

    @Test
    void movies_sorted_ascending_alphabetic(){                  //Aufruf sortMovies function aus HomeController
        List<Movie> unsortedMovies = new ArrayList<>(homeController.allMovies);
        homeController.sortMovies(true);
        List<Movie> sortedMovies = new ArrayList<>(homeController.allMovies);
        unsortedMovies.sort(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER));
        assertEquals(sortedMovies, unsortedMovies);
    }


    @Test
    void movies_sorted_descending_alphabetic() {
        List<Movie> unsortedMovies = new ArrayList<>(homeController.allMovies);
        homeController.sortMovies(false);
        List<Movie> sortedMovies = new ArrayList<>(homeController.allMovies);
        unsortedMovies.sort((m1, m2) -> m2.getTitle().compareTo(m1.getTitle()));
        assertEquals(unsortedMovies, sortedMovies);
    }


    @Test
    void filter_no_results_found(){                             // Wenn es keine passenden Filme gibt, bleibt die Liste leer
        homeController.searchField.setText("xyz");
        homeController.genreComboBox.setValue("Horror");
        homeController.applyFilters();

        assertTrue(homeController.movieListView.getItems().isEmpty());
    }



    @Test
    void does_applyFilters_filterGenreCorrectly(){
        homeController.genreComboBox = new com.jfoenix.controls.JFXComboBox<>();
        homeController.genreComboBox.setValue("DRAMA");
        homeController.searchField = new javafx.scene.control.TextField("");
        homeController.releaseYearInput = new javafx.scene.control.TextField("");
        homeController.ratingFromInput = new javafx.scene.control.TextField("");

        homeController.applyFilters();
        List<Movie> result = homeController.allMovies;
        assertFalse(result.isEmpty(), "Filme mit der Genre DRAMA existieren nicht");
        assertTrue(result.stream().allMatch(m -> m.getGenres().contains("DRAMA")),"Nicht alle Filme enthalten die Genre DRAMA");
    }

    @Test
    //Wenn kein Filter gesetzt ist, sollten alle Filme bleiben
    void applyFilters_without_filters_should_show_all_movies() {
        homeController.applyFilters(); // Keine Filter gesetzt

        assertEquals(homeController.allMovies.size(), homeController.movieListView.getItems().size());
    }

    @Test
    void applyFilters_with_search_text_should_show_only_matching_movie() {
        //Wenn nach Text gefiltert wird, soll nur gesuchter Film gezeigt werden
        homeController.searchField.setText("Inception");
        homeController.applyFilters();

        assertEquals(1, homeController.movieListView.getItems().size());
    }
    @Test
    void applyFilters_with_search_text_should_show_given_Movie() {
        //Wenn nach Text gefiltert wird, soll nur gesuchter Film gezeigt werden
        homeController.searchField.setText("Avatar");
        homeController.applyFilters();

        assertEquals(1, homeController.movieListView.getItems().size());
    }

    @Test
    void applyFilters_with_non_matching_search_text_should_show_no_movies() {
        //Test ob Liste leer bleibt, wenn man nach nicht vorhandenem Text sucht
        homeController.searchField.setText("NichtVorhanden");
        homeController.applyFilters();

        assertEquals(0, homeController.movieListView.getItems().size()); // Kein Film gefunden
    }

    @Test
    void sortMovies_twice_should_not_change_order() {
        //Test ob Sortierung gleich bleibt, wenn man 2 mal soertieren drückt
        homeController.sortMovies(true); // 1. Mal sortieren
        List<String> firstSort = homeController.allMovies.stream().map(Movie::getTitle).toList();

        homeController.sortMovies(true); // 2. Mal sortieren
        List<String> secondSort = homeController.allMovies.stream().map(Movie::getTitle).toList();

        assertEquals(firstSort, secondSort); // Reihenfolge bleibt gleich
    }

}

