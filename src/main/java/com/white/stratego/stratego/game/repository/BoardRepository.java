package com.white.stratego.stratego.game.repository;

import com.white.stratego.stratego.game.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
