package entity;

import java.io.Serializable;
import java.util.List;
/**
 * 分页结果类
 * @author Administrator
 *
 */
public class PageResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long total;//总记录数
	private List rows;//当前页面记录
	public Long getTotal() {
		return total;
	}
	public PageResult(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
