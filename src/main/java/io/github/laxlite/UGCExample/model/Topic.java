package io.github.laxlite.UGCExample.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String slug;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "post_topic",
            joinColumns = @JoinColumn(name = "topic_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "post_id", nullable = false)
    )
    private Set<Post> posts = new HashSet<>();
}
