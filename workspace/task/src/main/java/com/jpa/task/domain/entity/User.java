package com.jpa.task.domain.entity;

import com.jpa.task.domain.base.Period;
import com.jpa.task.domain.embedded.Address;
import com.jpa.task.domain.enumType.UserGender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "JPA_USER")
@Getter @Setter @ToString
@NoArgsConstructor
@SequenceGenerator(
        name = "SEQ_USER_GENERATOR",
        sequenceName = "SEQ_JPA_USER"
)
public class User extends Period {
    @Id
    @GeneratedValue(generator = "SEQ_USER_GENERATOR")
    private Long id;
    @Column(unique = true)
    private String loginId;
    private String password;
    private String name;

    @Embedded
    @AttributeOverrides({//not null 제약조건을 걸기 위해 사용했다. (또는 Address 타입의 필드에 직접 설정)
            @AttributeOverride(name = "address", column = @Column(nullable = false)),
            @AttributeOverride(name = "addressDetail", column = @Column(nullable = false)),
            @AttributeOverride(name = "zipcode", column = @Column(nullable = false))
    })
    private Address address;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'") // 컬럼에 디폴트 값을 설정한다.
    private UserGender userGender;

}





















