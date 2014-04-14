<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
package ${basepackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import ${basepackage}.${simpleClassName};
import ${basepackage}.${simpleClassName}Repository;


<#include "/java_copyright.include"/>

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ${simpleClassName}Service extends AbstractService<${simpleClassName}, ${idType}>{

	@Autowired
	private ${simpleClassName}Repository ${simpleClassNameFirstLower}Repository;
	
	@Override
	public ${simpleClassName}Repository getRepository() {
		return ${simpleClassNameFirstLower}Repository;
	}

}
