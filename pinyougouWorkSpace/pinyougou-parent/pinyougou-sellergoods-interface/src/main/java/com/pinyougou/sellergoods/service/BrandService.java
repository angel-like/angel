package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbBrand;//有pom.xml依赖里  可以找到这个实体类
import entity.PageResult;//有pom.xml依赖里  可以找到这个实体类

/**
 * 品牌接口
 * @author Administrator
 *
 */
public interface BrandService {
	
	public List<TbBrand> findAll();
	/**
	 * 品牌分页
	 * @param pageNum 当前页
	 * @param pageSize 页面大小
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	/**
	 * 增加
	 * @param brand
	 */
	public void add(TbBrand brand);
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public TbBrand findOne(Long id);
	/**
	 * 修改
	 * @param brand
	 */
	public void update(TbBrand brand);
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(Long[] ids);
	/**
	 * 根据条件 品牌分页
	 * @param brand
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult findPage(TbBrand brand,Integer pageNum,Integer pageSize);

	/**
	 * 返回下拉列表数据
	 * @return
	 */
	public List<Map> selectOptionList();
}
