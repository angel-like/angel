package com.ljx.redis.controller;

import com.ljx.redis.entity.Product;
import com.ljx.redis.service.JHSTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: JHSProductController
 * Package: com.ljx.redis.controller
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/3 6:54
 * @Version 1.0
 */
@Api(tags = "聚划算商品列表接口")
@Slf4j
@RestController
@RequestMapping("/product")
public class JHSProductController {

    @Autowired
    private JHSTaskService jhsTaskService;

    /**
     * 高并发 + 定时任务 + 分页任务
     * 不能直接使用MySQL， 会被打爆！
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/find")
    @ApiOperation("按照分页和每页显示容量，点击查看")
    public List<Product> find(int page, int size) {
        return jhsTaskService.find(page, size);
    }


    /**
     * 差异化缓存 避免隐患2，确保查不到也要很快返回回来
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findAB")
    @ApiOperation("防止热点key突然失效，AB双缓存架构")
    public List<Product> findAB(int page, int size) {
        return jhsTaskService.findAB(page, size);
    }
}
