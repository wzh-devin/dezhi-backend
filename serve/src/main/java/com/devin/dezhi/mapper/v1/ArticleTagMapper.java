package com.devin.dezhi.mapper.v1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devin.dezhi.domain.v1.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import java.util.Collection;

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
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    /**
     * 批量插入.
     * @param articleTagList 文章标签列表
     * @return 插入数量
     */
    int insertBatchSomeColumn(Collection<ArticleTag> articleTagList);
}
