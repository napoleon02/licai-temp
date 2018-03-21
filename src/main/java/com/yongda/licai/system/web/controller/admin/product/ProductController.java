package com.yongda.licai.system.web.controller.admin.product;

import com.github.pagehelper.Page;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yongda.licai.system.biz.ProductServiece;
import com.yongda.licai.system.dal.mapper.ProductDOMapper;
import com.yongda.licai.system.dal.model.AdminDO;
import com.yongda.licai.system.dal.model.ProductDO;
import com.yongda.licai.system.dal.params.BaseProductParams;
import com.yongda.licai.system.web.controller.BaseController;
import com.yongda.licai.system.web.response.ResponseWraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: sjj
 * @Description: 管理员后台产品管理CONTROLLER
 * @Date: 2018/2/05 13:08
 * @Modified by: napoleon
 * @Version: 1.0.0
 */
@Controller
@RequestMapping(value = "/admin/product")
public class ProductController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private static final String DOUHAO = ".";
    private static final String SUFFIX = ".pdf";
    private static final String CALLMSG = "上传文件只支持PDF格式!";

    @Resource(name = "productService")
    private ProductServiece productService;

    @Resource(name = "productDOMapper")
    private ProductDOMapper productDOMapper;

    @Value("${product.protocol.path}")
    private String protocolPath;

    /**
     * 跳转产品新增页面
     *
     * @return
     */
    @GetMapping(value = "/add")
    public String add() {
        return "/admin/product/add.html";
    }

    /**
     * 保存产品信息
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseWraper doAdd(ProductDO productDO,
                                @RequestParam("file") MultipartFile file) {
        try {
            /*判断文件后缀名合法性*/
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(DOUHAO));
            if (!suffix.equalsIgnoreCase(SUFFIX)) {
                return ResponseWraper.other(CALLMSG);
            }
            /*生成新的文件名称*/
            String uuid = RandomUtil.randomUUID().replace("-", "");
            String newFileName = uuid + suffix;
            /*处理文件保存路径*/
            protocolPath = StrUtil.appendIfMissing(protocolPath, "/", "/");
            String localPath = protocolPath + newFileName;
            File desFile = new File(localPath);
            if (!desFile.getParentFile().exists()) {
                desFile.getParentFile().mkdirs();
            }
            file.transferTo(desFile);
            /*保存产品*/
            productDO.setProtocolFile(localPath);
            AdminDO loginBean = getAdmin();
            productDO.setCreatePersonId(loginBean.getId());
            productDO.setCreatePersonName(loginBean.getName());
            productDO.setCreateTime(new Date());
            productDO.setUpdatePersonId(loginBean.getId());
            productDO.setUpdatePersonName(loginBean.getName());
            productDO.setUpdateTime(new Date());
            productService.addProduct(productDO);

            return ResponseWraper.ok().setData("产品保存成功");
        } catch (Exception e) {
            log.error("产品保存异常:", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 跳转到产品查询页
     */
    @GetMapping(value = "/queryProduct")
    public String list() {
        return "/admin/product/queryProduct.html";
    }

    /**
     * 跳转产品上下架页面
     *
     * @return
     */
    @GetMapping(value = "/queryUpOrDown")
    public String listUpOrDown() {
        return "/admin/product/queryUpOrDown.html";
    }

    /**
     * 详情
     *
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("queryProductDetailsById")
    public String queryOrderDetailsById(String id, ModelMap modelMap) {
        modelMap.put("detail", productService.queryProductByIdM(id));
        return "/admin/product/productDetail.html";
    }

    /**
     * 产品上下架
     *
     * @param id
     * @param status
     * @return
     */
    @GetMapping(value = "/queryUpOrDownConfirm")
    @ResponseBody
    public ResponseWraper productUpOrDown(String id, String status) {
        try {
            ProductDO productDO = new ProductDO();
            productDO.setId(id);
            productDO.setOnlineStatus(status);
            productDO.setUpdatePersonId(getAdmin().getId());
            productDO.setUpdatePersonName(getAdmin().getName());
            productDO.setUpdateTime(new Date());
            productService.updateOnlineStatusUpOrDown(productDO);
            return ResponseWraper.ok();
        } catch (Exception e) {
            log.error("产品上下架操作异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 根据条件查询产品或者全查
     */
    @PostMapping(value = "/queryProduct")
    @ResponseBody
    public ResponseWraper queryProduct(BaseProductParams baseProductParams,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "rows", defaultValue = "20") Integer rows) {
        try {
            Map<String, Object> data = new HashMap<>();
            Page<ProductDO> result = productService.queryAllProduct(baseProductParams, page, rows);
            data.put("rows", result.getResult());
            data.put("total", result.getTotal());
            return ResponseWraper.ok().setData(data);
        } catch (Exception e) {
            log.error("查询产品列表异常：", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 下载产品协议文件
     */
    @GetMapping(value = "/downloadProductProtocolFile")
    public ResponseEntity<byte[]> showProductProtocolFile(@RequestParam String id) throws Exception {
        ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
        if (null == productDO) {
            throw new FileNotFoundException("找不到产品信息");
        }
        String path = productDO.getProtocolFile();
        File file = new File(path);
        HttpHeaders httpHeaders = new HttpHeaders();
        String fileName = productDO.getProtocolName() + ".pdf";
        httpHeaders.setContentDispositionFormData("attachment", new String(StrUtil.bytes(fileName, "UTF-8"), "ISO-8859-1"));
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtil.readBytes(file),
                httpHeaders, HttpStatus.CREATED);
    }

    /**
     * @Author:nepoleon
     * @Descriptions:通过ID删除产品
     * @Param:id 产品ID
     * @Date: 2018/2/5 20:31
     */
    @GetMapping("/deleteProductById")
    @ResponseBody
    public ResponseWraper deleteProductById(String id) {
        try {
            Map map = productService.deleteProductById(id);
            return ResponseWraper.ok().setData(map);
        } catch (Exception e) {
            log.error("通过ID删除产品异常：", e);
            return ResponseWraper.error();
        }

    }

    /**
     * @Author:nepoleon
     * @Descriptions: 根据需要退款的产品ID返回还款视图
     * @Param:id 需要退款的产品ID
     * @Date: 2018/2/7 14:32
     */
    @GetMapping("/repay")
    public String productRepayById(String id, ModelMap modelMap) {
        modelMap.put("productId", id);
        return "/admin/product/repay.html";
    }

    /**
     * @Author:nepoleon
     * @Descriptions:通过ID对产品进行还款操作
     * @Param:id 产品ID
     * @Date: 2018/2/5 20:31
     */
    @PostMapping("/productRepayById")
    @ResponseBody
    public ResponseWraper productRepayById(ProductDO dto) {
        try {
            dto.setUpdateTime(new Date());
            dto.setUpdatePersonId(getAdmin().getId());
            dto.setUpdatePersonName(getAdmin().getName());
            Map result = productService.productRepayById(dto);
            return ResponseWraper.ok().setData(result);
        } catch (Exception e) {
            log.error("通过ID对产品进行还款操作:{}", e);
            return ResponseWraper.error();
        }
    }

    /**
     * 修改产品
     */
    @GetMapping(value = "/editProduct")
    public String editProduct(@RequestParam String id, ModelMap modelMap) {
        ProductDO productDO = productService.queryProductByIdM(id);
        modelMap.put("bean", productDO);
        return "/admin/product/edit.html";
    }

    /**
     * 处理修改产品逻辑
     */
    @PostMapping(value = "/editProduct")
    @ResponseBody
    public ResponseWraper doEditProduct(ProductDO productDO, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {

            if (null != file) {
                //判断文件后缀名合法性
                String fileName = file.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(DOUHAO));
                if (!suffix.equalsIgnoreCase(SUFFIX)) {
                    return ResponseWraper.other(CALLMSG);
                }
                //生成新的文件名称
                String uuid = RandomUtil.randomUUID().replace("-", "");
                String newFileName = uuid + suffix;
                //处理文件保存路径
                protocolPath = StrUtil.appendIfMissing(protocolPath, "/", "/");
                String localPath = protocolPath + newFileName;
                File desFile = new File(localPath);
                if (!desFile.getParentFile().exists()) {
                    desFile.getParentFile().mkdirs();
                }
                file.transferTo(desFile);
                productDO.setProtocolFile(localPath);
            }

            /*保存产品*/
            AdminDO loginBean = getAdmin();
            productDO.setUpdatePersonId(loginBean.getId());
            productDO.setUpdatePersonName(loginBean.getName());
            productDO.setUpdateTime(new Date());
            productDOMapper.updateByPrimaryKeySelective(productDO);
            return ResponseWraper.ok().setData("产品保存成功");
        } catch (Exception e) {
            log.error("编辑产品异常:", e);
            return ResponseWraper.error();
        }
    }

}
