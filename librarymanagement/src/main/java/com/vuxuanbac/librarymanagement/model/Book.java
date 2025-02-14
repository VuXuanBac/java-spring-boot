package com.vuxuanbac.librarymanagement.model;

import lombok.*;
import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookID;

    private String isbn;

    @Column(nullable = false)
    private String title;

    private String coverUrl;

    private Integer publishYear;

    private String publisher;

    @Column(nullable = false)
    private Integer numberOfPages;

    @Column(columnDefinition = "TEXT") // 65535 bytes
    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "book_genre"
            ,joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Collection<Genre> genres;
}
