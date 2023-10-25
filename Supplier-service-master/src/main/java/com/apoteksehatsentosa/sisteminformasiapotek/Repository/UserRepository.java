package com.apoteksehatsentosa.sisteminformasiapotek.Repository;

import com.apoteksehatsentosa.sisteminformasiapotek.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByUsername(String username);
}
