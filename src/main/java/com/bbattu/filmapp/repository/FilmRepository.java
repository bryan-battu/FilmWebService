package com.bbattu.filmapp.repository;

import com.bbattu.filmapp.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, String> {
}
