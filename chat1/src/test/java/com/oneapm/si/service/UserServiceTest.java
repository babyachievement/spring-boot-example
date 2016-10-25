package com.oneapm.si.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * FileName:UserServiceTest
 * Created by HaoQiang
 * Date:16-10-25
 * Time:下午5:22
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    
    @Autowired
    private UserService userService;

    @Before
    public void setup() throws Exception {
        userService.deleteAllUsers();
    }

    @Test
    public void test() throws Exception {
        // 插入5个用户
        userService.create("a", 1);
        userService.create("b", 2);
        userService.create("c", 3);
        userService.create("d", 4);
        userService.create("e", 5);
        // 查数据库，应该有5个用户
        Assert.assertEquals(5, userService.getAllUsersCount().intValue());
        // 删除两个用户
        userService.deleteByName("a");
        userService.deleteByName("e");
        // 查数据库，应该有5个用户
        Assert.assertEquals(3, userService.getAllUsersCount().intValue());

    }
}