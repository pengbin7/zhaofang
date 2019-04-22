package com.pb.repository;

import com.pb.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByName(String name);
}
