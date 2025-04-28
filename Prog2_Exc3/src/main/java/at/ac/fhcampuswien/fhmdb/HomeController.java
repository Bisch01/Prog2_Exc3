package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.data.MovieEntity;
import at.ac.fhcampuswien.fhmdb.data.MovieRepository;
import at.ac.fhcampuswien.fhmdb.exceptionHandling.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptionHandling.MovieApiException;
import at.ac.fhcampuswien.fhmdb.logic.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.data.DatabaseManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.h2.engine.Database;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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

    @FXML
    public JFXButton showWatchlistBtn;

    @FXML
    public JFXButton showAllMoviesBtn;

    private WatchlistRepository watchlistRepository;

    public List<Movie> allMovies = new ArrayList<>();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private ClickEventHandler<Movie> onAddToWatchlistClicked;

    private MovieRepository movieRepository;
    private MovieAPI movieAPI;

    public HomeController(MovieAPI api, MovieRepository movieRepo, WatchlistRepository watchlistRepo) {
        this.movieAPI = api;
        this.movieRepository = movieRepo;
        this.watchlistRepository = watchlistRepo;
    }

    public HomeController() {
        this.movieAPI = new MovieAPI();
        try {
            DatabaseManager dbManager = DatabaseManager.getDatabaseManagerInstance();
            this.movieRepository = new MovieRepository(dbManager.getMovieDao());
            this.watchlistRepository = new WatchlistRepository(dbManager.getWatchlistDao());
        } catch (DatabaseException e) {
            showAlert("Datenbank-Fehler", "Fehler beim Initialisieren der Datenbank: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        loadMovies();


        try {
            watchlistRepository = new WatchlistRepository(DatabaseManager.getDatabaseManagerInstance().getWatchlistDao());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        onAddToWatchlistClicked = (movie) -> {
            try {
                boolean added = watchlistRepository.addToWatchlist(movie.getapiId());
                if (added) {
                    showAlert("Erfolg", "Film wurde zur Watchlist hinzugefügt.");
                } else {
                    showAlert("Info", "Der Film ist bereits auf deiner Watchlist.");
                }
            } catch (DatabaseException e) {
                showAlert("Fehler", "Der Film konnte nicht zur Watchlist hinzugefügt werden: " + e.getMessage());
            }
        };

        movieListView.setItems(observableMovies);

        //Für normale Ansicht
        movieListView.setCellFactory(list -> new MovieCell(onAddToWatchlistClicked, "To Watchlist"));


        initializeGenreFilter();

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

        showWatchlistBtn.setOnAction(event -> showWatchlist());
        showAllMoviesBtn.setOnAction(event -> showAllMovies());

    }

    private void initializeGenreFilter() {
        // Genre-Filter initialisieren
        genreComboBox.setPromptText("Filter by Genre");
        List<String> uniqueGenres = allMovies.stream().flatMap(movie -> movie.getGenres().stream()).distinct().sorted().collect(Collectors.toList());

        uniqueGenres.add(0, "Alle Genres");
        genreComboBox.getItems().addAll(uniqueGenres);
        genreComboBox.setValue("Alle Genres");
    }

    public void loadMovies() {
        try{
            allMovies = loadMoviesFromAPI();
            observableMovies.setAll(allMovies);
        } catch (MovieApiException e){
            try {
                showAlert("API nicht erreichbar",
                        "Filme werden aus dem Cache geladen.");
                allMovies=loadMoviesFromCache();
                observableMovies.setAll(allMovies);
            }catch (DatabaseException dbEx){
                showAlert("Fehler",
                        "Filme konnten weder von der API noch aus der Datenbank gelesen werden.");
                allMovies = new ArrayList<>();
            }
        }
    }

    public List<Movie> loadMoviesFromAPI() throws MovieApiException {
        try {
            return movieAPI.fetchMovies();
        } catch (MovieApiException e) {
            throw new MovieApiException("Fehler beim Laden der Filme von der API", e);
        }

    }

    public List<Movie> loadMoviesFromCache() throws DatabaseException {
        List<MovieEntity> entities = movieRepository.getAllMovies();
        List<Movie> movies = new ArrayList<>();

        for (MovieEntity entity : entities) {
            Movie movie = convertEntityToMovie(entity);
            movies.add(movie);
        }
        return movies;
    }

    private Movie convertEntityToMovie(MovieEntity entity) {
        Movie movie = new Movie();

        movie.setTitle(entity.getTitle());
        movie.setDescription(entity.getDescription());
        movie.setApiId(entity.getApiId());
        movie.setReleaseYear(entity.getReleaseYear());
        movie.setRating(entity.getRating());

        if (entity.getGenres() != null && !entity.getGenres().isEmpty()) {
            String[] genreArray = entity.getGenres().split(",");
            List<String> genres = Arrays.asList(genreArray);
            movie.setGenres(genres);
        } else {
            movie.setGenres(new ArrayList<>());
        }
        return movie;
    }


    //Methode zur Sortierung aufsteigend true oder absteigend false
    public void sortMovies(boolean ascending) {
        if (ascending) {
            allMovies.sort((m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
        } else {
            allMovies.sort((m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
        }
        observableMovies.setAll(allMovies);
    }
    public void applyFilters() {

        String selectedGenre = genreComboBox.getValue();
        String searchText = Optional.ofNullable(searchField.getText()).orElse("").toLowerCase().trim();
        String releaseYear = Optional.ofNullable(releaseYearInput.getText()).orElse("").trim();
        String ratingFrom = Optional.ofNullable(ratingFromInput.getText()).orElse("").trim();

        if (validateInputs(releaseYear, ratingFrom)){
            return;
        }

        loadMoviesWithFilters(searchText, selectedGenre, releaseYear, ratingFrom);
    }

    private boolean validateInputs(String releaseYear, String ratingFrom) {
        if(!releaseYear.isEmpty() && !releaseYear.matches("\\d+")) {
            showAlert("Ungültige Eingabe","Bitte gib für das Jahr nur Zahlen ein.");
            return false;
        }

        if (!ratingFrom.isEmpty()){
            try {
                double rating = Double.parseDouble(ratingFrom);
                if (rating < 0 || rating > 10){
                    showAlert("Ungültige Eingabe","Die Bewertung muss zwischen 0 und 10 liegen.");
                    return false;
                }
            }catch (NumberFormatException e){
                showAlert("Ungültige Eingabe","Bitte gib für die Bewertung eine gültige Zahl ein.");
                return false;
            }
        }
        return true;
    }

    private void loadMoviesWithFilters(String searchText, String selectedGenre, String releaseYear, String ratingFrom) {
        try {
            List<Movie> filteredMovies = movieAPI.fetchMoviesWithParams(searchText, selectedGenre, releaseYear, ratingFrom);

            if(filteredMovies.isEmpty()){
                showAlert("Keine Ergebnisse","Es wurden keine Filme gefunden, die deinen Filtern entsprechen.");
            }
            allMovies = filteredMovies;
            observableMovies.setAll(allMovies);
        } catch (MovieApiException e) {
            // Fehlerausgabe bei fehlgeschlagenem API-Aufruf

            System.err.println("API-Fehler" + e.getMessage());
        }
    }

    ClickEventHandler<Movie> onRemoveFromWatchlistClicked = (movie) -> {
        try {
            watchlistRepository.removeFromWatchlist(movie.getapiId());
            showAlert("Erfolg", "Film aus Watchlist entfernt.");
            showWatchlist(); // Direkt aktualisieren!
        } catch (DatabaseException e) {
            showAlert("Fehler", "Film konnte nicht entfernt werden: " + e.getMessage());
        }
    };


    public void showWatchlist() {
        try {
            List<String> watchlistIds = watchlistRepository.getAllWatchlistEntries().stream().map(entry -> entry.getApiId()).collect(Collectors.toList());

            List<Movie> watchlistMovies = allMovies.stream().filter(movie -> watchlistIds.contains(movie.getapiId())).collect(Collectors.toList());

            observableMovies.setAll(watchlistMovies);

            // Setze CellFactory mit Entfernen-Button
            movieListView.setCellFactory(list -> new MovieCell(onRemoveFromWatchlistClicked, "Entfernen"));
        } catch (DatabaseException e) {
            showAlert("Fehler", "Watchlist konnte nicht geladen werden: " + e.getMessage());
        }
    }

    public void showAllMovies() {
        observableMovies.setAll(allMovies);
        movieListView.setCellFactory(list -> new MovieCell(onAddToWatchlistClicked, "To Watchlist"));
    }

}
