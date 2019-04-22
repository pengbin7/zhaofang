package com.pb.entity;

import com.pb.ApplicationTests;
import com.pb.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne(){
        User user = userRepository.findById(1L).get();
        Assert.assertEquals(user.getName(),"waliwali");
        System.err.println("Hello World!");
    }

}
