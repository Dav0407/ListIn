package com.igriss.ListIn.repository;

import com.igriss.ListIn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}