package com.lujh.dao;

import com.lujh.bean.AccessLog;
import com.lujh.bean.AccessLogExample;
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

public interface AccessLogMapper {
    @SelectProvider(type=AccessLogSqlProvider.class, method="countByExample")
    long countByExample(AccessLogExample example);

    @DeleteProvider(type=AccessLogSqlProvider.class, method="deleteByExample")
    int deleteByExample(AccessLogExample example);

    @Delete({
        "delete from access_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into access_log (id, ip, ",
        "userId, status, ",
        "createTime)",
        "values (#{id,jdbcType=INTEGER}, #{ip,jdbcType=VARCHAR}, ",
        "#{userid,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, ",
        "#{createtime,jdbcType=TIMESTAMP})"
    })
    int insert(AccessLog record);

    @InsertProvider(type=AccessLogSqlProvider.class, method="insertSelective")
    int insertSelective(AccessLog record);

    @SelectProvider(type=AccessLogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ip", property="ip", jdbcType=JdbcType.VARCHAR),
        @Result(column="userId", property="userid", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<AccessLog> selectByExample(AccessLogExample example);

    @Select({
        "select",
        "id, ip, userId, status, createTime",
        "from access_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ip", property="ip", jdbcType=JdbcType.VARCHAR),
        @Result(column="userId", property="userid", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.TIMESTAMP)
    })
    AccessLog selectByPrimaryKey(Integer id);

    @UpdateProvider(type=AccessLogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AccessLog record, @Param("example") AccessLogExample example);

    @UpdateProvider(type=AccessLogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AccessLog record, @Param("example") AccessLogExample example);

    @UpdateProvider(type=AccessLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AccessLog record);

    @Update({
        "update access_log",
        "set ip = #{ip,jdbcType=VARCHAR},",
          "userId = #{userid,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "createTime = #{createtime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AccessLog record);
}