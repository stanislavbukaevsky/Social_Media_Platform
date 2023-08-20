package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostCreatedDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Маппер-интерфейс, который преобразует сущность всех постов, опубликованных на платформе {@link Post}
 * в DTO {@link PostDto}
 */
@Mapper
public interface PostMapper {

    /**
     * Этот метод конвертирует сущность поста в DTO поста. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param post сущность поста
     * @return Возвращает сконвертированную DTO поста, опубликованного на платформе
     */
    @Mapping(source = "post", target = "image")
    @Mapping(source = "post.user.id", target = "author")
    @Mapping(source = "createdAt", target = "dateTime")
    PostDto toPostDto(Post post);

    /**
     * Этот метод конвертирует DTO добавленного поста в сущность
     *
     * @param postCreatedDto DTO добавляемого поста
     * @return Возвращает сконвертированную сущность поста
     */
    @Mapping(target = "image", ignore = true)
    Post toPost(PostCreatedDto postCreatedDto);

    /**
     * Этот метод конвертирует список с параметром сущности поста, в список с параметром DTO поста
     *
     * @param posts список постов
     * @return Возвращает сконвертированный список DTO постов
     */
    List<PostDto> toListPostDto(List<Post> posts);

    /**
     * Этот метод указывает путь к изображению
     *
     * @param post сущность поста
     * @return Возвращает ссылку на изображение в строковом виде
     */
    default String toStringLink(Post post) {
        return "/post/images/" + post.getId();
    }
}
