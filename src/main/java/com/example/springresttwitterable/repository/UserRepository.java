package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByName(String userName);
}
