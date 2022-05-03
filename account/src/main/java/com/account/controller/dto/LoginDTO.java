package com.account.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/2 23:44
 */
@Data
public class LoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
