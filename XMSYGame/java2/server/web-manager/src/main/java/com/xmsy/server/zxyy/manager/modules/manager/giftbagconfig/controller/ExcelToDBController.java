package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.ExcelModel;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.result.CodeMsg;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.result.Result;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.service.ImportService;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.util.Constant;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

/**
 * @Description: 控制层
 * @Author: 海绵宝宝
 * @Date: Created in  2019-11-1
 */
@RestController
@RequestMapping("excel/excel")
public class ExcelToDBController {
    @Autowired
    ImportService importService;

    private static final Logger log = LoggerFactory.getLogger(ExcelToDBController.class);

    /**
     * @Description: 下载模板
     * @Param: [file]
     * @Retrun: com.ydc.excel_to_db.result.Result<com.ydc.excel_to_db.result.CodeMsg>
     */
    @SysLog("导出新增兑换码模板")
    @RequestMapping("/ExportMould")
    @ResponseBody
    public void ExportMould(HttpServletResponse response) {
        // 设置excel的文件名称
//        String fileName = "新增兑换码模板.xlsx";
        try {
            // 设置响应输出的头类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=新增兑换码模板.xlsx");
            // =========easypoi部分
            ExportParams exportParams = new ExportParams();
            // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExcelModel.class, new ArrayList<>());
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @Description: 异步接收上传的Excel文件并传递至Service层
     * @Param: [file]
     * @Retrun: com.ydc.excel_to_db.result.Result<com.ydc.excel_to_db.result.CodeMsg>
     */
    @PostMapping("/doImport")
    @ResponseBody
    public Result<CodeMsg> doImport(@RequestParam("file") MultipartFile file) {
        CodeMsg codeMsg = importService.verfiyExcel(file);
        // 返回封装好的结果集
        return Result.success(codeMsg);
    }

    /**
     * @Description: 异步获取当前消息队列中未被消费的队列大小
     * @Param: []
     * @Retrun: com.ydc.excel_to_db.result.Result<java.lang.Long>
     */
    @GetMapping("/getUndoSize")
    @ResponseBody
    public Result<Long> getUndoSize() {
        return Result.success(importService.getTempSize(Constant.succSizeTempKey));
    }

//    /**
//     * @Description: 跳转至同步结果页面
//     * @Param: []
//     * @Retrun: java.lang.String
//     */
//    @GetMapping("/toResult")
//    public String toResult() {
//        return "importResult";
//    }

//    /**
//     * @Description: 异步获取同步结果页面中饼状图所需的数据
//     * @Param: []
//     * @Retrun: com.ydc.excel_to_db.result.Result<com.ydc.excel_to_db.vo.ExcelModelVo>
//     */
//    @GetMapping("/getResultData")
//    @ResponseBody
//    public Result<ExcelModelVo> getResultData() {
//        ExcelModelVo resultData = importService.getResultData();
//        return Result.success(resultData);
//    }
}
