package com.yongda.licai.system.web.controller.home;

import com.xiaoleilu.hutool.io.FileUtil;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.model.ProductDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 文件处理控制器
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2018/2/5-上午10:40
 */
@Controller
@RequestMapping(value = "/home/file")
@Api(tags = "999.文件处理接口", description = "999.文件处理接口")
public class HomeFileHandleController {

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    /**
     * 展示产品协议文件
     */
    @GetMapping(value = "/showProductProtocolFile", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value = "展示产品协议文件", httpMethod = "GET")
    public ResponseEntity<byte[]> showProductProtocolFile(@ApiParam(value = "产品ID", required = true)
                                                          @RequestParam String id) throws Exception {
        ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
        if (null == productDO) {
            throw new FileNotFoundException("找不到产品信息");
        }
        String path = productDO.getProtocolFile();
        File file = new File(path);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(FileUtil.readBytes(file),
                httpHeaders, HttpStatus.CREATED);
    }

}
