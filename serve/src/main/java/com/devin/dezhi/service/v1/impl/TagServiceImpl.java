package com.devin.dezhi.service.v1.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.constant.ErrMsgConstant;
import com.devin.dezhi.dao.v1.TagDao;
import com.devin.dezhi.domain.v1.entity.ArticleTag;
import com.devin.dezhi.domain.v1.entity.Tag;
import com.devin.dezhi.domain.v1.vo.TagQueryVO;
import com.devin.dezhi.domain.v1.vo.TagVO;
import com.devin.dezhi.service.v1.TagService;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.BeanCopyUtils;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 2025/7/13 22:04.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    private final SnowFlake snowFlake;

    @Override
    public void saveTag(final TagVO tagVO) {
        // 检查标签名是否重复
        Tag checkNameDuplicate = tagDao.getTagByName(tagVO.getName());
        AssertUtil.isEmpty(checkNameDuplicate, ErrMsgConstant.TAG_NAME_DUPLICATE);

        // 新增标签
        Tag tag = BeanCopyUtils.copy(tagVO, Tag.class);
        tag.setId(snowFlake.nextId());
        tag.init();
        tagDao.save(tag);
    }

    @Override
    public void delTags(final List<Long> ids) {
        // 判断是否被引用
        List<ArticleTag> articleTags = tagDao.getArticleTagByIds(ids);
        AssertUtil.isEmpty(articleTags, ErrMsgConstant.TAG_HAS_QUOTE);
        // 删除标签
        tagDao.removeBatchByIds(ids);
    }

    @Override
    public Page<Tag> page(final TagQueryVO queryVO) {
        return tagDao.getPageList(queryVO);
    }

    @Override
    public void editTag(final TagVO tagVO) {
        tagDao.updateTag(tagVO);
    }
}
