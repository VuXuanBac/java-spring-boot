package com.vuxuanbac.myfirstweb.controller;

import com.vuxuanbac.myfirstweb.model.Book;
import com.vuxuanbac.myfirstweb.model.Student;
import com.vuxuanbac.myfirstweb.repository.BookRepository;
import com.vuxuanbac.myfirstweb.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books/")
public class BookController {
    @Autowired
    private BookRepository repository;

    @GetMapping()
    public ResponseEntity<?> all(){
        System.out.println("Get All");
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Book book){
        System.out.println(book.getId() + " " + book.getTitle() + " " + book.getPublisher() + " " + book.getAuthors());
        //book.setId(null);
        return ResponseEntity.ok(repository.save(book));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
        Book b = repository.findById(id).orElse(null);
        if(b != null) {
            b.setTitle(book.getTitle());
            b.setPublisher(book.getPublisher());
            repository.save(b);
        }
        return ResponseEntity.ok(b);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Book b = repository.findById(id).orElse(null);
        if(b != null)
            repository.delete(b);
        return ResponseEntity.ok(b);
    }
}
