package com.example.First.Web.controllers;

import com.example.First.Web.dto.PersonDTORequest;
import com.example.First.Web.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class PersonController {
    private IPersonService service;

    // Autowired: Auto create and inject
    // Qualifier: Choose BEAN for injecting if has many. Set name in @Component or other BEAN annotations.
    //            And use that name in @Qualifier.
    @Autowired
    public PersonController(IPersonService service) {
        this.service = service;
    }

    // Set path uses: { [path_pattern,] }
    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getList());
    }

    // @RequestParam: Cast URL params into method params
    //                required = false: Not Required
    //                defaultValue : if not exist, used defaultValue.
    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam(required = false, defaultValue = "") String keyword){
        System.out.println("Enter search" + keyword);
        return ResponseEntity.status(HttpStatus.OK).body(service.search(keyword));
    }

    // @PathVariable: Cast a part of URL Path into method params
    @GetMapping("/{id}")
    public String getUser(@PathVariable int id){
        var user = service.getUser(id);
        return "index";
    }

    // @RequestBody: Cast data in Body into an Object.
    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody PersonDTORequest info){
        return ResponseEntity.status(HttpStatus.OK).body(service.createUser(info));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable int id){
        System.out.println(id);
        var user = service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // @PutMapping: If id exist: Update. Otherwise: Add
    @PutMapping("/{id}")
    public ResponseEntity<?>  updateUser(@PathVariable int id, @RequestBody PersonDTORequest info){
        var user = service.updateUser(id, info);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
