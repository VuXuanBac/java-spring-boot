package com.example.First.Web.models;

import com.example.First.Web.dto.PersonDTORequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Person {
    public Person(int id, PersonDTORequest info){
        this.id = id;
        Clone(info);
    }
    public void Clone(PersonDTORequest info){
        this.name = info.getName();
        this.email = info.getEmail();
    }
    private int id;
    private String name;
    private String email;
}
