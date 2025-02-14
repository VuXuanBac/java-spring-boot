package com.vuxuanbac.myfirstweb.repository;

import com.vuxuanbac.myfirstweb.model.Book;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
