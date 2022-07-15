package com.main.todogo.repos;

import com.main.todogo.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordsRepository extends JpaRepository<Password, Long> {
}
