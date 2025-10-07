package com.devin.dezhi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.domain.entity.Tag;
import com.devin.dezhi.domain.vo.TagQueryVO;
import com.devin.dezhi.domain.vo.TagVO;
import java.util.List;

/**
 * 2025/7/13 22:03.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface TagService {

    /**
     * 保存标签.
     *
     * @param tagVO 标签信息
     */
    void saveTag(TagVO tagVO);

    /**
     * 删除标签.
     *
     * @param ids 标签ids
     */
    void delTags(List<Long> ids);

    /**
     * 分页查询标签.
     *
     * @param queryVO 查询参数
     * @return Page
     */
    Page<Tag> page(TagQueryVO queryVO);

    /**
     * 编辑标签.
     *
     * @param tagVO 标签信息
     */
    void editTag(TagVO tagVO);

    /**
     * 获取标签可选项.
     *
     * @return List
     */
    List<TagVO> getTagOptional();
}
