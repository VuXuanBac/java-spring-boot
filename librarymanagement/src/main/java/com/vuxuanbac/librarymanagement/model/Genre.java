package com.vuxuanbac.librarymanagement.model;

import lombok.*;
import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreID;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Collection<Book> books;
}
