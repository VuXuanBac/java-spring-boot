package com.vuxuanbac.myfirstweb.repository;

import com.vuxuanbac.myfirstweb.model.Student;
import com.vuxuanbac.myfirstweb.model.StudentContact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;
//
//    @Test
//    public void createStudent(){
//        Student s = new Student();
//        s.setLastName("Xuân Bắc");
//        s.setFirstName("Vũ");
//        s.setEmail("bac.vx194230@sis.hust.edu.vn");
//        s.setDob(LocalDate.of(2001, Month.JANUARY, 7));
//        StudentContact contact = new StudentContact("0123456789", "Father", "0987654321", "Home", false);
//        s.setContact(contact);
//        Student t = new Student();
//        t.setLastName("Abc");
//        t.setFirstName("Vũ");
//        t.setEmail("bac.vxMnpq@sis.hust.edu.vn");
//        t.setDob(LocalDate.of(2001, Month.JANUARY, 7));
//        StudentContact contact2 = new StudentContact("9999999999", "Mother", "0987654321", "Home", true);
//        t.setContact(contact2);
//        Student v = new Student();
//        v.setLastName("123456");
//        v.setFirstName("MAbcNP");
//        v.setEmail("bac.vxAbcd@sis.hust.edu.vn");
//        v.setDob(LocalDate.of(2001, Month.JANUARY, 7));
//        StudentContact contact3 = new StudentContact("000000000", "Mother", "0987654321", "Home", true);
//        v.setContact(contact3);
//        ArrayList<Student> list = new ArrayList<>();
//        list.add(s);
//        list.add(t);
//        list.add(v);
//        repository.saveAll(list);
//    }
//
//    @Test
//    public void getAllStudents(){
//        List<StudentContact> contacts = repository.getStudentContactByName("mabcnp", "Abc Xyz");
//
//        System.out.println("contacts = " + contacts);
//    }

}