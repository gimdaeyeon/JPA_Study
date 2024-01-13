package com.jpa.finalapp.domain.type.member;


public enum MemberGender {
    NONE("선택 안함"), MALE("남자"), FEMALE("여자");
//    enum의 상수들은 전부 자신의 인스턴스를 public static final로 만든것이다.
//    즉, NONE("선택 안함") 은
//    -> public static final MemberGender NONE = new MemberGender("선택안함")인 것이다.
    private String gender;

    MemberGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
