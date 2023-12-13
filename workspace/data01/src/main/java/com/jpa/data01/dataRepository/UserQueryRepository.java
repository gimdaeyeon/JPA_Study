package com.jpa.data01.dataRepository;
import com.jpa.data01.domain.dto.UserDto;
import com.jpa.data01.domain.embedded.Address;
import com.jpa.data01.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface UserQueryRepository extends JpaRepository<User,Long> {
    //    모든 메소드는 @Query와 JPQL을 사용한다.

//    전체 회원의 이름 조회
    @Query("select u.name from User u")
    List<String> findUserNames();
//    핸드폰 번호가 010으로 시작하는 회원 조회 escape 모르면 검색해보기
    @Query("select u from User u where u.phone like '010%' escape '/'" )
    List<User> findUserByPhonePattern();
//    주소 상세를 전달 받아 일치하는 회원 조회 (예 : 101호)
    @Query("select u from User u where u.address.addressDetail = :addressDetail")
    List<User> findUserByAddressDetail(@Param("addressDetail") String addressDetail);
//    이름이 특정 글자로 시작하는 회원 조회(성으로 회원 조회)
    @Query("select u from User u where u.name like concat(:firstName,'%') escape '/'")
    List<User> findUserByFirstName(@Param("firstName")String firstName);
//    이름을 여러 개 전달 받아 해당 일치하는 회원 조회
    @Query("select u from User u where u.name in :userNames")
    List<User> findUserByNames(@Param("userNames") Collection<String> userNames);
//    전체 회원의 주소, 주소 상세, 우편번호 조회
    @Query("select u.address from User u")
    List<Address> findUsersAddress();

//    id, name, birth 필드를 가진 UserDto를 만든다. -> 우편번호를 전달받아 부분 일치하는 회원 정보를 Dto로 반환 (예 : 1 -> 11111, 41233, 44412)
    @Query("select new com.jpa.data01.domain.dto.UserDto(u.id, u.name,u.birth) from User u where u.address.zipcode like concat('%',:zipcode,'%') escape '/'")
    List<UserDto> findUserDtoByZipcode(@Param("zipcode") String zipcode);
//    회원의 각 주소별 나이가 가장 많은 사람의 주소와 생일을 Map으로 반환
    @Query("select new Map(u.address.address as address,min(u.birth) as birth) from User u group by u.address.address")
    List<Map<String, Object>> findUserMapByAddressGroupBY();
//    나이가 10살 이상인 회원 조회
    @Query("select u from User u where year(current_timestamp())-year(u.birth) >=10")
    List<User> findUserByUserAge();
//  각 월별 생일인 사람들의 수
    @Query("select new Map(month(u.birth) as month, count(u.id) as count)  from User u group by month(u.birth)")
    List<Map<String,Object>> findMonthlyUserCont();



}




