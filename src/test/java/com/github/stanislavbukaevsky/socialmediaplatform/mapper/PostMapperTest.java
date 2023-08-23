package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostCreatedDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Image;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PostMapperTest {
    private static final Long ID = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_IMAGE = 10L;
    private static final Long ID_IMAGE_2 = 11L;
    private static final Long ID_USER = 20L;
    private static final Long ID_USER_2 = 21L;
    private static final String TITLE = "Заголовок";
    private static final String TITLE_2 = "Заголовок 2";
    private static final String TEXT = "Текст";
    private static final String TEXT_2 = "Текст 2";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final LocalDateTime CREATED_AT_2 = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT_2 = LocalDateTime.now();
    private static final String IMAGE_STRING = "/post/images/" + ID;
    private static final String IMAGE_STRING_2 = "/post/images/" + ID_2;
    @InjectMocks
    private PostMapperImpl postMapper;

    @Test
    public void toPostDtoTest() {
        Image image = new Image();
        image.setId(ID_IMAGE);
        User user = new User();
        user.setId(ID_USER);
        Post post = new Post();
        post.setId(ID);
        post.setTitle(TITLE);
        post.setText(TEXT);
        post.setCreatedAt(CREATED_AT);
        post.setUpdatedAt(UPDATED_AT);
        post.setUser(user);
        post.setImage(image);

        PostDto postDto = postMapper.toPostDto(post);

        Assertions.assertThat(postDto).isNotNull();
        Assertions.assertThat(postDto.getId()).isEqualTo(ID);
        Assertions.assertThat(postDto.getImage()).isEqualTo(IMAGE_STRING);
        Assertions.assertThat(postDto.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(postDto.getText()).isEqualTo(TEXT);
        Assertions.assertThat(postDto.getAuthor()).isEqualTo(ID_USER);
        Assertions.assertThat(postDto.getDateTime()).isEqualTo(CREATED_AT);
    }

    @Test
    public void toPostTest() {
        PostCreatedDto postCreatedDto = new PostCreatedDto();
        postCreatedDto.setTitle(TITLE);
        postCreatedDto.setText(TEXT);

        Post post = postMapper.toPost(postCreatedDto);

        Assertions.assertThat(post).isNotNull();
        Assertions.assertThat(post.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(post.getText()).isEqualTo(TEXT);
    }

    @Test
    public void toListPostDtoTest() {
        Image imageOne = new Image();
        imageOne.setId(ID_IMAGE);
        User userOne = new User();
        userOne.setId(ID_USER);
        Post postOne = new Post();
        postOne.setId(ID);
        postOne.setTitle(TITLE);
        postOne.setText(TEXT);
        postOne.setCreatedAt(CREATED_AT);
        postOne.setUpdatedAt(UPDATED_AT);
        postOne.setUser(userOne);
        postOne.setImage(imageOne);

        Image imageTwo = new Image();
        imageTwo.setId(ID_IMAGE_2);
        User userTwo = new User();
        userTwo.setId(ID_USER_2);
        Post postTwo = new Post();
        postTwo.setId(ID_2);
        postTwo.setTitle(TITLE_2);
        postTwo.setText(TEXT_2);
        postTwo.setCreatedAt(CREATED_AT_2);
        postTwo.setUpdatedAt(UPDATED_AT_2);
        postTwo.setUser(userTwo);
        postTwo.setImage(imageTwo);

        List<Post> posts = new ArrayList<>();
        posts.add(postOne);
        posts.add(postTwo);
        List<PostDto> postsDto = postMapper.toListPostDto(posts);

        Assertions.assertThat(postsDto).isNotNull();
        Assertions.assertThat(postsDto).hasSize(2);
        Assertions.assertThat(postsDto.get(0).getId()).isEqualTo(ID);
        Assertions.assertThat(postsDto.get(0).getImage()).isEqualTo(IMAGE_STRING);
        Assertions.assertThat(postsDto.get(0).getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(postsDto.get(0).getText()).isEqualTo(TEXT);
        Assertions.assertThat(postsDto.get(0).getAuthor()).isEqualTo(ID_USER);
        Assertions.assertThat(postsDto.get(0).getDateTime()).isEqualTo(CREATED_AT);

        Assertions.assertThat(postsDto.get(1).getId()).isEqualTo(ID_2);
        Assertions.assertThat(postsDto.get(1).getImage()).isEqualTo(IMAGE_STRING_2);
        Assertions.assertThat(postsDto.get(1).getTitle()).isEqualTo(TITLE_2);
        Assertions.assertThat(postsDto.get(1).getText()).isEqualTo(TEXT_2);
        Assertions.assertThat(postsDto.get(1).getAuthor()).isEqualTo(ID_USER_2);
        Assertions.assertThat(postsDto.get(1).getDateTime()).isEqualTo(CREATED_AT_2);
    }
}
