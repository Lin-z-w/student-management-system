package com.example.webdemo.mapper;

import com.example.webdemo.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from dept")
    List<Dept> list();

    /**
     * delete dept by id
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) " +
            "values (#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);
}
