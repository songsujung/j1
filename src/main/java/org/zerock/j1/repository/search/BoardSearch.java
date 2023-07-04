package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.j1.domain.Board;

import lombok.extern.log4j.Log4j2;

public interface BoardSearch {

    Page<Board> search1(String searchType, String keyword, Pageable pageable);
    
}
