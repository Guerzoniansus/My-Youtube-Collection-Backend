package com.myytcollection.repository;

import com.myytcollection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class used for database access for User objects.
 */
public interface UserRepository extends JpaRepository<User, String> {
}
