package com.devin.dezhi.domain.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 2025/10/3 20:12.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class ModelResp {

    /**
     * 模型返回结果id.
     */
    private String id;

    /**
     * 模型返回结果创建时间.
     */
    private Date created;

    /**
     * 模型返回结果模型.
     */
    private String model;

    /**
     * 模型返回结果系统指纹.
     */
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    /**
     * 模型返回结果.
     */
    private List<Choices> choices;
}
