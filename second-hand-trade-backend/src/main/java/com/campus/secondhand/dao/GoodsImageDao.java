package com.campus.secondhand.dao;

import com.campus.secondhand.model.GoodsImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsImageDao {
    @Select("SELECT id, goods_id as goodsId, image_data as imageData, created_at as createdAt FROM goods_images WHERE goods_id = #{goodsId}")
    List<GoodsImage> listByGoodsId(Integer goodsId);

    @Insert("INSERT INTO goods_images (goods_id, image_data, created_at) VALUES (#{goodsId}, #{imageData}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(GoodsImage goodsImage);

    @Delete("DELETE FROM goods_images WHERE id = #{id}")
    void delete(Integer id);
}
