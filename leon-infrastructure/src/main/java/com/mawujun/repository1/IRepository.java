package com.mawujun.repository1;

import java.io.Serializable;

import com.mawujun.repository.hibernate.IHibernateDao;
import com.mawujun.utils.page.Page;

/**
 * 自定义的方法名不要重复，不要重载，因为方法名就对应于sql的id，这个是不能重复的
 * @author mawujun email:16064988@163.com qq:16064988
 *
 * @param <T>
 * @param <ID>
 */
public interface IRepository<T, ID extends Serializable> extends IHibernateDao<T, ID> {
	/**
	 * 会自动建立count的sql语句，id默认为：方法名_count,本例就queryPage_count.你也可以自己定义这个sql，取名规则也是这样，这样的话就会覆盖默认实现的count语句
	 * 后台只需要定义一个sql,select * from XXX where ...就可以了，不需要些分页的代码例如mysql的limit，sqlserver的top等
	 * 如果 要自定义其他查询分页，只需要定义一个方法，把方法名改成你想要的就可以了，其他的应该和这个方法一样.参数和返回值都必须是Page，才会自动进行查询
	 * 
	 * Page的使用方法Page page=Page.getInstance().setPageSize(1).setStart(1).setParams("");
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param statement
	 * @param page
	 * @return
	 */
	public Page queryPage(Page page);
	public Page queryPage1(Page page);
}
