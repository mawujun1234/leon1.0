package com.mawujun.fun;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;

@Repository
public interface FunRepository extends IRepository<Fun, String> {
	public List<String> queryAllDenyPageElement(Map params);
}
