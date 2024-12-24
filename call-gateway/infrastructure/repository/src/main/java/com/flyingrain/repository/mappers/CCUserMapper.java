package com.flyingrain.repository.mappers;

import com.flyingrain.repository.models.CCUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CCUserMapper {

    @Select("select * from cc_user where id = #{id}")
    @Results(id = "CCUserResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "register_number", property = "registerNumber")
    })
    List<CCUser> queryById(@Param("id") Integer userId);

}
