package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.dao.ExcelModelMapper;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.dao.GiftBagConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.ExcelModel;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.redis.RedisDao;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.result.CodeMsg;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.service.ImportService;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.util.Constant;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

@Service
public class ImportServiceImpl implements ImportService {
    @Autowired
    RedisDao redisDao;
    @Autowired
    ExcelModelMapper excelModelMapper;
    @Autowired
    GiftBagConfigDao giftBagConfigDao;

    private static final Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);

    /**
     * @Description:
     * 1.校验上传的文件类型及其对应的数据
     * 2.将通过（1）的数据转换为实体对象集合
     * 3.清空redis中的部分旧数据cleanCache()
     * 4.将对象集合传入cacheAndPublish()中
     * 5.封装本次数据校验结果并返回
     * @Param: [file]
     * @Retrun: com.ydc.excel_to_db.result.CodeMsg
     */
    @Override
    public CodeMsg verfiyExcel(MultipartFile file) {
        // 截取原始文件名里的类型名(最后一个“.”后的数据)
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        // 判断是否属于Excel表格的类型，不属于则直接返回“上传文件类型异常”(CodeMsg.FilE_ERROR)
        if (!".xls".equals(suffix) && !".xlsx".equals(suffix)) {
            return CodeMsg.FilE_ERROR;
        }
        ImportParams importParams = new ImportParams();
        // 对上传的数据进行处理，详情信息请看-com.ydc.excel_to_db.handler.ExcelModelHandler;
//        IExcelDataHandler<ExcelModel> handler = new ExcelModelHandler();
//        handler.setNeedHandlerFields(new String[] {"姓名"});
//        importParams.setDataHanlder(handler);
        // 是否需要验证
        importParams.setNeedVerfiy(true);
        try {
            ExcelImportResult<ExcelModel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), ExcelModel.class,
                    importParams);

//            int succSize = result.getList().size();
            List<ExcelModel> Modellist = result.getList();
            Map<String, Object> map = new HashMap<>();
            List<String> newlist = new ArrayList<>();
            for (ExcelModel excelModel : Modellist) {
                map.put("exchange_code",excelModel.getExchangeCode());
                List<GiftBagConfigEntity>  GiftBaglist= giftBagConfigDao.selectByMap(map);
                if(GiftBaglist.size()==0){
                    continue;
                }
                newlist.add(excelModel.getExchangeCode());
            }
            if(newlist.size()>0){
                String s = newlist.toString();
                String msg =  "兑换码为" + s + "已存在 ";
                return CodeMsg.userDefined(msg);
            }
            int failSize = result.getFailList().size();
            if(failSize>0) {
                List<ExcelModel> failList = result.getFailList();
                List<String> list = new ArrayList<>();
                for (ExcelModel excelModel : failList) {
                    list.add(excelModel.getExchangeCode());
                }
                /*HSSFWorkbook failWorkbook = (HSSFWorkbook)result.getFailWorkbook();
                InternalWorkbook internalWorkbook = failWorkbook.getInternalWorkbook();
                Class<? extends InternalWorkbook> aClass = internalWorkbook.getClass();
                Field sst = aClass.getDeclaredField("sst");
                Class<SSTRecord> type = (Class<SSTRecord>) sst.getType();
                Method method = type.getDeclaredMethod("countStrings", null);
                method.setAccessible(true);
                Object o = method.invoke(type.newInstance());
                UnicodeString string = internalWorkbook.getSSTString(12);*/
               String s = list.toString();
               String msg = failSize + "条数据" + "兑换码为" + s + "未通过格式校验 ";
                return CodeMsg.userDefined(msg);
            }

            // 当结果中通过校验的数据(result.getList())为空时
            // 直接返回“上传Excel表格格式有误<br>或者<br> 上传Excel数据为空”(CodeMsg.Excel_FORMAT_ERROR)
            if (result.getList().size() == 0 || result.getList().get(0) == null) {
                return CodeMsg.Excel_FORMAT_ERROR;
            }


            // 清空redis中的部分旧数据
            cleanCache();
            // 将参数result中的部分数据存入redis中，并把格式校验成功的数据发布至对应频道中
            cacheAndPublish(result);
            return  CodeMsg.SUCCESS;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        // 当以上过程中抛出异常时，返回"服务端异常，请您联系管理员查看服务器日志"(CodeMsg.SERVER_ERROR)
        return CodeMsg.SERVER_ERROR;
    }

    /**
     * @Description: 清空redis中的部分旧数据
     * @Param: []
     * @Retrun: void
     */
    @Override
    @Transactional
    public void cleanCache() {
        List<String> keyList = new ArrayList<>(10);
        keyList.add(Constant.failToDBKey);
        keyList.add(Constant.succSizeTempKey);
        keyList.add(Constant.failListKey);
        keyList.add(Constant.failSizeKey);
        keyList.add(Constant.succSizeKey);
        redisDao.cleanCache(keyList);
    }

    /**
     * @Description: 将参数result中的部分数据存入redis中，并把格式校验成功的数据发布至对应频道中
     * @Param: [result]
     * @Retrun: void
     */
    @Override
    @Transactional
    public void cacheAndPublish(ExcelImportResult<ExcelModel> result) {
        // 通过校验的数据
        List<ExcelModel> successList = result.getList();
        // 未通过校验的数据
        List<ExcelModel> failList = result.getFailList();
        int succSize = successList.size();
        int failSize = failList.size();
        // 将未通过校验的数据存入redis中
        redisDao.setStringKey(Constant.failListKey, failList);
        redisDao.setStringKey(Constant.failSizeKey, failSize);
        // 将通过校验的数据存入redis中
        redisDao.setStringKey(Constant.succSizeKey, succSize);
        // succSizeTempKey 用于判断消息队列中未消费数据的大小
        redisDao.setStringKey(Constant.succSizeTempKey, succSize);
//        redisDao.setStringKey(Constant.successListKey,successList);
        // 发布消息
        redisDao.publish(Constant.receiveList, successList);
    }

    /**
     * @Description: 将实体对象存入数据库中
     * @Param: [excelModel]
     * @Retrun: boolean 保存成功，返回true；保存失败，返回false；
     */
    @Override
    public boolean save(ExcelModel excelModel) {
        // INSERT IGNORE  存在重复主键时，返回0
        return excelModelMapper.insert(excelModel) > 0;
    }

    /**
     * @Description: 根据不同的失败类型的名称(failTypeName), 返回对应的数据
     * @Param: [failTypeName]
     * @Retrun: java.util.List<com.ydc.excel_to_db.domain.ExcelModel>
     */
    @Override
    public List<ExcelModel> getFailList(String failTypeName) {
        if ("format".equals(failTypeName)) {
            return redisDao.getStringListValue(Constant.failListKey, ExcelModel.class);
        } else if ("db".equals(failTypeName)) {
            return redisDao.getListValue(Constant.failToDBKey, ExcelModel.class);
        } else {
            return new ArrayList<ExcelModel>();
        }
    }
    /**
     * @Description: 根据key值，返回redis中对应的结果
     * @Param: [key]
     * @Retrun: long
     */
    @Override
    public long getTempSize(String key) {
        return redisDao.getStringValue(key, long.class);
    }

}
