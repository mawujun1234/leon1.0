package com.mawujun.utils.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	protected int pageNo = 1;
	protected int pageSize = 50;// 默认是每页50条
	protected int start = 1;

	// 这个优先级较高，如果设置了这个值，传递到后台的话，就使用这个值作为参数
	// mybatis时候用的较多
	protected Object params;// 具体的参数形式，可能是Map也可能是Bean
	
	protected List result = null;
	protected int total = -1;
	/**
	 * Page.getInstance().setPageSize(1).setStart(1).setParams("");
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public static Page getInstance(){
		
		return new Page();
	}
	public static Page getInstance(int start,int limit){
		
		return new Page().setStart(start).setPageSize(limit);
	}
	
	public int getPageNo() {
		//开始计算pageNo。看getFirst()
		this.pageNo=Double.valueOf(Math.ceil(new Double(start)/new Double(pageSize))).intValue()+1;
		return pageNo;
	}

	/**
	 * 每页显示多少条
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	public Page setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	public int getStart() {
		return start;
	}
	public Page setStart(int start) {
		this.start = start;
		return this;
	}
	public Object getParams() {
		return params;
	}
	/**
	 * 设置参数，一般是作为where条件的，可以是map，bean等各种类型
	 * 和addParams是冲突的，只能选一个
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param params
	 */
	public Page setParams(Object params) {
		this.params = params;
		return this;
	}
	/**
	 * 调用这个方法，就默认参数Map类型的,
	 * 默认null和空字符串不会添加进去
	 * 和setParams是冲突的，只能选一个
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param key
	 * @param params
	 * @return
	 */
	public Page addParam(String key,Object value) {
		if(value==null || "".equals(value)){
			return this;
		}
		if(this.params==null || !(this.params instanceof Map)){
			this.params=new HashMap<String,Object>();
		}
		((Map)this.params).put(key, value);
		return this;
	}
	public List getResult() {
		return result;
	}
	public Page setResult(List result) {
		this.result = result;
		return this;
	}
	/**
	 * 返回记录总数
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public int getTotal() {
		return total;
	}
	public Page setTotal(int totalItems) {
		this.total = totalItems;
		return this;
	}
	/**
	 * 返回结果集中的数量
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public int getResultSize() {
		if(result==null){
			return 0;
		}
		return result.size();
	}
	
	
	/**
	 * 返回页数
	 * 根据pageSize与totalItems计算总页数.
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) total / (double) getPageSize());

	}

	/**
	 * 是否还有下一页.
	 */
	public boolean hasNextPage() {
		return (getPageNo() + 1 <= getTotalPages());
	}

	/**
	 * 是否最后一页.
	 */
	public boolean isLastPage() {
		return !hasNextPage();
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPageNo() {
		if (hasNextPage()) {
			return getPageNo() + 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean hasPrePage() {
		return (getPageNo() > 1);
	}

	/**
	 * 是否第一页.
	 */
	public boolean isFirstPage() {
		return !hasPrePage();
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePageNo() {
		if (hasPrePage()) {
			return getPageNo() - 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
	 * @param count 需要计算的列表大小
	 * @return pageNo列表 
	 */
	public List<Integer> getSlider(int count) {
		int halfSize = count / 2;
		int totalPage = getTotalPages();

		int startPageNo = Math.max(getPageNo() - halfSize, 1);
		int endPageNo = Math.min(startPageNo + count - 1, totalPage);

		if (endPageNo - startPageNo < count) {
			startPageNo = Math.max(endPageNo - count, 1);
		}

		List<Integer> result = new ArrayList<Integer>();
		for (int i = startPageNo; i <= endPageNo; i++) {
			result.add(i);
		}
		return result;
	}

	
	
	
}
