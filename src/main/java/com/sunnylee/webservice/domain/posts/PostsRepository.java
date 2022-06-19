package com.sunnylee.webservice.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// DB Layer 접근자
// JPA에서는 레포지토리라고 불림. JDBC 드라이버 사용할 때 dao 생각하면 됨
// 기본적인 CRUD 메소드가 자동으로 생성된다
// Entity 클래스와 기본 Entity Repository는 함께 위치해야 함
// 도메인 패키지에서 함께 관리
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
