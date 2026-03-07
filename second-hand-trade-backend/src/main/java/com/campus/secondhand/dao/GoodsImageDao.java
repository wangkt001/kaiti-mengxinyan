package com.campus.secondhand.dao;

import com.campus.secondhand.model.GoodsImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsImageDao {
    @Select("SELECT * FROM goods_images WHERE goods_id = #{goodsId}")
    List<GoodsImage> listByGoodsId(Integer goodsId);

    @Insert("INSERT INTO goods_images (goods_id, image_path, created_at) VALUES (#{goodsId}, #{imagePath}, #{createdAt})")
    void insert(GoodsImage goodsImage);

    @Delete("DELETE FROM goods_images WHERE id = #{id}")
    void delete(Integer id);
}
