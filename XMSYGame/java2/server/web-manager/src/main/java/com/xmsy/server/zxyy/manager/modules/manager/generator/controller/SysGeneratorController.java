package com.xmsy.server.zxyy.manager.modules.manager.generator.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.generator.service.SysGeneratorService;
import com.xmsy.server.zxyy.manager.modules.manager.generator.utils.FileUtils;
import com.xmsy.server.zxyy.manager.modules.manager.generator.utils.GenUtils;
import com.xmsy.server.zxyy.manager.modules.manager.generator.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.generator.utils.ZipUtil;

/**
 * 代码生成器
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(String tables, HttpServletResponse response) throws IOException {
		String[] tableArray = tables.split(",");
		byte[] data = sysGeneratorService.generatorCode(tableArray);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"manager.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
		Configuration config = GenUtils.getConfig();
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean isSuccess = true;
				// 浏览器下载文件存储的路径
				String zipFileName = config.getString("downloadPath") == null ? "D:\\Downloads\\manager.zip"
						: config.getString("downloadPath");
				// 文件需要解压到的目的路径
				String dstPath = config.getString("destinetion") == null
						? "D:\\project\\server\\zxyy-webhome\\src"
						: config.getString("destinetion");
				String sqlDestinetionPath = config.getString("sqlFilePath") == null
						? "D:\\project\\server\\zxyy-webhome\\src\\main\\resources\\sql"
						: config.getString("sqlFilePath");
				File file = null;
				while (isSuccess) {
					file = new File(zipFileName);
					if (file.exists()) {
						ZipUtil.upZipFile(zipFileName, dstPath);
						sleep(2000L);
						file.delete();
						copySql(tableArray, dstPath, sqlDestinetionPath);
						isSuccess = false;
					} else {
						sleep(2000L);
					}

				}
			}
		}).start();

	}

	// sqlPath:sql源路径, destDir:目标路径
	private void copySql(String[] tableArray, String sqlPath, String destDir) {
		String path = "";
		for (String table : tableArray) {
			table = table.replaceAll("_", "") + "_menu";
			System.out.println(table);
			path = sqlPath + "\\" + table + ".sql";
			FileUtils.cutGeneralFile(path, destDir);
		}

	}
	private void sleep(Long seconds) {
		try {
			System.out.println("文件不存在等" + seconds + "秒");
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
		}
	}
}
