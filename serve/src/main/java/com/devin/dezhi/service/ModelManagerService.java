package com.devin.dezhi.service;

import com.devin.dezhi.domain.vo.ai.ModelManagerSaveVO;
import com.devin.dezhi.domain.vo.ai.ModelManagerVO;
import java.util.List;

/**
 * 2025/8/24 20:52.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface ModelManagerService {

    /**
     * 保存模型.
     *
     * @param modelManagerSaveVO 模型保存VO
     */
    void saveModel(ModelManagerSaveVO modelManagerSaveVO);

    /**
     * 删除模型.
     * @param idList 模型ID列表
     */
    void delModel(List<Long> idList);

    /**
     * 获取模型列表.
     * @return 模型列表
     */
    List<ModelManagerVO> getModelList();
}
