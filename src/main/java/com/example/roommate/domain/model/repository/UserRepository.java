package com.example.roommate.domain.model.repository;

import com.example.roommate.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    @Query ("select u from User u where u.githubadresse = :email")
    User findByEmail(String email);
    @Query ("select u from User u where u.githubadresse = :email AND u.password = :password")
    User findByEmailAndByPassword(String email, String password);

    @Query("select u.is_admin from User u where u.id = :id")
    Boolean isAdmin(String id);
}
