package ru.shmelev.vinylshop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    private String label;

    private String country;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "total_duration")
    private Integer totalDuration;

    private BigDecimal price;

    @Column(nullable = false)
    private String format;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Track> tracklist = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "other_attributes")
    private Map<String, Object> otherAttributes;

    @ManyToMany
    @JoinTable(
            name = "product_genres",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Data
    @NoArgsConstructor
    public static class Track {
        private Integer position;
        private String title;
        private String duration; // или Integer seconds
    }
}