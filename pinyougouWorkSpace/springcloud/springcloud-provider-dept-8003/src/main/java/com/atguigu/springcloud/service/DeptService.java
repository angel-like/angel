package com.atguigu.springcloud.service;

import java.util.List;

import com.atguigu.springcloud.entity.Dept;

public interface DeptService {
	 public boolean add(Dept dept);
	 
	  public Dept get(Long id);
	 
	  public List<Dept> list();
}
