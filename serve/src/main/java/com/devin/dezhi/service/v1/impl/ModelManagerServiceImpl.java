package com.devin.dezhi.service.v1.impl;

import com.devin.dezhi.ai.strategy.ModelStrategy;
import com.devin.dezhi.ai.strategy.ModelStrategyFactory;
import com.devin.dezhi.constant.ModelRoleConstant;
import com.devin.dezhi.dao.v1.ModelManagerDao;
import com.devin.dezhi.domain.v1.dto.ModelDTO;
import com.devin.dezhi.domain.v1.entity.ModelManager;
import com.devin.dezhi.domain.v1.vo.ai.ModelManagerSaveVO;
import com.devin.dezhi.domain.v1.vo.ai.ModelManagerVO;
import com.devin.dezhi.enums.ai.ModelProvidersEnum;
import com.devin.dezhi.model.ModelReqBody;
import com.devin.dezhi.service.v1.ModelManagerService;
import com.devin.dezhi.utils.BeanCopyUtils;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2025/6/1 23:06.
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
public class ModelManagerServiceImpl implements ModelManagerService {

    private final ModelManagerDao modelManagerDao;

    private final ModelStrategyFactory modelStrategyFactory;

    private final SnowFlake snowFlake;

    @Override
    public void saveModel(final ModelManagerSaveVO modelManagerSaveVO) {
        // 测试模型连通性
        ModelStrategy strategy = modelStrategyFactory.getModelStrategy(modelManagerSaveVO.getProvider());
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setProvider(modelManagerSaveVO.getProvider());
        modelDTO.setApiKey(modelManagerSaveVO.getApiKey());
        modelDTO.setBaseUrl(modelManagerSaveVO.getBaseUrl());
        ModelReqBody modelReqBody = ModelReqBody.builder()
                .model(modelManagerSaveVO.getModel())
                .stream(false)
                .message(ModelRoleConstant.USER, "hi")
                .build();
        modelDTO.setModelReqBody(modelReqBody);
        strategy.checkConnectModel(modelDTO);
        // 保存模型信息
        ModelManager modelManager = BeanCopyUtils.copy(modelManagerSaveVO, ModelManager.class);
        modelManager.setProvider(modelManagerSaveVO.getProvider().name());
        modelManager.setId(snowFlake.nextId());
        modelManager.init();
        modelManagerDao.save(modelManager);
    }

    @Override
    public void delModel(final List<Long> idList) {
        modelManagerDao.removeBatchByIds(idList);
    }

    @Override
    public List<ModelManagerVO> getModelList() {
        List<ModelManager> modelManagerList = modelManagerDao.list();
        Map<String, List<ModelManager>> modelMap = modelManagerList.stream()
                .collect(Collectors.groupingBy(ModelManager::getProvider, Collectors.toList()));
        return modelMap.entrySet().stream()
                .map(entry -> {
                    ModelManagerVO modelManagerVO = new ModelManagerVO();
                    modelManagerVO.setProvider(ModelProvidersEnum.valueOf(entry.getKey()));
                    modelManagerVO.setModels(entry.getValue().stream().map(modelManager -> {
                        ModelManagerVO.Model model = new ModelManagerVO.Model();
                        model.setId(modelManager.getId());
                        model.setModel(modelManager.getModel());
                        model.setBaseUrl(modelManager.getBaseUrl());
                        return model;
                    }).toList());
                    return modelManagerVO;
                }).toList();
    }
}
