package com.mawujun.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;

@Service
public class MenuService extends BaseService<Menu, String> {
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public BaseRepository<Menu, String> getRepository() {
		// TODO Auto-generated method stub
		return menuRepository;
	}

	/**
	 * 这样就可以，调用父类的方法就不可以，这是怎么回事情，参考http://www.verydemo.com/demo_c143_i3007.html
	 * 给出一个分析思路，首先是是使用hibernate4.HibernateTransactionManager，接着是去掉替换read的配置，再接着是controller和service两个配置文件分开造成的原因
	 * 最后考虑嗲用的方法是不是从父类哪里继承过来的，如果是的话，为甚么不能对继承的方法使用事务，现在问题就在这里
	 * @param id
	 * @return
	 */
	public Menu get1(String id) {
		return getRepository().get(id);
	}
}
