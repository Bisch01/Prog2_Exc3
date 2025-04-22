package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<String> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public TextField releaseYearInput;

    @FXML
    public TextField ratingFromInput;

    public List<Movie> allMovies = new ArrayList<>();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //echt Daten von API laden
            MovieAPI api = new MovieAPI();
            allMovies = api.fetchMovies();
            observableMovies.setAll(allMovies);
        }catch(IOException e){
            System.out.println("Fehler beim Laden der Filme: " + e.getMessage());
        }

        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());



        // Genre-Filter initialisieren
        genreComboBox.setPromptText("Filter by Genre");
        List<String> uniqueGenres = allMovies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        uniqueGenres.add(0, "Alle Genres");
        genreComboBox.getItems().addAll(uniqueGenres);
        genreComboBox.setValue("Alle Genres");

        // Filterevents
        searchBtn.setOnAction(event -> applyFilters());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        genreComboBox.setOnAction(event -> applyFilters());

        // Sortierbutton
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortMovies(true);
                sortBtn.setText("Sort (desc)");
            } else {
                sortMovies(false);
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    //Methode zur Sortierung aufsteigend true oder absteigend false
    public void sortMovies(boolean ascending){
        if(ascending){
            allMovies.sort((m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
        }else{
            allMovies.sort((m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
        }
        observableMovies.setAll(allMovies);
    }

    public void applyFilters() {

        String selectedGenre = genreComboBox.getValue();
        String searchText = Optional.ofNullable(searchField.getText()).orElse("").toLowerCase().trim();
        String releaseYear = Optional.ofNullable(releaseYearInput.getText()).orElse("").trim();
        String ratingFrom = Optional.ofNullable(ratingFromInput.getText()).orElse("").trim();

        try {
            // API-Instanz erstellen und Filteranfrage an die MovieAPI senden
            MovieAPI api = new MovieAPI();
            List<Movie> filteredMovies = api.fetchMoviesWithParams(searchText, selectedGenre, releaseYear, ratingFrom);

            // Speichern in allMovies für spätere Sortierung
            allMovies = filteredMovies;

            // Anzeige im UI aktualisieren
            observableMovies.setAll(filteredMovies);
        } catch (IOException e) {
            // Fehlerausgabe bei fehlgeschlagenem API-Aufruf
            System.err.println("Fehler beim Filtern über API: " + e.getMessage());
        }
    }
}
