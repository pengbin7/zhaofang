package com.pb.service.user;

import com.pb.entity.Role;
import com.pb.entity.User;
import com.pb.repository.RoleRepository;
import com.pb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author pengbin
 * @date 2019/4/17 17:01
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public User findUserByName(String name) {
        User user = userRepository.findByName(name);
        if(user==null){
            return new User();
        }
        List<Role> roleList = roleRepository.findRolesByUserId(user.getId());
        if(roleList.isEmpty()){
            throw new DisabledException("权限非法");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        roleList.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
        user.setAuthorities(authorities);
        return user;
    }


}
