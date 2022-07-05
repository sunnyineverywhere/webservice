package com.sunnylee.webservice.service.posts;

import com.sunnylee.webservice.domain.posts.Posts;
import com.sunnylee.webservice.domain.posts.PostsRepository;
import com.sunnylee.webservice.web.dto.PostListResponseDto;
import com.sunnylee.webservice.web.dto.PostsResponseDto;
import com.sunnylee.webservice.web.dto.PostsSaveRequestDto;
import com.sunnylee.webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public PostListResponseDto buildPostListResponseDto(Posts posts){
        return new PostListResponseDto(posts);
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id " + id));

        return new PostsResponseDto(entity);
    }


    // *
    @Transactional(readOnly = true) // 트랜잭션 범위 유지 + 조회 기능만 남겨두어 속도 개선
    public List<PostListResponseDto> findAllDesc(){

        List<Posts> postsList = postsRepository.findAll();
        List<PostListResponseDto> postListResponseDtoList = new ArrayList<>();
        for(Posts posts : postsList){
            PostListResponseDto dto = buildPostListResponseDto(posts);
            postListResponseDtoList.add(dto);
        }
        return postListResponseDtoList;

    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no post id = " + id));
        postsRepository.delete(posts);
    }


}
