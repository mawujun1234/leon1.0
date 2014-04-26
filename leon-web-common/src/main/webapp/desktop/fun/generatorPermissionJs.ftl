var funIds={
<#list funIds as funId>	
'${funId}':true<#if funId_has_next>,</#if>
</#list>
}

function checkPermission(funId){
	if(funIds[funId]){
		return true;
	} else {
		return false;
	}
}