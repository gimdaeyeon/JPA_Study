package com.jpa.securityex.domain.type;

public enum Role {
    ADMIN,MEMBER;

    private static final String PREFIX = "ROLE_";

    public String getSecurityRole(){
//        스프링 시큐리티에서 role을 사용하는 경우 MEMBER라고 사용하면 인식하지 못하며
//        ROLE_MEMBER라고 사용해야 인식한다.
        return PREFIX+name();
    }


}
