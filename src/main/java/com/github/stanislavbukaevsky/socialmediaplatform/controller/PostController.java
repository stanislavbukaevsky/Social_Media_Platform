package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FeedActivityDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostCreatedDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ResponseWrapperPostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы со всеми постами, опубликованными на платформе
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа со всеми постами размещенными на платформе", description = "Позволяет управлять методами по работе со всеми постами размещенными на платформе")
public class PostController {
    private final PostService postService;

    /**
     * Этот метод позволяет добавлять новые посты на платформу
     *
     * @param postCreatedDto добавляемый пост
     * @param imageFile      изображение
     * @return Возвращает новый, добавленный пост на платформу
     * @throws IOException общий класс исключений ввода-вывода
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пост успешно добавлен на платформу (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")

    })
    @Operation(summary = "Метод для добавления постов на платформу", description = "Позволяет добавить пост на платформу")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> addPost(@RequestPart(name = "description") @Valid PostCreatedDto postCreatedDto, @RequestPart(name = "image") MultipartFile imageFile) throws IOException {
        log.info(ADD_POST_MESSAGE_LOGGER_CONTROLLER);
        return ResponseEntity.ok(postService.addPost(postCreatedDto, imageFile));
    }

    /**
     * Этот метод позволяет получить и просмотреть все посты, опубликованные на платформе
     *
     * @param title заголовок поста (его название)
     * @return Возвращает все опубликованные посты на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посты успешно получены (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperPostDto.class)))
    })
    @Operation(summary = "Метод для получения всех постов, опубликованных на платформе", description = "Позволяет просмотреть все посты, размещенные на платформе")
    @GetMapping
    public ResponseEntity<ResponseWrapperPostDto> getAllPost(@Parameter(description = "Заголовок поста") @RequestParam(required = false) String title) {
        log.info(GET_ALL_POST_MESSAGE_LOGGER_CONTROLLER, title);
        return ResponseEntity.ok(postService.getAllPost(title));
    }

    /**
     * Этот метод позволяет получить изображение у поста, размещенного на платформе
     *
     * @param id идентификатор поста
     * @return Возвращает изображение пользователю
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение у поста успешно получено (OK)", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = byte[].class))),
            @ApiResponse(responseCode = "404", description = "Изображение у поста не найдено (Not Found)")
    })
    @Operation(summary = "Метод для получения изображения у поста, размещенного на платформе", description = "Позволяет получить изображение у поста, размещенного на платформе")
    @GetMapping(value = "/images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getPostImage(@Parameter(description = "Идентификатор поста") @PathVariable Long id) {
        log.info(GET_POST_IMAGE_ID_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(postService.getPostImage(id));
    }

    /**
     * Этот метод позволяет изменить информацию о посте, размещенного на платформе
     *
     * @param id             идентификатор изменяемого поста
     * @param postCreatedDto пост
     * @return Возвращает измененный пост на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пост успешно изменен (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пост не найден (Not Found)")
    })
    @Operation(summary = "Метод для изменения информации о посте, размещенного на платформе", description = "Позволяет изменить информацию о посте, размещенном на платформе")
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Parameter(description = "Идентификатор изменяемого поста") @PathVariable Long id, @RequestBody @Valid PostCreatedDto postCreatedDto) {
        log.info(UPDATE_POST_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(postService.updatePost(id, postCreatedDto));
    }

    /**
     * Этот метод позволяет изменить изображение у поста, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile изображение
     * @return Возвращает DTO измененного изображения у поста, размещенного на платформе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение у поста успешно изменено (OK)", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден (Not Found)")
    })
    @Operation(summary = "Метод для изменения изображения у поста, размещенного на платформе", description = "Позволяет изменить изображение у поста, размещенного на платформе")
    @PatchMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> updatePostImage(@Parameter(description = "Идентификатор поста") @PathVariable Long id, @RequestPart(name = "image") MultipartFile imageFile) throws IOException {
        log.info(UPDATE_POST_IMAGE_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok(postService.updatePostImage(id, imageFile));
    }

    /**
     * Этот метод позволяет удалить пост с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого поста
     * @return Возвращает статус "ОК" с кодом 200, если пост удален
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пост успешно удален (OK)"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)")
    })
    @Operation(summary = "Метод для удаления поста, размещенного на платформе", description = "Позволяет удалить пост, размещенный на платформе")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@Parameter(description = "Идентификатор удаляемого поста") @PathVariable Long id) {
        postService.deletePost(id);
        log.info(DELETE_POST_MESSAGE_LOGGER_CONTROLLER, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Этот метод позволяет осуществлять поиск постов от пользователей, на которых он подписан.
     * Также, метод поддерживает пагинацию и сортировку по времени создания постов
     *
     * @param page номер страницы
     * @param size количество постов на странице
     * @return Возвращает сконвертированную DTO ленты активности пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список сообщений успешно получен (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FeedActivityDto.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")

    })
    @Operation(summary = "Метод для поиска постов от пользователей, на которых он подписан", description = "Позволяет осуществлять поиск постов от пользователей, на которых он подписан")
    @GetMapping("/activity")
    public ResponseEntity<List<FeedActivityDto>> userActivityFeed(
            @Parameter(description = "Номер страницы") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Количество постов на странице") @RequestParam(required = false, defaultValue = "5") int size) {
        log.info(USER_ACTIVITY_FEED_MESSAGE_LOGGER_CONTROLLER, page, size);
        return ResponseEntity.ok(postService.userActivityFeed(page, size));
    }
}
