package com.vuxuanbac.librarymanagement.model;

import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorID;

    @Column(nullable = false)
    private String name;

    private String avatarUrl;

    private String description;
}
