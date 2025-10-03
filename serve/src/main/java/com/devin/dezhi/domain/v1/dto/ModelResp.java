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
    private String id;

    private Date created;

    private String model;

    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    private List<Choices> choices;
}
