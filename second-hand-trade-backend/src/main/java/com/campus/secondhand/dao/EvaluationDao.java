package com.campus.secondhand.dao;

import com.campus.secondhand.model.Evaluation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EvaluationDao {
    @Select("SELECT * FROM evaluations WHERE order_id = #{orderId}")
    List<Evaluation> listByOrder(Integer orderId);

    @Select("SELECT e.id, e.order_id AS orderId, e.evaluator_id AS evaluatorId, e.evaluated_id AS evaluatedId, e.rating, e.comment, e.created_at AS createdAt, u.username AS evaluatorName, g.name AS goodsName FROM evaluations e LEFT JOIN users u ON e.evaluator_id = u.id LEFT JOIN orders o ON e.order_id = o.id LEFT JOIN goods g ON o.goods_id = g.id WHERE e.evaluated_id = #{userId} OR e.evaluator_id = #{userId}")
    List<Evaluation> listByEvaluated(Integer userId);

    @Insert("INSERT INTO evaluations (order_id, evaluator_id, evaluated_id, rating, comment, created_at) VALUES (#{orderId}, #{evaluatorId}, #{evaluatedId}, #{rating}, #{comment}, #{createdAt})")
    void insert(Evaluation evaluation);
}
