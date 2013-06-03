package com.mawujun.utils.page;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mawujun.utils.ArrayUtils;



/**
 * 分页参数封装类.
 * params参数的类型，可以是Map，Bean，和基本类型
 * 
 */
public class PageRequest {
	protected int pageNo = 1;
	protected int pageSize = -1;//默认是不分页的

	//protected String orderBy = null;
	//protected String orderDir = null;

	//protected boolean countTotal = true;
	
	//这个优先级较高，如果设置了这个值，传递到后台的话，就使用这个值作为参数
	//mybatis时候用的较多
	protected Map  params;//具体的参数形式，可能是Map也可能是Bean
	
	protected String sqlId;//mybatis中的statement名称，可以在前台指定，这样后台dao就不需要再写了。
	
//	//主要是自动查询，通过修改前台的参数形式来传递
//	protected List<WhereInfo> wheres=new ArrayList<WhereInfo>();
//	
//	protected List<SortInfo> sorts=new ArrayList<SortInfo>();
	protected WhereInfo[] wheres;//=new ArrayList<WhereInfo>();
	
	protected SortInfo[] sorts;//=new ArrayList<SortInfo>();
	
	boolean starFlag=false;//用来判断当start和limit都设置了之后，才进行初始化pageNo和pageSize的值
	protected int start = 0;

	public PageRequest() {
	}

	
	public static PageRequest initByStratAndLimit(final Integer start,final Integer limit) {
		PageRequest page=new PageRequest();
		page.setStratAndLimit(start, limit);
		return page;
	}
	
	public static PageRequest initByPageNoAndPageSize(final Integer start,final Integer limit) {
		PageRequest page=new PageRequest();
		page.setStratAndLimit(start, limit);
		return page;
	}


	/**
	 * 用来获取extjs的传递过来的值
	 * @param start
	 */
	public void setStart(Integer start) {
		if (start==null || start < 0) {
			this.start = 0;
			return;
		}
		if(starFlag==false){
			this.start=start;
			starFlag=true;
		} else {
			this.setStratAndLimit(start, this.pageSize);
		}
	}
	/**
	 * 用来获取extjs的传递过来的值
	 * limit设置为-1或者null，表示不分页，取出所有数据
	 * @param limit
	 */
	public void setLimit(Integer limit) {
		if (limit==null || limit < 1) {
			this.pageSize = -1;
			return;
		}
		if(starFlag==false){
			this.pageSize=limit;
			starFlag=true;
		} else {
			this.setStratAndLimit(this.start, limit);
		}
	}

	/**
	 * 获得当前页的页号, 序号从1开始, 默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号, 序号从1开始, 低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}
	/**
	 * pageSize=-1表示不分页，取出所有数据
	 * @param pageNo
	 * @param pageSize
	 */
	public void setPageNoAndPageSize(final Integer pageNo,final Integer pageSize) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
	}
	
	/**
	 * 设置将要开始的页的起始记录，比如每页显示10条，那那第二页的start=11
	 * 最终会转换成pageNo
	 * @param start 开始记录
	 * @param limit 每页显示的行数
	 */
	public void setStratAndLimit(final Integer start,final Integer limit) {
		if(start==null || limit==null || start<0 || limit<1){
			return;
		}
		//设置页面条数
		this.setPageSize(limit);
		//如果没有设置start，那就是从第一页开始
		if(start==null || start < 0){
			this.pageNo = 1;
			return;
		}
		//开始计算pageNo。看getFirst()
		this.pageNo=start/limit+1;
	}

	/**
	 * 获得每页的记录数量, 默认为-1,表示不分页.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量, 低于1时自动调整为-1表示不分页.
	 */
	public void setPageSize(final int pageSize) {
		if (pageSize < 1) {
			this.pageSize = -1;
		} else {
			this.pageSize = pageSize;
		}
	}

//	/**
//	 * 获得排序字段, 无默认值. 多个排序字段时用','分隔.
//	 */
//	public String getOrderBy() {
//		return orderBy;
//	}
//
//	/**
//	 * 设置排序字段, 多个排序字段时用','分隔.
//	 */
//	public void setOrderBy(final String orderBy) {
//		this.orderBy = orderBy;
//	}
//
//	/**
//	 * 获得排序方向, 无默认值.
//	 */
//	public String getOrderDir() {
//		return orderDir;
//	}
//
//	/**
//	 * 设置排序方式向.
//	 * 
//	 * @param orderDir 可选值为desc或asc,多个排序字段时用','分隔.
//	 */
//	public void setOrderDir(final String orderDir) {
//		if(orderDir==null){
//			return;
//		}
//		String lowcaseOrderDir = StringUtils.lowerCase(orderDir);
//
//		//检查order字符串的合法值
//		String[] orderDirs = StringUtils.split(lowcaseOrderDir, ',');
//		for (String orderDirStr : orderDirs) {
//			if (!StringUtils.equals(Sort.DESC, orderDirStr) && !StringUtils.equals(Sort.ASC, orderDirStr)) {
//				throw new IllegalArgumentException("排序方向" + orderDirStr + "不是合法值");
//			}
//		}
//
//		this.orderDir = lowcaseOrderDir;
//	}
//
//	/**
//	 * 获得排序参数.
//	 */
//	public List<Sort> getSort() {
//		if(orderBy==null || orderDir==null){
//			return null;
//		}
//		String[] orderBys = StringUtils.split(orderBy, ',');
//		String[] orderDirs = StringUtils.split(orderDir, ',');
//		AssertUtils.isTrue(orderBys.length == orderDirs.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");
//
//		List<Sort> orders = new ArrayList<Sort>();
//		for (int i = 0; i < orderBys.length; i++) {
//			orders.add(new Sort(orderBys[i], orderDirs[i]));
//		}
//
//		return orders;
//	}
//	public String getSortColumns() {
//		String[] orderBys = StringUtils.split(orderBy, ',');
//		String[] orderDirs = StringUtils.split(orderDir, ',');
//		AssertUtils.isTrue(orderBys.length == orderDirs.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");
//
//		//List<Sort> orders = new ArrayList<Sort>();
//		StringBuilder build=new StringBuilder("");
//		for (int i = 0; i < orderBys.length; i++) {
//			if(i!=0){
//				build.append(" , ");	
//			}
//			build.append(orderBys[i]+" "+orderDirs[i]);
//		}
//
//		return build.toString();
//	}
//
//	/**
//	 * 是否已设置排序字段,无默认值.
//	 */
//	public boolean isOrderBySetted() {
//		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orderDir));
//	}

	/**
	 * 是否默认计算总记录数.
	 */
	public boolean isCountTotal() {
		if(this.getPageSize()>=1){
			return true;
		} else {
			return false;
		}
	}



	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置, 序号从1开始.
	 */
	public int getStart() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * 是否使用where来进行判断
	 * @return
	 */
	public boolean hasWhereInfo() {
		if(this.getWheres()!=null && this.getWheres().length>0){
			return true;
		} else {
			return false;
		}
	}



	public boolean hasSort() {
		if(this.getSorts()!=null && this.getSorts().length>0){
			return true;
		} else {
			return false;
		}
	}





	/**
	 * 
	 * 
	 * @param params
	 */
	public Map getParams() {
		if(params==null){
			params=new HashMap();
		}
		if(this.getWheres()!=null){
			for(WhereInfo whereInfo:this.getWheres()){
				params.put(whereInfo.getProperty(), whereInfo.getValue());
			}
			params.put("wheres", this.getWheres());
		}
		if(this.getSorts()!=null){
			params.put("sorts", this.getSorts());
		}
		return params;
		
//		if(params!=null){
//			return params;
//		} else {
//			if(this.getWheres()==null){
//				return null;
//			}
//			Map<String,Object> params=new HashMap<String,Object>();
//			for(WhereInfo whereInfo:this.getWheres()){
//				params.put(whereInfo.getProperty(), whereInfo.getValue());
//			}
//			params.put("whereInfos", this.getWheres());
//			return params;
//		}
//		
	}
	/**
	 * 如从sorts中获取,返回的是String,例如 name asc，id desc，aaa，没有order by关键字
	 * @param params
	 */
	public String getSortsString() {
		
			if(!this.hasSort()){
				return null;
			}
			StringBuilder builder=new StringBuilder();
			for(SortInfo sortInfo:this.sorts){
				builder.append(","+sortInfo.getProperty()+" "+sortInfo.getDirection());
			}
			return builder.toString().substring(1);
			
	}


	public void setParams(Map params) {
		this.params = params;
	}


	public List<WhereInfo> getWheresList() {
		if(wheres==null){
			return null;
		}
		return Arrays.asList(wheres);
	}
	public WhereInfo[] getWheres() {
		return this.wheres;
	}


	public List<SortInfo> getSortsList() {
		if(sorts==null){
			return null;
		}
		return Arrays.asList(sorts);
	}
	public SortInfo[] getSorts() {
		return this.sorts;
	}
	public void addSorts(SortInfo sortInfo) {
		if(this.sorts==null){
			this.sorts=new SortInfo[0];
		}
		this.sorts=ArrayUtils.add(this.sorts, sortInfo);
	}


	public void setWheres(List<WhereInfo> wheres) {
		if(wheres!=null){
			this.wheres=wheres.toArray(new WhereInfo[wheres.size()]);
		}
	}
	public void setWheres(WhereInfo... wheres) {
		this.wheres=wheres;
	}
//	public void setWheres(WhereInfo[] wheres) {
//		this.wheres=wheres;
//	}
	public void addWheres(WhereInfo whereInfo) {
		if(this.wheres==null){
			this.wheres=new WhereInfo[0];
		}
		this.wheres=ArrayUtils.add(this.wheres, whereInfo);
	}


	public void setSorts(List<SortInfo> sorts) {
		if(sorts!=null){
			this.sorts=sorts.toArray(new SortInfo[sorts.size()]);
		}
	}
	public void setSorts(SortInfo[] sorts) {
		this.sorts=sorts;
	}


	public String getSqlId() {
		return sqlId;
	}


	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	
//	/**
//	 * 取得带相同前缀的Request Parameters.
//	 * 
//	 * 返回的结果的Parameter名已去除前缀.
//	 */
//	public  Map<String, Object> initParamsStartingWith(ServletRequest request, String prefix) {
//		AssertUtils.notNull(request, "Request must not be null");
//		Enumeration paramNames = request.getParameterNames();
//		Map<String, Object> params = new TreeMap<String, Object>();
//		if (prefix == null) {
//			prefix = "";
//		}
//		while (paramNames != null && paramNames.hasMoreElements()) {
//			String paramName = (String) paramNames.nextElement();
//			if ("".equals(prefix) || paramName.startsWith(prefix)) {
//				String unprefixed = paramName.substring(prefix.length());
//				String[] values = request.getParameterValues(paramName);
//				if (values == null || values.length == 0) {
//					// Do nothing, no values found at all.
//				} else if (values.length > 1) {
//					params.put(unprefixed, values);
//				} else {
//					params.put(unprefixed, values[0]);
//				}
//			}
//		}
//		this.params=params;
//		return params;
//	}

}

