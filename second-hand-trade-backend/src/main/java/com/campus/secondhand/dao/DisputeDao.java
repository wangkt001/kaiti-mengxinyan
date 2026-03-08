package com.campus.secondhand.dao;

import com.campus.secondhand.model.Dispute;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DisputeDao {
    @Select("SELECT * FROM disputes WHERE user_id = #{userId}")
    List<Dispute> listByUser(Integer userId);

    @Select("SELECT * FROM disputes WHERE order_id = #{orderId}")
    List<Dispute> listByOrder(Integer orderId);

    @Select("SELECT * FROM disputes WHERE id = #{id}")
    Dispute getById(Integer id);

    @Select("SELECT * FROM disputes")
    List<Dispute> listAll();

    @Update("UPDATE disputes SET status = #{status}, admin_id = #{adminId}, resolution = #{resolution}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Dispute dispute);

    @Insert("INSERT INTO disputes (order_id, user_id, title, description, status, created_at, updated_at) VALUES (#{orderId}, #{userId}, #{title}, #{description}, #{status}, #{createdAt}, #{updatedAt})")
    void insert(Dispute dispute);
}
