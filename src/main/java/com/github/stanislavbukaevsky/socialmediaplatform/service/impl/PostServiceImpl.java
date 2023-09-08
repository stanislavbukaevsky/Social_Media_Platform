package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FeedActivityDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostCreatedDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ResponseWrapperPostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Image;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.ActivityFeedNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.BlockingForbiddenException;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.ImageNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.PostNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.PostMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.PostRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import com.github.stanislavbukaevsky.socialmediaplatform.service.ImageService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.PostService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех постов, опубликованных на платформе.
 * Реализует интерфейс {@link PostService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final UserSecurity userSecurity;
    private final ImageService imageService;
    private final PostMapper postMapper;

    /**
     * Реализация метода для поиска поста по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает найденный пост по его идентификатору
     */
    @Override
    public Post findPostById(Long id) {
        log.info(FIND_POST_BY_ID_MESSAGE_LOGGER_SERVICE, id);
        return postRepository.findPostById(id).orElseThrow(() ->
                new PostNotFoundException(POST_NOT_FOUND_EXCEPTION));
    }

    /**
     * Реализация метода для добавления новых постов на платформу
     *
     * @param postCreatedDto добавляемый пост
     * @param imageFile      изображение
     * @return Возвращает новый, добавленный DTO-пост с изображением на платформу
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public PostDto addPost(PostCreatedDto postCreatedDto, MultipartFile imageFile) throws IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        User user = userService.findUserByUsername(userSecurity.getUsername());
        if (user.getBlocking().equals(Blocking.NOT_BAN)) {
            Post post = postMapper.toPost(postCreatedDto);
            post.setCreatedAt(dateTime);
            post.setUpdatedAt(dateTime);
            post.setUser(user);
            Post savedPost = postRepository.save(post);
            Image image = imageService.addImagePost(savedPost.getId(), imageFile);
            savedPost.setImage(image);
            log.info(ADD_POST_MESSAGE_LOGGER_SERVICE);
            return postMapper.toPostDto(savedPost);
        }
        throw new BlockingForbiddenException(BLOCKING_FORBIDDEN_EXCEPTION);
    }

    /**
     * Реализация метода для получения и просмотра всех постов, опубликованных на платформе
     *
     * @param title зоголовок поста
     * @return Возвращает DTO всех опубликованных постов на платформе
     */
    @Override
    public ResponseWrapperPostDto getAllPost(String title) {
        List<Post> posts = postRepository.findAllByPost(title);
        List<PostDto> postsDto = postMapper.toListPostDto(posts);
        ResponseWrapperPostDto responseWrapperPostDto = new ResponseWrapperPostDto();
        responseWrapperPostDto.setCount(postsDto.size());
        responseWrapperPostDto.setResults(postsDto);
        log.info(GET_ALL_POST_MESSAGE_LOGGER_SERVICE, title);
        return responseWrapperPostDto;
    }

    /**
     * Реализация метода для получения изображения у поста по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает массив байт искомого изображения
     */
    @Override
    public byte[] getPostImage(Long id) {
        byte[] image = imageService.getPostImage(id);
        if (image == null) {
            throw new ImageNotFoundException(IMAGE_NOT_FOUND_EXCEPTION);
        }
        log.info(GET_POST_IMAGE_ID_MESSAGE_LOGGER_SERVICE, id);
        return image;
    }

    /**
     * Реализация метода для измения информации о посте, размещенного на платформе
     *
     * @param id             идентификатор изменяемого поста
     * @param postCreatedDto добавленный пост
     * @return Возвращает DTO измененного поста на платформе
     */
    @Override
    public PostDto updatePost(Long id, PostCreatedDto postCreatedDto) {
        Post post = findPostById(id);
        if (post.getUser().getBlocking().equals(Blocking.NOT_BAN)) {
            LocalDateTime dateTime = LocalDateTime.now();
            checkUsersByPost(id);
            post.setTitle(postCreatedDto.getTitle());
            post.setText(postCreatedDto.getText());
            post.setUpdatedAt(dateTime);
            Post result = postRepository.save(post);
            log.info(UPDATE_POST_MESSAGE_LOGGER_SERVICE, id);
            return postMapper.toPostDto(result);
        }
        throw new BlockingForbiddenException(BLOCKING_FORBIDDEN_EXCEPTION_2);
    }

    /**
     * Реализация метода для изменения изображения у поста, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile изображение
     * @return Возвращает DTO измененного изображения у поста, размещенного на платформе
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public PostDto updatePostImage(Long id, MultipartFile imageFile) throws IOException {
        Post post = findPostById(id);
        if (post.getUser().getBlocking().equals(Blocking.NOT_BAN)) {
            checkUsersByPost(id);
            if (post.getImage() == null) {
                imageService.addImagePost(post.getId(), imageFile);
            } else if (post.getImage() != null) {
                imageService.updateImagePost(post.getId(), imageFile);
            }
            log.info(UPDATE_POST_IMAGE_MESSAGE_LOGGER_SERVICE, id);
            return postMapper.toPostDto(post);
        }
        throw new BlockingForbiddenException(BLOCKING_FORBIDDEN_EXCEPTION_3);
    }

    /**
     * Реализация метода для удаления поста с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого поста
     */
    @Override
    public void deletePost(Long id) {
        Post post = findPostById(id);
        if (post.getUser().getBlocking().equals(Blocking.NOT_BAN)) {
            if (checkUsersByPost(post.getId())) {
                postRepository.deleteById(id);
                log.info(DELETE_POST_MESSAGE_LOGGER_SERVICE, id);
            }
        }
        throw new BlockingForbiddenException(BLOCKING_FORBIDDEN_EXCEPTION_4);
    }

    /**
     * Реализация метода для поиска постов от пользователей, на которых он подписан.
     * Также, метод поддерживает пагинацию и сортировку по времени создания постов
     *
     * @param page номер страницы
     * @param size количество постов на странице
     * @return Возвращает сконвертированную DTO ленты активности пользователя
     */
    @Override
    public List<FeedActivityDto> userActivityFeed(int page, int size) {
        User user = userService.findUserByUsername(userSecurity.getUsername());
        Set<User> userSubscribers = user.getSubscribers();
        if (userSubscribers.isEmpty()) {
            throw new ActivityFeedNotFoundException(ACTIVITY_FEED_NOT_FOUND_EXCEPTION);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(userSubscribers, pageable);

        List<FeedActivityDto> feedsActivityDto = new ArrayList<>();
        for (Post post : posts) {
            FeedActivityDto feedActivityDto = new FeedActivityDto();
            feedActivityDto.setId(post.getId());
            feedActivityDto.setTitle(post.getTitle());
            feedActivityDto.setText(post.getText());
            feedActivityDto.setUsername(post.getUser().getUsername());
            feedActivityDto.setDateTime(post.getUpdatedAt());
            feedsActivityDto.add(feedActivityDto);
        }
        log.info(USER_ACTIVITY_FEED_MESSAGE_LOGGER_SERVICE, page, size);
        return feedsActivityDto;
    }

    /**
     * Приватный метод, который проверяет авторизированного пользователя, размещающего пост на платформе и пользователя по его роли.
     * Этот метод может выбросить исключение {@link ResponseStatusException} со статусом 403, если у пользователя нет доступа для действия
     *
     * @param id идентификатор поста
     * @return Возвращает true, если условие выполняется, в противном случае выбрасывает исключение
     */
    private boolean checkUsersByPost(Long id) {
        Boolean checkAuthUserToPost = findPostById(id).getUser().getUsername().equals(userSecurity.getUsername());
        Boolean checkAdmin = userSecurity.getAuthorities().stream().anyMatch(us -> us.getAuthority().contains(Role.ADMIN.name()));
        if (!(checkAuthUserToPost || checkAdmin)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, RESPONSE_STATUS_EXCEPTION);
        }
        log.info(CHECK_USERS_BY_POST_MESSAGE_LOGGER_SERVICE, id);
        return true;
    }
}
