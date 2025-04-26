package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.logic.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final JFXButton watchlistBtn = new JFXButton("To watchlist");
    private final VBox layout;

    private ClickEventHandler<Movie> onWatchlistClick;

    // Neuer Konstruktor, der einen ClickEventHandler übergeben bekommt
    public MovieCell(ClickEventHandler<Movie> handler, String buttonText) {
        super();
        this.onWatchlistClick = handler;

        watchlistBtn.setStyle(
                "-fx-background-color: #f5c518;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-size: 8px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 6 30 6 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-cursor: hand;"
        );

        // Setze Text mit Icon (optional)
        if (buttonText.toLowerCase().contains("entfernen")) {
            watchlistBtn.setText("Delete");
        } else {
            watchlistBtn.setText("To Watchlist");
        }

        watchlistBtn.setOnAction(e -> {
            if (getItem() != null && this.onWatchlistClick != null) {
                this.onWatchlistClick.onClick(getItem());
            }
        });

        // Layout für Titel und Button in eine Zeile
        HBox topRow = new HBox(title, watchlistBtn);
        topRow.setSpacing(10);

        layout = new VBox(topRow, detail, genre);
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            // Genre als Liste formatieren
            genre.setText("");
            genre.setText(String.join(",", movie.getGenres()).toUpperCase());


            // color scheme
            genre.getStyleClass().add("text-white");
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            genre.fontProperty().set(genre.getFont().font(12));
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
    public Label getTitle() {
        return title;
    }
    public Label getDetail() {
        return detail;
    }
    public Label getGenre() {
        return genre;
    }
}

