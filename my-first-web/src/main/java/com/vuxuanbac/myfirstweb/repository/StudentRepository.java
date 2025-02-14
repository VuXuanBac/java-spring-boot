package com.vuxuanbac.myfirstweb.repository;

import com.vuxuanbac.myfirstweb.model.Student;
import com.vuxuanbac.myfirstweb.model.StudentContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Can use other CRUD methods that not defined in JPARepository
    // but follow a convention like:
    // findByFirstName(String firstName);
    // Just put a declaration in this interface.

    // Or can create a custom query with annotation @Query
    // Note that the query string must follow JPQA convention, not SQL query
    // JPQA: Use Class name and Class Field name instead of Table name and Column name
    // Or set nativeQuery on @Query if want to use SQL query syntax
    @Query("select s.contact from Student s where s.firstName = ?1 or s.lastName = ?2")
    List<StudentContact> getStudentContactByName(String firstName, String lastName);

}
