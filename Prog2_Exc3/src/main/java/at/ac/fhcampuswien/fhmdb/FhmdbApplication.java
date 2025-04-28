package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.data.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.data.MovieEntity;
import at.ac.fhcampuswien.fhmdb.data.MovieRepository;
import at.ac.fhcampuswien.fhmdb.exceptionHandling.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptionHandling.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();

        try {
            MovieAPI api = new MovieAPI();
            List<Movie> movies = api.fetchMovies();

            MovieRepository movieRepo = new MovieRepository(DatabaseManager.getDatabaseManagerInstance().getMovieDao());
            for (Movie movie : movies) {
                MovieEntity entity = new MovieEntity();
                entity.setApiId(movie.getapiId());
                entity.setTitle(movie.getTitle());
                entity.setGenres(String.join(",", movie.getGenres()));
                entity.setDescription(movie.getDescription());
                entity.setReleaseYear(movie.getReleaseYear());
                entity.setRating(movie.getRating());

                movieRepo.addMovie(entity);
            }
        }catch (MovieApiException | DatabaseException e){
            System.err.println("Fehler beim Cachen der Filme:" + e.getMessage());
        }
        /*try {
            DatabaseManager.getDatabaseManagerInstance().testDB();

        } catch (DatabaseException | SQLException e) {
            System.err.println("Fehler beim erstellen der Tabelle(glaub ich)" + e.getMessage());
        }*/
    }

    public static void main(String[] args) {
        launch();
    }
}
