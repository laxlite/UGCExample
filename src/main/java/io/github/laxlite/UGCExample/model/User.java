package io.github.laxlite.UGCExample.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(nullable = false, updatable = false, insertable = false)
    private Instant createdAt;

    @Setter
    @Column(nullable = false)
    private String avatar;

    @Setter
    @Column(nullable = false)
    private String hash;

    @Setter
    @Column(nullable = false, insertable = false)
    private double rating = 0.0;

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),    // FK на владеющую сущность
            inverseJoinColumns = @JoinColumn(name = "event_id", nullable = false)
    )
    private Set<Event> events = new HashSet<>();

    @Setter
    @Column(nullable = false, unique = true)
    private String email;
}