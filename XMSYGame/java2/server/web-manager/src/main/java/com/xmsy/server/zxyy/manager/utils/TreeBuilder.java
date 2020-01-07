package com.xmsy.server.zxyy.manager.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xmsy.common.bean.message.SysConfigMessage;

/**
 * .构建属性结构（关联上下级关系）
 * 
 * @author aleng
 *
 */
public class TreeBuilder {

	List<SysConfigMessage> nodes = new ArrayList<>();

	public String buildTree(List<SysConfigMessage> nodes) {
		TreeBuilder treeBuilder = new TreeBuilder(nodes);
		return treeBuilder.buildJSONTree();
	}

	public TreeBuilder() {
	}

	public TreeBuilder(List<SysConfigMessage> nodes) {
		super();
		this.nodes = nodes;
	}

	// 构建JSON树形结构
	public String buildJSONTree() {
		List<SysConfigMessage> nodeTree = buildTree();
		return JSON.toJSONString(nodeTree);
	}

	// 构建树形结构
	public List<SysConfigMessage> buildTree() {
		List<SysConfigMessage> treeNodes = new ArrayList<>();
		List<SysConfigMessage> rootNodes = getRootNodes();
		for (SysConfigMessage rootNode : rootNodes) {
			buildChildNodes(rootNode);
			treeNodes.add(rootNode);
		}
		return treeNodes;
	}

	// 递归子节点
	public void buildChildNodes(SysConfigMessage node) {
		List<SysConfigMessage> children = getChildNodes(node);
		if (!children.isEmpty()) {
			for (SysConfigMessage child : children) {
				buildChildNodes(child);
			}
			node.setChildren(children);
		}
	}

	// 获取父节点下所有的子节点
	public List<SysConfigMessage> getChildNodes(SysConfigMessage pnode) {
		List<SysConfigMessage> childNodes = new ArrayList<>();
		for (SysConfigMessage config : nodes) {
			if (pnode.getId().equals(config.getParent())) {
				childNodes.add(config);
			}
		}
		return childNodes;
	}

	// 判断是否为根节点
	public boolean rootNode(SysConfigMessage node) {
		boolean isRootNode = true;
		for (SysConfigMessage commonConfig : nodes) {
			if (node.getParent().equals(commonConfig.getId())) {
				isRootNode = false;
				break;
			}
		}
		return isRootNode;
	}

	// 获取集合中所有的根节点
	public List<SysConfigMessage> getRootNodes() {
		List<SysConfigMessage> rootNodes = new ArrayList<>();
		for (SysConfigMessage commonConfig : nodes) {
			if (rootNode(commonConfig)) {
				rootNodes.add(commonConfig);
			}
		}
		return rootNodes;
	}
}
