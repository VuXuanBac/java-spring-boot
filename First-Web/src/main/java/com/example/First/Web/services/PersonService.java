package com.example.First.Web.services;

import com.example.First.Web.dto.PersonDTORequest;
import com.example.First.Web.exceptions.NotFoundException;
import com.example.First.Web.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("default")
public class PersonService implements IPersonService{
    private static int person_count = 0;
    private List<Person> users = new ArrayList<>();

    public PersonService() {
        users.add(new Person(++person_count, "Vũ Xuân Bắc", "bac.vx194230@sis.hust.edu.vn"));
        users.add(new Person(++person_count, "Trần Việt Anh", "anh.tv194224@sis.hust.edu.vn"));
        users.add(new Person(++person_count, "Nguyễn Chí Công", "cong.nc194328@sis.hust.edu.vn"));
        users.add(new Person(++person_count, "Anonymous", "hacker1996@gmail.com"));
    }

    @Override
    public List<Person> getList() {
        return users;
    }

    @Override
    public Person getUser(int id) {
        for (Person p: users) {
            if(p.getId() == id)
                return p;
        }
        throw new NotFoundException("Không tìm thấy User với ID: " + id);
    }

    @Override
    public Person createUser(PersonDTORequest info) {
        var user = new Person(++person_count, info);
        users.add(user);
        return user;
    }

    @Override
    public Person updateUser(int id, PersonDTORequest info) {
        for (Person p: users) {
            if(p.getId() == id) {
                p.Clone(info);
                return p;
            }
        }
        throw new NotFoundException("Không tìm thấy User với ID: " + id);
    }

    @Override
    public Person deleteUser(int id) {
        for (Person p: users) {
            if(p.getId() == id) {
                users.remove(p);
                return p;
            }
        }
        throw new NotFoundException("Không tìm thấy User với ID: " + id);
    }

    @Override
    public List<Person> search(String keyword) {
        List<Person> result = new ArrayList<>();
        for (Person p: users) {
            if(p.getName().contains(keyword) || p.getEmail().contains(keyword)) {
                System.out.println(p.getName());
                result.add(p);
            }
        }
        return result;
    }
}
