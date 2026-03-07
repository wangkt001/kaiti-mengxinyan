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

    @Select("SELECT * FROM evaluations WHERE evaluated_id = #{evaluatedId}")
    List<Evaluation> listByEvaluated(Integer evaluatedId);

    @Insert("INSERT INTO evaluations (order_id, evaluator_id, evaluated_id, rating, comment, created_at) VALUES (#{orderId}, #{evaluatorId}, #{evaluatedId}, #{rating}, #{comment}, #{createdAt})")
    void insert(Evaluation evaluation);
}
