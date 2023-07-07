package org.zerock.j1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    private String type, keyword;

    public PageRequestDTO(){

    }

    public PageRequestDTO(int page, int size){

    }

    public PageRequestDTO(int page, int size, String type, String keyword){
        this.page  = page <= 0 ? 1 : page;
        this.size = size < 0 || size >= 100 ? 10 : size;
        this.type = type;
        this.keyword = keyword;
    }
}
