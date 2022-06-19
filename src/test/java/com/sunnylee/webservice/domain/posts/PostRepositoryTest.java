package com.sunnylee.webservice.domain.posts;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // 배포 전 전체 테스트 수행 시 테스트간 데이터 침범을 막기 위해 사용
    // 여러 테스트 동시에 수행 시 테스트용 디비에 데이터가 그대로 남아 테스트 실패 가능성
    @After // junit에서 단위테스트가 끝날 때 마다 수행되는 메소드 지정
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title =  "test title";
        String content = "test content";

        // 테이블 posts에 insert, update 쿼리 실행
        // id 값이 있으면 update
        // 없으면 insert
        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author("sunnylee@gmail.com")
                        .build());

        // when
        List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터를 조회해 옴

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
