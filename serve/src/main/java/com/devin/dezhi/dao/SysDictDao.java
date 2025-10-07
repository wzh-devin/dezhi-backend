package com.devin.dezhi.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.SysDict;
import com.devin.dezhi.mapper.SysDictMapper;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 2025/6/1 23:10.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class SysDictDao extends ServiceImpl<SysDictMapper, SysDict> {

    /**
     * 根据类型获取字典CODE.
     * @param type 字典类型
     * @return List
     */
    public List<String> getCodeByType(final String type) {
        return lambdaQuery()
                .eq(SysDict::getType, type)
                .list().stream()
                .map(SysDict::getCode)
                .toList();
    }
}
