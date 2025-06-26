package com.bonbravo.store.repositories;

import com.bonbravo.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
