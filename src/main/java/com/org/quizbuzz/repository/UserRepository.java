package com.org.quizbuzz.repository;

import com.org.quizbuzz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
