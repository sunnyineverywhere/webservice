package com.sunnylee.webservice.config.auth.dto;

import com.sunnylee.webservice.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

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
