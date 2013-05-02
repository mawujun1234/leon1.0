package com.mawujun.fun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.exception.BussinessException;
import com.mawujun.exception.WebCommonExceptionCode3;
import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;
import com.mawujun.utils.page.WhereInfo;

@Service
public class FunService extends BaseService<Fun, String> {
	@Autowired
	private FunRepository funRepository;
	
	@Override
	public BaseRepository<Fun, String> getRepository() {
		// TODO Auto-generated method stub
		return funRepository;
	}
	@Override
	public void delete(Fun entity) {
		//判断是否具有子节点
		WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getId());
		int childs=this.getRepository().queryCount(whereinfo);
		if(true){
			throw new BussinessException("存在子节点，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		
		getRepository().delete(entity);
	}

}
