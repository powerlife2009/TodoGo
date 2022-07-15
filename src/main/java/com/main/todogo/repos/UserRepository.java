package com.main.todogo.repos;

import com.main.todogo.models.Role;
import com.main.todogo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
}
