package com.sunspot.pojoext;

import java.util.List;
 
public class DataGridModel<T> {
	
	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 列表数
	 */
	private int rows;

	/**
	 * 总的条数
	 */
	private int total;

	/**
	 * 
	 */
	private int records;
	
	/**
	 * 数据库所有条数
	 */
	private int count ; 
	
	/**
	 * 当前条数,如前页面=1，则当前条数=当前页面*显示条数(curNum = page * rows)
	 */ 
	public int getCurNum() {
		return (this.getPage()-1) * this.getRows();
	}
  
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 列表的json
	 */
	private List<T> content;
 
	public DataGridModel() {
		this.page = 1 ;
		this.rows = 15 ;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
	    total = (int) Math.ceil((float) getCount() / (float) getRows());
		return total ; 
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	/**
	 * recores 即 content.size
	 * @param records
	 * @author LuoAnDong
	 */
	public void setRecords(int records) {
		this.records = this.getContent().size();
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

}
