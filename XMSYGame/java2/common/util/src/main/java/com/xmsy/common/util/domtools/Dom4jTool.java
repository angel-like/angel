package com.xmsy.common.util.domtools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * .dom节点操作
 *
 * @author chenjisi
 * @date 2017年1月2日 下午11:54:34
 * @Description: TODO
 */
public class Dom4jTool {

	private static Logger logger = LoggerFactory.getLogger(Dom4jTool.class);

	public static Document xmlString2Doc(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (Exception e) {
			logger.error("【Dom4jTool->xmlString2Doc】 xml:{} ", xml, e);
			return null;
		}
		return doc;
	}

	public static Document xmlFile2Doc(File xmlFile) {

		if (xmlFile == null) {
			logger.info("【Dom4jTool->xmlFile2Doc】 xmlFile is null");
			return null;
		}
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(xmlFile);
		} catch (Exception e) {
			logger.error("【Dom4jTool->xmlFile2Doc】 xmlFile:{} ", xmlFile, e);
		}
		return doc;
	}

	public static Document xmlFile2Doc(String xmlFilePath) {

		if (xmlFilePath == null) {
			logger.info("【Dom4jTool->xmlFile2Doc】 xmlFilePath is null");
			return null;
		}
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(new File(xmlFilePath));
		} catch (Exception e) {
			logger.error("【Dom4jTool->xmlFile2Doc】 xmlFilePath:{} ", xmlFilePath, e);
		}
		return doc;
	}

	public static Document url2Doc(URL url) {
		if (url == null) {
			logger.info("【Dom4jTool->url2Doc】 url is null");
			return null;
		}
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(url);

		} catch (Exception e) {
			logger.error("【Dom4jTool->xmlFile2Doc】 url:{} ", url, e);
		}
		return doc;
	}

	public static Document stream2Doc(InputStream inputStream) {
		if (inputStream == null) {
			logger.info("【Dom4jTool->stream2Doc】 inputStream is null");
			return null;
		}
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(inputStream);
		} catch (Exception e) {
			logger.error("【Dom4jTool->inputStream】 inputStream:{} ", inputStream, e);
		}
		return doc;
	}

	public static void doc2File(Document doc, String filePath) {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			logger.error("【Dom4jTool->doc2File】 doc:{} filePath:{}", doc, filePath, e);
			return;
		}
	}

	public static String doc2XmlString(Node node) {
		return node.asXML();
	}

	public static Node selectSingleNode(Node node, String path) {
		Node selNode = node.selectSingleNode(path);
		return selNode;
	}

	@SuppressWarnings("unchecked")
	public static List<Node> selectNodes(Node node, String path) {
		List<Node> selNode = node.selectNodes(path);
		return selNode;
	}

	public static String getNodeText(Node node, String path) {
		Node res = node.selectSingleNode(path);
		if (res != null) {
			return res.getText();
		}
		return null;
	}

	public static void setNodeText(Node node, String path, String value) {
		Node res = node.selectSingleNode(path);
		res.setText(value);
	}

	public static Integer xml2File(String xml, String path) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			doc2File(doc, path);
		} catch (DocumentException e) {
			logger.error("【Dom4jTool->xml2File】 xml:{}", xml, e);
			return -1;
		}
		return 0;
	}

	public static Document readXmlFromFile(String path) {
		File f = new File(path);
		if (f == null || !f.exists()) {
			logger.info("【Dom4jTool->readXmlFromFile】 无法找到配置文件 path:{}", path);
			return null;
		}
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(f);
			return doc;
		} catch (DocumentException e) {
			logger.error("【Dom4jTool->readXmlFromFile】path:{}", path, e);
		}
		return null;
	}

	public static Integer str2File(String xml, String path) {
		Document doc = Dom4jTool.xmlString2Doc(xml);
		if (doc == null) {
			logger.info("【Dom4jTool->str2File】doc is null xml {} path {}", xml, path);
			return -1;
		}
		Dom4jTool.doc2File(doc, path);
		return 0;
	}

	public static Document readXml(String xml) {
		try {
			return DocumentHelper.parseText(xml);
		} catch (Exception e) {
			logger.error("【Dom4jTool->readXml】xml {}", xml, e);
		}
		return null;
	}

}
