package com.jpa.finalapp.domain.dto.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter @ToString
public class PageBlock<T> {
    private int blockSize; // 페이지 한 블록당 크기
    private int currentPage; // 현재 페이지
    private int startPage; // 블록의 시작 번호
    private int endPage; // 블록의 마지막 번호
    private int lastPage; // 전체 페이지 중 마지막 번호

    public PageBlock(int blockSize, Pageable pageable, Page<T> page) {
        this.blockSize = blockSize;
        this.currentPage = pageable.getPageNumber() +1;

        this.endPage = (int)(Math.ceil(currentPage/(double)blockSize))*blockSize;
        this.startPage = endPage-blockSize +1;
        this.lastPage = page.getTotalPages() == 0 ?1:page.getTotalPages();

        this.endPage = Math.min(endPage,lastPage);
    }

    public boolean hasNextBlock(){
        return endPage<lastPage;
    }

    public boolean hasPrevBlock(){
        return startPage>1;
    }



}
















