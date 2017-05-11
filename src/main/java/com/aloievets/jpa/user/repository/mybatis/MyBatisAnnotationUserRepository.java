package com.aloievets.jpa.user.repository.mybatis;

import com.aloievets.jpa.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Andrew on 11.05.2017.
 */
public interface MyBatisAnnotationUserRepository {

    @Select("SELECT id, email FROM User WHERE id = #{id}")
    User find(@Param("id") long id);

    @Update("UPDATE User SET email = #{email} WHERE id = #{id}")
    void update(@Param("id") long id, @Param("email") String email);

    @Insert("INSERT INTO User (id, email) VALUES (#{id}, #{email})")
    void insert(User user);
}
