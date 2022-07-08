package com.sunnylee.webservice.config.auth;

import com.sunnylee.webservice.config.auth.dto.OAuthAttributes;
import com.sunnylee.webservice.config.auth.dto.SessionUser;
import com.sunnylee.webservice.domain.user.User;
import com.sunnylee.webservice.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분. 네이버 로그인 연동 시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 로그인 진행 시 키가 되는 필드값. primary key와 같은 의미.
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버와 카카오는 지원하지 않음.
        // 구글의 기본 코드는 sub
        // 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


        // OAuth2User의 어트리뷰트를 담을 클래스. 다른 소셜 로그인도 동일한 클래스 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // sessionuser는 세션에 사용자 정보를 저장하기 위한 dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new
                        SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey()
        );
    }

    public User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
