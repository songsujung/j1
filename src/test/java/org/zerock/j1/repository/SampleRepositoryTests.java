package org.zerock.j1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Sample;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleRepositoryTests {

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    public void test1(){
        log.info(sampleRepository.getClass().getName());
    }

    @Test
    public void TestInsert(){
        
        // 100개의 데이터 생성
        IntStream.rangeClosed(0, 100).forEach(i -> {
            Sample obj = Sample.builder()
            .keyCol("u" + i)
            .first("first")
            .last("last")
            .build();

            sampleRepository.save(obj); // 만든 obj를 저장
        });
    }

    @Test
    public void testRead(){

        String keyCol = "u10";

        // Optional : 검색 결과가 있을 경우 값을 갖고, 없을 경우 비움
		// 반환타입이 Null 일수도 있어서 Optional을 사용
        Optional<Sample> result = sampleRepository.findById(keyCol);

        Sample obj = result.orElseThrow(); // 만약 없다면 예외를 만들어서 던져버리겠다.

        log.info(obj);
    }

    @Test
    public void testDelete(){

        String keyCol = "u100";

        sampleRepository.deleteById(keyCol); // repository의 deletebyid의 값을 keycol로 받아서 삭제


    }

    @Test
    public void Paging(){

        Pageable pageable = PageRequest.of(0, 10, 
        Sort.by("KeyCol").descending()); // 역순 정렬

        Page<Sample> result = sampleRepository.findAll(pageable);

        log.info(result.getTotalElements()); // 데이터 총 갯 수

        log.info(result.getTotalPages()); // 총 페이지 수

        // 한 페이지의 데이터 출력
        result.getContent().forEach(obj -> {
            log.info(obj);
        });
    }


    
}
