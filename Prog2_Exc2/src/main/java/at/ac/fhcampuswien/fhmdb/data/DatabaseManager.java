    package at.ac.fhcampuswien.fhmdb.data;

    import at.ac.fhcampuswien.fhmdb.exceptionHandling.DatabaseException;
    import com.j256.ormlite.dao.Dao;
    import com.j256.ormlite.dao.DaoManager;
    import com.j256.ormlite.jdbc.JdbcConnectionSource;
    import com.j256.ormlite.support.ConnectionSource;
    import com.j256.ormlite.table.DatabaseTable;
    import com.j256.ormlite.table.TableUtils;
    import javafx.scene.control.Tab;

    import java.sql.SQLException;

    public class DatabaseManager {
        private static final String DB_URL = "jdbc:h2:file:./db/moviesdb;AUTO_SERVER=TRUE";
        public static final String username = "hallo";
        public static final String password = "bye";
        private static ConnectionSource conn;
        private Dao<MovieEntity, Long> movieDao;
        private Dao<WatchlistMovieEntity, Long> watchlistDao;

        private static DatabaseManager instance; //Instanz für die Singleton

        private DatabaseManager() { //Singleton/Constructor
            try {
            createConnectionSource();
                TableUtils.createTableIfNotExists(conn, MovieEntity.class);
                TableUtils.createTableIfNotExists(conn, WatchlistMovieEntity.class);
                movieDao = DaoManager.createDao(conn, MovieEntity.class);
                watchlistDao = DaoManager.createDao(conn, WatchlistMovieEntity.class);
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public void testdb1() throws SQLException {
            MovieEntity movie = new MovieEntity("Shrek");
            movieDao.create(movie);
        }

        public static DatabaseManager getDatabaseManagerInstance() { //Singleton-getter
            if (instance == null) {
                instance = new DatabaseManager();
            }
            return instance;
        }


        private static void createConnectionSource() throws SQLException {
            conn = new JdbcConnectionSource(DB_URL, username, password);
        }

        public ConnectionSource getConnectionSource() {
            return conn;
        }

        public void createTables() throws SQLException {
            TableUtils.createTable(conn, MovieEntity.class);
            TableUtils.createTable(conn, WatchlistMovieEntity.class);
        }

        public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
            return watchlistDao;
        }

        public Dao<MovieEntity, Long> getMovieDao() {
            return movieDao;
        }


    }
