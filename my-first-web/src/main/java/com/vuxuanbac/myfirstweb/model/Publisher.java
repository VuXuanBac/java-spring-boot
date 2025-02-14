/***
 * An example of JPA Relationship
 * Classes: Book, BookFile (1-1), Publisher(1-n), Author(n-n)
 *         + Owning side: The Entity that define @JoinColumn
 *         + Referencing side: The other side.
 *
 *         + FETCH: EAGER (Load one entity and relationship data alse), LAZY (Load one entity but only load relationship when needed)
 *                  @OneToMany: LAZY
 *                  @ManyToOne:  EAGER
 *         + OPTIONAL: (nullable)
 *                  @OneToMany (true always)
 *                  @ManyToOne(optional = false) : Can't save a Book without Publisher data
 *
 *         + CASCADE: Perform the same operation on relationship whenever perform operation on an entity.
 *                    PERSIST (save), REMOVE, DETACH, MERGE, ALL,...
 *         + MAPPEDBY: Setup a bidirectional relationship.
 *                  @OneToMany(mappedBy = "[property_name_in_owning_side_class]")
 *                  @OneToOne(mappedBy = "[property_name_in_owning_side_class]")
 * - One-to-Many (Publisher 1 - Book n):
 *       + Should choose the "Many" Entity is Owning side (Book)
 * - One-to-One (BookFile 1 - Book 1):
 *       + Foreign column will be created on Owning side's Table
 *       + Should choose the Entity that can not exist without the other Entity is Owning side (BookFile)
 *         So optional = false on @OneToOne annotation on Owning side.
 * - Many-to-Many (Author n - Book n):
 *       + Create new table @JoinTable with 2 columns
 *       + referenceColumn: A (some) column(s) reference to a property in Owning side Entity
 *       + inverseReferenceColumn: A (some) column(s) reference to a property in Referencing side Entity
 */
package com.vuxuanbac.myfirstweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) // can not change
    private Long id;

    @Column(nullable = false)
    private String name;

    // Choose Publisher is Owning side in One-to-Many relationship
    // @OneToMany
    // @JoinColumn(name = "publisher_id", referencedColumnName = "id") // Create new column with name "publisher_id" on Book (Many) Table
    //                                                                 // reference to column associated with field "id" of Publisher Class
    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private Collection<Book> books = new HashSet<>();

}