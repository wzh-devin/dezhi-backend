package com.devin.dezhi.domain.v1.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

/**
 * 2025/4/26 16:52.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "登录信息响应")
public class LoginResp {

    @SchemaProperty(name = "token信息")
    private String token;

}
