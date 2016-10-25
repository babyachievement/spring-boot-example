/**
 * FileName:UserRepository
 * Created by HaoQiang
 * Date:16-10-25
 * Time:下午5:42
 */
package com.oneapm.si.dao;

import com.oneapm.si.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 在Spring-data-jpa中，只需要编写类似这样的接口就可实现数据访问,不需要实现接口
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);

}

