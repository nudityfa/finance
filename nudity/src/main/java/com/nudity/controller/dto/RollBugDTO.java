package com.nudity.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/23 16:34
 */
@Data
public class RollBugDTO {

    @NotBlank(message = "code不能为空")
    private String code;
}
