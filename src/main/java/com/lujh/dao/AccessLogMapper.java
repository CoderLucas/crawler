package com.lujh.dao;

import com.lujh.bean.AccessLog;
import com.lujh.bean.AccessLogExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AccessLogMapper {
    @SelectProvider(type = AccessLogSqlProvider.class, method = "groupByIP")
    @Results({
            @Result(column = "ip", property = "ip", jdbcType = JdbcType.VARCHAR),
    })
    List<String> groupByIP(AccessLogExample example);

    @SelectProvider(type = AccessLogSqlProvider.class, method = "groupByUserAgent")
    @Results({
            @Result(column = "userAgent", property = "userAgent", jdbcType = JdbcType.VARCHAR),
    })
    List<String> groupByUserAgent(AccessLogExample example);

    @SelectProvider(type = AccessLogSqlProvider.class, method = "groupByReferer")
    @Results({
            @Result(column = "referer", property = "referer", jdbcType = JdbcType.VARCHAR),
    })
    List<String> groupByReferer(AccessLogExample example);

    @SelectProvider(type = AccessLogSqlProvider.class, method = "countByExample")
    long countByExample(AccessLogExample example);

    @DeleteProvider(type = AccessLogSqlProvider.class, method = "deleteByExample")
    int deleteByExample(AccessLogExample example);

    @Delete({
            "delete from access_log",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into access_log (id, ip, ",
            "userAgent, status, ",
            "createTime, referer)",
            "values (#{id,jdbcType=INTEGER}, #{ip,jdbcType=VARCHAR}, ",
            "#{useragent,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
            "#{createtime,jdbcType=TIMESTAMP}, #{referer,jdbcType=VARCHAR})"
    })
    int insert(AccessLog record);

    @InsertProvider(type = AccessLogSqlProvider.class, method = "insertSelective")
    int insertSelective(AccessLog record);

    @SelectProvider(type = AccessLogSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "ip", property = "ip", jdbcType = JdbcType.VARCHAR),
            @Result(column = "userAgent", property = "useragent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "createTime", property = "createtime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "referer", property = "referer", jdbcType = JdbcType.VARCHAR)
    })
    List<AccessLog> selectByExample(AccessLogExample example);

    @Select({
            "select",
            "id, ip, userAgent, status, createTime, referer",
            "from access_log",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "ip", property = "ip", jdbcType = JdbcType.VARCHAR),
            @Result(column = "userAgent", property = "useragent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "createTime", property = "createtime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "referer", property = "referer", jdbcType = JdbcType.VARCHAR)
    })
    AccessLog selectByPrimaryKey(Integer id);

    @UpdateProvider(type = AccessLogSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AccessLog record, @Param("example") AccessLogExample example);

    @UpdateProvider(type = AccessLogSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") AccessLog record, @Param("example") AccessLogExample example);

    @UpdateProvider(type = AccessLogSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AccessLog record);

    @Update({
            "update access_log",
            "set ip = #{ip,jdbcType=VARCHAR},",
            "userAgent = #{useragent,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=INTEGER},",
            "createTime = #{createtime,jdbcType=TIMESTAMP},",
            "referer = #{referer,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AccessLog record);
}