package ${basepackage};

import com.mawujun.repository.BaseRepository;
import org.springframework.stereotype.Service;


<#include "/java_copyright.include"/>

@Service
public class ${simpleClassName}Service extends BaseRepository<${simpleClassName}, ${idType}>{
	<#if extenConfig.create==true>
	public void create(${simpleClassName} entity) {
		// TODO Auto-generated method stub
		super.create(entity);
	}
	</#if>
	
	<#if extenConfig.update==true>
	public void update(${simpleClassName} entity) {
		// TODO Auto-generated method stub
		super.create(entity);
	}
	</#if>
	
	<#if extenConfig.destroy==true>
	public void delete(${idType} id) {
		// TODO Auto-generated method stub
		super.delete(id);
	}
	</#if>

}
