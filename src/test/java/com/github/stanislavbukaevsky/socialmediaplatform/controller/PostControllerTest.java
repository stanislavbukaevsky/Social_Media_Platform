package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Image;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.ImageRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.PostRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    private static final String AUTH_NAME = "AuthName";
    private static final Long ID_POST = 1L;
    private static final Long ID_POST_2 = 2L;
    private static final Long ID_USER = 10L;
    private static final Long ID_USER_2 = 11L;
    private static final Long ID_IMAGE = 20L;
    private static final Long ID_IMAGE_2 = 21L;
    private static final String TITLE = "Заголовок";
    private static final String TITLE_2 = "Заголовок 2";
    private static final String TEXT = "Текстовая информация";
    private static final String TEXT_2 = "Текстовая информация 2";
    private static final String IMAGE_STRING = "/post/images/" + ID_POST;
    private static final String IMAGE_PATH = "src/test/resources/image.png";
    private static final String IMAGE_FILENAME = "image.png";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final LocalDateTime CREATED_AT_2 = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT_2 = LocalDateTime.now();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ImageRepository imageRepository;


    @Test
    @WithMockUser(username = AUTH_NAME)
    public void getAllPostTest() throws Exception {
        User userOne = new User();
        userOne.setId(ID_USER);
        Image imageOne = new Image();
        imageOne.setId(ID_IMAGE);
        Post postOne = new Post();
        postOne.setId(ID_POST);
        postOne.setTitle(TITLE);
        postOne.setText(TEXT);
        postOne.setCreatedAt(CREATED_AT);
        postOne.setUpdatedAt(UPDATED_AT);
        postOne.setUser(userOne);
        postOne.setImage(imageOne);

        User userTwo = new User();
        userTwo.setId(ID_USER_2);
        Image imageTwo = new Image();
        imageTwo.setId(ID_IMAGE_2);
        Post postTwo = new Post();
        postTwo.setId(ID_POST_2);
        postTwo.setTitle(TITLE_2);
        postTwo.setText(TEXT_2);
        postTwo.setCreatedAt(CREATED_AT_2);
        postTwo.setUpdatedAt(UPDATED_AT_2);
        postTwo.setUser(userTwo);
        postTwo.setImage(imageTwo);

        when(postRepository.findAllByPost(any())).thenReturn(List.of(postOne, postTwo));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].id").value(ID_POST))
                .andExpect(jsonPath("$.results[1].id").value(ID_POST_2));
    }
}
