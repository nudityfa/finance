package com.nudity.controller;

import com.adminUtil.commonUtil.ResponseBean;
import com.nudity.controller.dto.RollBugDTO;
import com.nudity.service.JiJinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/23 16:32
 */
@Api(value = "基金模块",tags = "基金模块")
@RestController
@RequestMapping("/jiJin/")
public class JiJinController {

    @Autowired
    private JiJinService jiJinService;

    @ApiOperation(value = "手动爬取top100")
    @PostMapping("doRollTop100")
    public ResponseBean doRollTop100(){
        jiJinService.doRollTop100();
        return ResponseBean.success();
    }


    @ApiOperation(value = "爬取某个基金detail")
    @PostMapping("rollBug")
    public ResponseBean rollBug(@RequestBody RollBugDTO dto){
        jiJinService.rollBug(dto);
        return ResponseBean.success();
    }

    @ApiOperation(value = "top10")
    @PostMapping("top10")
    public ResponseBean top10(){
        return ResponseBean.success(jiJinService.top10());
    }
}
