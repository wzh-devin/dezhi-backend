package com.devin.dezhi.common.configuration;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import java.util.List;

/**
 * 2025/6/4 13:18.
 *
 * <p>
 * 注入批量新增方法
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class CommonSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(final Class<?> mapperClass, final TableInfo tableInfo) {
        // super.getMethodList() 保留 Mybatis Plus 自带的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 添加自定义方法：批量插入，方法名为 insertBatchSomeColumn
        // bean mapper中的方法名也是insertBatchSomeColumn 须和内部定义好的方法名保持一致。
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}