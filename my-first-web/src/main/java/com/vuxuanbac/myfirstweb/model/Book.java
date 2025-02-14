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
 *         + CASCADE: Perform the same operation on relationship (child, property) whenever perform operation on an entity (parent, container).
 *                    PERSIST (save),
 *                    REMOVE (delete: record + persistent context)
 *                    DETACH (not on persistent context)
 *                    MERGE: Update info into same record (on ID)
 *                    ALL,...
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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // Choose Book is Owning side in One-to-Many relationship.
    @ManyToOne(cascade = CascadeType.ALL) // If save a Book object, save publisher inside it also.
    @JoinColumn(name = "publisher_id", referencedColumnName = "id") // Create new column with name "publisher_id" on Book Table
                                                                    // reference to column associated with field "id" of Publisher Class
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name = "book_author"
            // create a column name "book_id" in table "book_author" that reference to column "id" in table Book
            ,joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
            // create a column name "author_id" in table "book_author" that reference to column "id" in table Author
            ,inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Collection<Author> authors;

    @OneToOne(mappedBy = "book")
    private BookFile file;
}
