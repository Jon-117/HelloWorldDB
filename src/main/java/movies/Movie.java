package movies;

public class Movie {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    private String name;
    int stars;
    boolean watched;

    Movie(String name, int stars, boolean watched) {
        this.name = name;
        this.stars = stars;
        this.watched = watched;

    }

    @Override
    public String toString() {
        return String.format("Movie - %s, %d, %b", name, stars, watched);
}}
