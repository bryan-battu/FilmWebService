package com.bbattu.filmapp.controller;

import com.bbattu.filmapp.model.Film;
import com.bbattu.filmapp.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController {

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleException(HttpMessageNotReadableException ex) {
        //Handle Exception Here...
    }

    @Autowired
    FilmService filmService;

    /**
     * Récupérer tous les films
     * @return
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAllFilms();
        log.info("TEST");
        return ResponseEntity.ok(films);
    }


    /**
     * Récupérer un film grâce à son id
     * @param id
     * @return Film
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Film> getFilmById(@PathVariable String id) {
        Optional<Film> film = filmService.getFilmById(id);
        return film.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Créer un film
     * @return
     */
    @PostMapping()
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        //Check for valid name
        if (film.getName() == null || film.getName().length() > 128){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //Check for valid description
        if(film.getDescription() == null || film.getDescription().length() > 2048){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //Check for valid release date
//        try {
//            LocalDateTime.parse(film.getDate());
//        } catch (DateTimeParseException e){
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
//        }
        //Check for valid rating (if provided)
        Integer rating = film.getNote();
        if(rating == null || rating < 0 || rating > 5) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Film filmCreated = filmService.createFilm(film);

        return new ResponseEntity<>(filmCreated, HttpStatus.CREATED);
    }

    /**
     * Mettre à jour un film
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable String id, @RequestBody Film film) {
        //Check for valid name
        if (film.getName() == null || film.getName().length() > 128){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //Check for valid description
        if(film.getDescription() == null || film.getDescription().length() > 2048){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //Check for valid release date
//        try {
//            LocalDateTime.parse(film.getDate());
//        } catch (DateTimeParseException e){
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
//        }
        //Check for valid rating (if provided)
        Integer rating = film.getNote();
        if(rating == null || rating < 0 || rating > 5) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Film updatedFilm = filmService.updateFilm(id, film);
        if (updatedFilm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(updatedFilm);
        }
    }

    /**
     * Supprimer un film
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable String id){
        if (id == null || id.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(filmService.deleteFilm(id));
    }
}


