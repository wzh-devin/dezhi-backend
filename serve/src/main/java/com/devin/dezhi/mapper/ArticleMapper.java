package com.devin.dezhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devin.dezhi.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 2025/6/1 23:06.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 插入文章以及向量.
     * @param article 文章
     * @return 插入数量
     */
    int insertWithVector(@Param("article") Article article);
}
