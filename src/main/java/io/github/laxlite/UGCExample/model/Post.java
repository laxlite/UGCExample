package io.github.laxlite.UGCExample.model;

import io.github.laxlite.UGCExample.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false) // id генерирует БД
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private User author;

    @Setter
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "post_status") // чтобы сгенерировался тип post_status из БД
    private PostStatus status = PostStatus.DRAFT;

    @Setter
    @Column(nullable = false, length = 100)
    private String header;

    @Setter
    @Column(nullable = false, insertable = false)
    private String content = "";

    @Setter
    @Column(nullable = false) // rating INTEGER NOT NULL
    private int rating = 0; // DEFAULT 0; дублируем в Java

    @ManyToMany // связь post_tag: many-to-many
    @JoinTable(
            name = "post_tag", // связующая таблица из миграции
            joinColumns = @JoinColumn(name = "post_id"), // FK на post
            inverseJoinColumns = @JoinColumn(name = "tag_id") // FK на tag
    )
    private Set<Tag> tags = new HashSet<>(); // Set, чтобы не было дублей

    @ManyToMany // связь post_comment: many-to-many
    @JoinTable(
            name = "post_comment", // связующая таблица из миграции
            joinColumns = @JoinColumn(name = "post_id"), // FK на post
            inverseJoinColumns = @JoinColumn(name = "comment_id") // FK на comment
    )
    private Set<Comment> comments = new HashSet<>(); // Set, чтобы не было дублей
}
