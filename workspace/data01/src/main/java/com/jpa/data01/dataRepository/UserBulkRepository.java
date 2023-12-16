package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBulkRepository extends JpaRepository<User,Long> {
//    주소가 강남구인 회원의 이름을 기존이름 + '/강남' 으로 수정하기 ->김철수/강남
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name = concat(u.name, '/강남') ,u.modifiedDate = current_timestamp where u.address.address = '강남구'")
    void userModify1();

//    주소가 강남구인 회원을 제외한 나머지 모든 회원의 이름을 기존이름 + '/주소'로 변경하기
//    예) 김철수/강북구,홍길동/강서구 등등
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name = concat(u.name,'/',u.address.address),u.modifiedDate = current_timestamp where u.address.address !=  '강남구'")
    void userModify2();

}
