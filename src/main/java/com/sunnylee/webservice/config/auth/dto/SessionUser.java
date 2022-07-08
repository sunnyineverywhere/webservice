package com.sunnylee.webservice.config.auth.dto;

import com.sunnylee.webservice.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;


// 인증된 사용자 정보만 필요
// 따라서 name, email, picture만 필드로 선언함
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    @Builder
    public SessionUser(User user) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
