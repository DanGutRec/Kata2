package software.ulpgc.proyect.application;

import software.ulpgc.proyect.model.Movie;
import software.ulpgc.proyect.io.MovieLoader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public class RemoteMovieLoader implements MovieLoader {
    private final Function<String, Movie> desarilazer;

    public RemoteMovieLoader(Function<String, Movie> desarilazer) {
        this.desarilazer = desarilazer;
    }

    @Override
    public List<Movie> loadMovies() {
        try {
            return loadFrom(new URL("https://datasets.imdbws.com/title.basics.tsv.gz"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(URL url) throws IOException {
        return loadFrom(url.openConnection());
    }

    private List<Movie> loadFrom(URLConnection urlConnection) {
        try (InputStream is= unzip(urlConnection.getInputStream())) {
            return loadFrom(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(InputStream is) throws IOException {
        return loadFrom(toReader(is));
    }

    private List<Movie> loadFrom(BufferedReader reader) throws IOException {
        reader.readLine();
        List<Movie> list=new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {break;}
            list.add(desarilazer.apply(line));
        }
        return list;
    }

    private BufferedReader toReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }

    private InputStream unzip(InputStream inputStream) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(inputStream));
    }
}
