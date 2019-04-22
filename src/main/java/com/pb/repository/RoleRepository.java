package com.pb.repository;

import com.pb.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author pengbin
 * @date 2019/4/17 17:42
 */
public interface RoleRepository extends CrudRepository<Role,Long> {

    List<Role> findRolesByUserId(Long userId);

}
