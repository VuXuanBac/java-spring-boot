package com.example.First.Web.services;

import com.example.First.Web.dto.PersonDTORequest;
import com.example.First.Web.models.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPersonService {
    List<Person> getList();
    Person getUser(int id);
    Person createUser(PersonDTORequest person);
    Person updateUser(int id, PersonDTORequest info);
    Person deleteUser(int id);

    List<Person> search(String keyword);
}
