package io.github.laxlite.UGCExample.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags") // обратная сторона post_tag; владелец — Post.tags
    private Set<Post> posts = new HashSet<>(); // Set, чтобы не было дублей
}
