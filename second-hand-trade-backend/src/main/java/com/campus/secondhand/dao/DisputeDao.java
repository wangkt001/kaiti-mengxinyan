package com.campus.secondhand.dao;

import com.campus.secondhand.model.Dispute;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DisputeDao {
    @Select("SELECT d.id, d.order_id as orderId, d.user_id as userId, d.title, d.description, d.status, d.admin_id as adminId, d.resolution, d.created_at as createdAt, d.updated_at as updatedAt, o.order_number as orderNumber FROM disputes d LEFT JOIN orders o ON d.order_id = o.id WHERE d.user_id = #{userId} ORDER BY d.created_at DESC")
    List<Dispute> listByUser(Integer userId);

    @Select("SELECT d.id, d.order_id as orderId, d.user_id as userId, d.title, d.description, d.status, d.admin_id as adminId, d.resolution, d.created_at as createdAt, d.updated_at as updatedAt, o.order_number as orderNumber, u.username as userName FROM disputes d LEFT JOIN orders o ON d.order_id = o.id LEFT JOIN users u ON d.user_id = u.id WHERE d.order_id = #{orderId} ORDER BY d.created_at DESC")
    List<Dispute> listByOrder(Integer orderId);

    @Select("SELECT d.id, d.order_id as orderId, d.user_id as userId, d.title, d.description, d.status, d.admin_id as adminId, d.resolution, d.created_at as createdAt, d.updated_at as updatedAt, o.order_number as orderNumber, u.username as userName FROM disputes d LEFT JOIN orders o ON d.order_id = o.id LEFT JOIN users u ON d.user_id = u.id WHERE d.id = #{id}")
    Dispute getById(Integer id);

    @Select("SELECT d.id, d.order_id as orderId, d.user_id as userId, d.title, d.description, d.status, d.admin_id as adminId, d.resolution, d.created_at as createdAt, d.updated_at as updatedAt, o.order_number as orderNumber, u.username as userName FROM disputes d LEFT JOIN orders o ON d.order_id = o.id LEFT JOIN users u ON d.user_id = u.id ORDER BY d.created_at DESC")
    List<Dispute> listAll();

    @Update("UPDATE disputes SET status = #{status}, admin_id = #{adminId}, resolution = #{resolution}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Dispute dispute);

    @Insert("INSERT INTO disputes (order_id, user_id, title, description, status, created_at, updated_at) VALUES (#{orderId}, #{userId}, #{title}, #{description}, #{status}, #{createdAt}, #{updatedAt})")
    void insert(Dispute dispute);
}
