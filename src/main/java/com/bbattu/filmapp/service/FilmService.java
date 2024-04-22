package com.bbattu.filmapp.service;

import com.bbattu.filmapp.model.Film;
import com.bbattu.filmapp.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    @Autowired
    FilmRepository filmRepository;
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(String id) {
        return filmRepository.findById(id);
    }
    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    /**
     *
     * @param id
     * @param updatedFilm
     * @return Film | null
     */
    public Film updateFilm(String id, Film updatedFilm) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            film.setName(updatedFilm.getName());
            film.setDescription(updatedFilm.getDescription());
            film.setDate(updatedFilm.getDate());
            film.setNote(updatedFilm.getNote());

            return filmRepository.save(film);
        } else {
            return null;
        }
    }

    public String deleteFilm(String id) {
        filmRepository.deleteById(id);
        return id;
    }
}
