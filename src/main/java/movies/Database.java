package movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static input.InputUtils.stringInput;

public class Database {

    private String databasePath;

    Database(String databasePath) {      // constructor

        // check if table exists, create if not

        this.databasePath = databasePath;

        try (Connection connection = DriverManager.getConnection(databasePath);
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS movies(name text,star " +
                    "integer, watched boolean)");
        } catch (SQLException e) {
            System.out.println("Error creating movie DB table because " + e.getMessage());
        }

    }

    public void addNewMovie(Movie movie) {

        String insertSQL = "INSERT INTO movies VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(databasePath);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(insertSQL)) {


            PreparedStatement psInsert = connection.prepareStatement(insertSQL);
            psInsert.setString(1, movie.getName());
            psInsert.setInt(2, movie.getStars());
            psInsert.setBoolean(3, movie.isWatched());
            psInsert.execute();



    } catch (SQLException e) {
            System.out.println("Error adding movie to DB table because " + e);
        }
}

    public List<Movie> getAllMovies() {
        try (Connection connection = DriverManager.getConnection(databasePath);
        Statement statement = connection.createStatement();) {

            ResultSet movieResults = statement.executeQuery("SELECT * FROM movies ORDER" +
                    " BY name");
            List<Movie> movies = new ArrayList<>();
            while (movieResults.next()) {
                movies.add(new Movie(
                        movieResults.getString("name"),
                        movieResults.getInt("star"),
                        movieResults.getBoolean("watched")
                ));
            }
            return movies;}

        catch (SQLException e){

    System.out.println("Error fetching all movies from database because " +e);
    return null;
        }
    }


    public List<Movie> getAllMoviesByWatched(boolean watchedStatus) {


        try (Connection connection = DriverManager.getConnection(databasePath);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT *" +
                    " FROM movies Where watched = ? ORDER BY name");
             ) {
            preparedStatement.setBoolean(1, watchedStatus);
            ResultSet movieResults = preparedStatement.executeQuery();

            List<Movie> movies = new ArrayList<>();

            while (movieResults.next()) {
                String name = movieResults.getString("name");
                int stars = movieResults.getInt("star");
                boolean watched = movieResults.getBoolean("watched");
                Movie movie = new Movie(name, stars, watched);
                movies.add(movie);
            }
            return movies;}

        catch (SQLException e) {
            System.out.println("Error getting movies because " + e);
            return null;
        }
    }

    public void updateMovie(Movie movie){

        String sql = "UPDATE movies SET star = ? , watched = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(databasePath);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, movie.getStars());
            preparedStatement.setBoolean(2, movie.isWatched());
            preparedStatement.setString(3, movie.getName());
            preparedStatement.execute();

    } catch (SQLException e) {
            System.out.println("Error updating " + movie.getName() + " movie because " + e);
        }
    }

    public void deleteMovie(Movie movie){
        String sql = "DELETE FROM movies WHERE name =?";
        try (Connection connection = DriverManager.getConnection(databasePath);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, movie.getName());
            preparedStatement.execute();


    } catch (SQLException e) {
            System.out.println("Error deleting " + movie + " because " + e);
        }
    }


    public List<Movie> searchMovie(String search){

    List<Movie> movies = new ArrayList<>();

    try (Connection connnection = DriverManager.getConnection(databasePath);
    PreparedStatement preparedStatement = connnection.prepareStatement("SELECT * FROM " +
            "movies WHERE name like ? Order by name")) {
        preparedStatement.setString(1, "%" + search + "%");

        ResultSet movieResults = preparedStatement.executeQuery();

        while (movieResults.next()) {
            String name = movieResults.getString("name");
            int stars = movieResults.getInt("star");
            boolean watched = movieResults.getBoolean("watched");
            Movie movie = new Movie(name, stars, watched);

            movies.add(movie);}
        return movies;

        } catch (SQLException e) {
        System.out.println("Error searching because " + e);
        return null; }


    }
}