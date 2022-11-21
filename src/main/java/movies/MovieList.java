package movies;

import java.sql.SQLOutput;
import java.util.List;

import static input.InputUtils.*;

public class MovieList {

    private static final String DB_PATH = "jdbc:sqlite:movie_watchlist.sqlite";
    private static Database database;

    public static void main(String[] args) {
        database = new Database(DB_PATH);
//        addNewMovies();
//        checkIfWatchedAndRate();
//        deleteMovie();
        searchMovie();
//        displayMovieList();
}

    public static void addNewMovies() {
        do {
            String movieName = stringInput("Enter movie name");
            boolean movieWatched = yesNoInput("Watched this movie?");
            int movieStars = 0;
            if (movieWatched){
                movieStars = positiveIntInput("Star rating out of 5?");
                // ToDo - add validation of movie stars ( between 0 and 5 )
            }
            Movie movie = new Movie(movieName, movieStars, movieWatched);
            database.addNewMovie(movie);

        } while (yesNoInput("Add movie to watchlist"));
    }
    public static void displayMovieList() {
        List<Movie> movies = database.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("Error - No movies found");
        }
        else {
            for (Movie movie : movies) {
                System.out.println(movie.toString());
            }

        }
    }

    public static void checkIfWatchedAndRate(){
        List<Movie> unwatchedMovies = database.getAllMoviesByWatched(false);

        for (Movie movie : unwatchedMovies){
            boolean hasWatched = yesNoInput("Have you watched " + movie.getName()
                    + " yet?");
            if (hasWatched == true) {
                int stars = positiveIntInput("How do you rate " + movie.getName() + " out " +
                        "of 5 stars?");
                movie.setStars(stars);
                database.updateMovie(movie);

            }
        }

    }

    public static void deleteMovie() {
        System.out.println("Here's all the movies you've seen: ");
        List<Movie> movies = database.getAllMovies();
        for (Movie movie : movies) {
            boolean delete = yesNoInput("Are you sure you want to delete " + movie + "?");
            if (delete) database.deleteMovie(movie);

    }
}
// ToDo - Fix search functionality. Currently returns all movies in the database.
//  Should only return searched movie.
    public static List<Movie> searchMovie() {
        String movieTitle = stringInput("Enter search text");
        List<Movie> movies = database.searchMovie(movieTitle);

        if (movies.isEmpty()) {
            System.out.println("Error - No movie found");
        } else {
            for (Movie movie : movies) {
                System.out.println(movie.toString());
            }
        }
        return movies;

    }}
