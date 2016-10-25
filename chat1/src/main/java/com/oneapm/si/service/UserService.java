/**
 * FileName:UserService
 * Created by HaoQiang
 * Date:16-10-25
 * Time:下午5:04
 */
package com.oneapm.si.service;

import com.oneapm.si.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 新增一个用户
     * @param name
     * @param age
     */
    void create(String name, Integer age);
    /**
     * 根据name删除一个用户高
     * @param name
     */
    void deleteByName(String name);
    /**
     * 获取用户总量
     */
    Integer getAllUsersCount();
    /**
     * 删除所有用户
     */
    void deleteAllUsers();

    List<User> getAllUsers();

    User getUserByID(long id);
}
