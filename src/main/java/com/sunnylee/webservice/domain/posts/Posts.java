package com.sunnylee.webservice.domain.posts;

import com.sunnylee.webservice.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// 롬복은 코드 단순화 -> 필수 X
// 주요 @를 클래스에 가깝게 두기
// 새 언어로 전환(코틀린)하면 롬복이 필요 없을 때 삭제가 쉬움
@Getter
@NoArgsConstructor // lombok @, 기본 생성자 자동 추가
@Entity // JPA @ // 절대 Setter 메소드를 만들지 않음
public class Posts extends BaseTimeEntity { // 실제 DB 테이블과 매칭

    @Id // pk field
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성규칙, auto_increment
    private Long id;


    // @Column은 필수 @는 아님 => 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨
    // 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용(length, columnDefinition 등)
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 ㅍ클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌드에 포함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
