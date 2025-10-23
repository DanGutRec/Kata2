package software.ulpgc.proyect.application;

import software.ulpgc.proyect.model.Movie;

import java.util.List;

public class Main {
    static void main(String[] args) {
        List<Movie> movies = new RemoteMovieLoader(Desarilazer::fromTsv).loadMovies();
        System.out.println(movies.size());
    }
}
