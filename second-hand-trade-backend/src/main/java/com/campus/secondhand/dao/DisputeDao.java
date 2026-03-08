package com.campus.secondhand.dao;

import com.campus.secondhand.model.Dispute;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DisputeDao {
    @Select("SELECT id, order_id as orderId, user_id as userId, title, description, status, admin_id as adminId, resolution, created_at as createdAt, updated_at as updatedAt FROM disputes WHERE user_id = #{userId}")
    List<Dispute> listByUser(Integer userId);

    @Select("SELECT id, order_id as orderId, user_id as userId, title, description, status, admin_id as adminId, resolution, created_at as createdAt, updated_at as updatedAt FROM disputes WHERE order_id = #{orderId}")
    List<Dispute> listByOrder(Integer orderId);

    @Select("SELECT id, order_id as orderId, user_id as userId, title, description, status, admin_id as adminId, resolution, created_at as createdAt, updated_at as updatedAt FROM disputes WHERE id = #{id}")
    Dispute getById(Integer id);

    @Select("SELECT d.id, d.order_id as orderId, d.user_id as userId, d.title, d.description, d.status, d.admin_id as adminId, d.resolution, d.created_at as createdAt, d.updated_at as updatedAt, o.order_number as orderNumber, u.username as userName FROM disputes d LEFT JOIN orders o ON d.order_id = o.id LEFT JOIN users u ON d.user_id = u.id")
    List<Dispute> listAll();

    @Update("UPDATE disputes SET status = #{status}, admin_id = #{adminId}, resolution = #{resolution}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Dispute dispute);

    @Insert("INSERT INTO disputes (order_id, user_id, title, description, status) VALUES (#{orderId}, #{userId}, #{title}, #{description}, #{status})")
    void insert(Dispute dispute);
}
