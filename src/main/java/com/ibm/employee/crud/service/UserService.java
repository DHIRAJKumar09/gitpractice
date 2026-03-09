package com.ibm.employee.crud.service;

import com.ibm.employee.crud.entity.User;
import com.ibm.employee.crud.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(User user) {
        if (user.getName() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        return repo.save(user);
    }
    public List<User> getAll(){
        List<User> userlist =repo.findAll();
        if (userlist.isEmpty()){
            throw new  RuntimeException("user not found in the data base");
        }
        return userlist;

    }

    public User get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User update(Long id, User user) {
        User existing = get(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) {
        User user = get(id);
        repo.delete(user);
    }
}

