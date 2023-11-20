package com.jpa.basic3.domain.base;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// 테이블과 매핑하지 않고 단순히 필드를 상속하기 위한 용도의 클래스르 의민한다.
// 즉, 자바에서는 상속관계이지만 RDB의 상속 구현과는 무관한 클래스임을 표시한다.
@MappedSuperclass @Getter
public abstract class Period {
    private LocalDateTime createdDate; // 최초로 insert된 시간 정보
    private LocalDateTime modifiedDate; // 마지막으로 수정된 시간 정보

    @PrePersist //entityManager의 persist() 직전에 실행되는 메소드로 지정한다.(하이버네이트가 지원)
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now();
    }
}
