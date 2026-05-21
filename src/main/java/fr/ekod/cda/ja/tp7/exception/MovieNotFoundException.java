package fr.ekod.cda.ja.tp7.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Integer id) {
        super("Movie with id " + id + " not found");
    }
}
