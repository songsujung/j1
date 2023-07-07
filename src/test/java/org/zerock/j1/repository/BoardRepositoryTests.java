package org.zerock.j1.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.j1.domain.Board;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.BoardReadDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {

        for(int i = 0; i < 1; i++){
            Board board = Board.builder()
            .title("Sample Title"+i)
            .content("Sample Content"+i)
            .writer("Sample user"+(i%10))
            .build();

        boardRepository.save(board);
        }


    }

    @Test
    public void testRead() {

        Long bno = 1L;

        // NUll 값이 나올수 있기 때문에 Optional을 사용
        Optional<Board> result = boardRepository.findById(bno);

        log.info("--------------");

        // 예외나면 던짐
        Board board = result.orElseThrow();

        log.info(result.get());

    }

    @Test
    public void testUpdate() {

        // 먼저 조회한 후, 수정한 뒤 저장한다.

        Long bno = 1L;

        // NUll 값이 나올수 있기 때문에 Optional을 사용
        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        // 조회한 값을 이용해 수정하기

        board.changeTitle("Update Title");

        boardRepository.save(board);

    }

    @Test
    public void testQuery1() {
        
        List<Board> list = boardRepository.findByTitleContaining("1");

        log.info("----------------------");
        log.info(list.size());
        log.info(list);
    }

    @Test
    public void testQuery1_1() {
        
        List<Board> list = boardRepository.listTitle("1");

        log.info("----------------------");
        log.info(list.size());
        log.info(list);
    }

    @Test
    public void testQuery1_2() {
        
        List<Object[]> list = boardRepository.listTitle2("1");

        log.info("----------------------");
        log.info(list.size());
        
        list.forEach(arr -> log.info(Arrays.toString(arr)));
    }

        @Test
    public void testQuery1_3() {

        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("bno").descending());
        
        Page<Object[]> result = boardRepository.listTitle2("1", pageable);

        log.info(result);

    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10,
        Sort.by("bno").descending());
        
        Page<Board> result = boardRepository.findByContentContaining("1", pageable);

        log.info("----------------------");
        log.info(result);
    }

    @Commit
    @Transactional
    @Test
    public void testModify() {

        Long bno = 100L;
        String title = "Modified Title 100";

        int count = boardRepository.modifyTitle(title, bno);

        log.info("------------------" + count);
    }

    @Test
    public void testNative() {
        
        List<Object[]> result = boardRepository.listNative();

        result.forEach(arr -> log.info(Arrays.toString(arr)));
        
    }

    @Test
    public void testSearch1() {

        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("bno").descending());

        Page<Board> result = boardRepository.search1("tcw","1",pageable);

        log.info(result.getTotalElements());

        result.get().forEach(b -> log.info(b));
    }
    
    @Test
    public void testListWithRcnt() {

        List<Object[]> result = boardRepository.getListWithRcnt();

        for (Object[] result2 : result) {
            log.info(Arrays.toString(result2));
        }

    }

    @Test
    public void testListWithRcntSearch() {

        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("bno").descending());

        boardRepository.searchWithRcnt("tcw", "1", pageable);
    }

    @Test
    public void test0706_1() {

        PageRequestDTO pageRequest = new PageRequestDTO();

        PageResponseDTO<BoardListRcntDTO> responseDTO = boardRepository.searchDTORcnt(pageRequest);

        log.info(responseDTO);
    }

    @Test
    public void testReadOne() {
        Long bno = 77L;

        BoardReadDTO dto = boardRepository.readOne(bno);

        log.info(dto);
        log.info(dto.getRegDate());
        log.info(dto.getModDate());
        log.info(dto.getClass().getName());
    }
}
