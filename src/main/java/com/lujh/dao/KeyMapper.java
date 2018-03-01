package com.lujh.dao;

import com.lujh.bean.Key;
import com.lujh.bean.KeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface KeyMapper {
    @SelectProvider(type=KeySqlProvider.class, method="countByExample")
    long countByExample(KeyExample example);

    @DeleteProvider(type=KeySqlProvider.class, method="deleteByExample")
    int deleteByExample(KeyExample example);

    @Delete({
        "delete from custom_key",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into custom_key (id, keyStr, ",
        "value)",
        "values (#{id,jdbcType=INTEGER}, #{keystr,jdbcType=VARCHAR}, ",
        "#{value,jdbcType=VARCHAR})"
    })
    int insert(Key record);

    @InsertProvider(type=KeySqlProvider.class, method="insertSelective")
    int insertSelective(Key record);

    @SelectProvider(type=KeySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="keyStr", property="keystr", jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", jdbcType=JdbcType.VARCHAR)
    })
    List<Key> selectByExample(KeyExample example);

    @Select({
        "select",
        "id, keyStr, value",
        "from custom_key",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="keyStr", property="keystr", jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", jdbcType=JdbcType.VARCHAR)
    })
    Key selectByPrimaryKey(Integer id);

    @UpdateProvider(type=KeySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Key record, @Param("example") KeyExample example);

    @UpdateProvider(type=KeySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Key record, @Param("example") KeyExample example);

    @UpdateProvider(type=KeySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Key record);

    @Update({
        "update custom_key",
        "set keyStr = #{keystr,jdbcType=VARCHAR},",
          "value = #{value,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Key record);
}