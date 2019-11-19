package com.pinyougou.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;
import entity.Result;

@RestController
@RequestMapping("/brand")
public class BrandController {
	
	@Reference//  从zookeeper注册中心那     引用
	private BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAllBrand(){
		System.out.println("测试进入方法-----");
		List<TbBrand> list = brandService.findAll();
		return list;
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage(int page,int size) {
		PageResult findPage = brandService.findPage(page, size);
		return findPage;
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand) {
		try {
			brandService.add(brand);
			return new Result(true,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"添加失败");
		}
	}
	
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id) {
		return brandService.findOne(id);
	}
	
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand) {
		try {
			brandService.update(brand);
			return new Result(true,"修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"修改失败");
		}
	}
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			brandService.delete(ids);
			return new Result(true,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"删除失败");
		}
	}
	/**
	 * 分页搜索
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand brand,Integer page,Integer rows) {
		/*System.out.println("123");
		if(rows==null) {
			rows=10;
		}*/
		return brandService.findPage(brand, page, rows);
	}
	/**
	 * 商品拉列表
	 * @return
	 */
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		return brandService.selectOptionList();
	}
	
	
	public static void main(String[] args) {
		String s="[{\"id\":1,\"text\":\"联想\"},{\"id\":2,\"text\":\"华为\"},{\"id\":5,\"text\":\"OPPO\"},{\"id\":4,\"text\":\"小米\"},{\"id\":9,\"text\":\"苹果\"},{\"id\":8,\"text\":\"魅族\"},{\"id\":6,\"text\":\"360\"},{\"id\":10,\"text\":\"VIVO\"},{\"id\":3,\"text\":\"三星\"},{\"id\":7,\"text\":\"中兴\"},{\"id\":15,\"text\":\"飞利浦\"}]";
		Object parse = JSON.parse(s);
		System.out.println(parse);
		System.out.println(s);
	}
}
