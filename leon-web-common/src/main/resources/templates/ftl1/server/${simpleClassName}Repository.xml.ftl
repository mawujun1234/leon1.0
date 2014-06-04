
<#assign simpleClassNameFirstLower = simpleClassName?uncap_first>   
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"
"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">

<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro printDollar value>${r"${"}${value}}</#macro>
<#macro namespace>${basepackage}</#macro>
<!-- mawujun qq:16064988 e-mail:16064988@qq.com-->
<mapper namespace="<@namespace/>.${simpleClassName}Repository">

	<!-- insert语句的生成开始=============================================================================================== -->
	<#-- 还有guid的生成方式没有做，这个是根据数据库的函数生成的-->
	<#if idGeneratorStrategy=="identity">
		<!-- useGeneratedKeys="true" keyProperty="xxx" for sqlserver and mysql -->
		<insert id="create" useGeneratedKeys="true" keyProperty="${idPropertyName}" parameterType="<@namespace/>.${simpleClassName}">	
		    <![CDATA[
		        INSERT INTO ${tableName} (
		        <#list propertyColumns as propertyColumn> 	
		        	<#if propertyColumn.isAssociationType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isComponentType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isBaseType==true>
		        		${propertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        	<#else>
		        		${propertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        	</#if>
		        </#list>
		        ) VALUES (
		        <#list propertyColumns as propertyColumn>
		        	<#if propertyColumn.isAssociationType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			<@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isComponentType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			<@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isBaseType==true>
		        		<@mapperEl propertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        	<#else>
		        		<@mapperEl propertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        	</#if>
		        </#list>        
		        )
		    ]]>
		</insert>
	<#elseif idGeneratorStrategy=="sequence">
		<insert id="create" parameterType="<@namespace/>.${simpleClassName}">
			<!--	
				oracle: order="BEFORE" SELECT sequenceName.nextval AS ID FROM DUAL 
				DB2: order="BEFORE"" values nextval for sequenceName
			<selectKey resultType="java.lang.Long" order="BEFORE" keyProperty="userId">
				SELECT sequenceName.nextval AS ID FROM DUAL 
	        </selectKey>
			-->
			<selectKey resultType="java.lang.Long" order="BEFORE" keyProperty="${idPropertyName}">
				SELECT ${sequenceName}.nextval AS ${idColumnName} FROM DUAL 
	        </selectKey>
	        <![CDATA[
		        INSERT INTO ${tableName} (
		        <#list propertyColumns as propertyColumn> 	
		        	<#if propertyColumn.isAssociationType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isComponentType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isBaseType==true>
		        		${propertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        	</#if>
		        </#list>
		        ) VALUES (
		        <#list propertyColumns as propertyColumn>
		        	<#if propertyColumn.isAssociationType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			<@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isComponentType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			<@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isBaseType==true>
		        		<@mapperEl propertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        	</#if>
		        </#list>        
		        )
		    ]]>
	    </insert>
	<#elseif idGeneratorStrategy=="uuid">
		<!--在插入的时候id就要写上了，相当于是自定义ID-->
		<insert id="create" parameterType="<@namespace/>.${simpleClassName}">
			<![CDATA[
		        INSERT INTO ${tableName} (
		        <#list propertyColumns as propertyColumn> 	
		        	<#if propertyColumn.isAssociationType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isComponentType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isBaseType==true>
		        		${propertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        	<#else>
		        		${propertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        	</#if>
		        </#list>
		        ) VALUES (
		        <#list propertyColumns as propertyColumn>
		        	<#if propertyColumn.isAssociationType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			<@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isComponentType==true>
		        		<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        			<@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        		</#list>
		        	<#elseif propertyColumn.isBaseType==true>
		        		<@mapperEl propertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        	<#else>
		        		<@mapperEl propertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        	</#if>
		        </#list>        
		        )
		    ]]>
	    </insert>
	   
	</#if>
	<!-- insert语句的生成结束=============================================================================================== -->

	

	<!-- delete语句的生成开始=============================================================================================== -->
	<#-- 按id删除和按条件删除-->
	<delete id="destroy" parameterType="${idType}">
    <![CDATA[
        DELETE FROM ${tableName} WHERE ${idColumnName}=<@mapperEl idPropertyName/>
    ]]>
    </delete>
    
  <!--  
    <delete id="deleteByWheres">
        DELETE FROM ${tableName}
        <where>
        	<foreach collection="array" index="index" item="item" separator=" and ">
				${r"${item.prop} ${item.op} #{item.value}"}
			</foreach>
        </where>
    </delete>
	 delete语句的生成结束=============================================================================================== -->

	

	<!-- update语句的生成开始=============================================================================================== -->
	<update id="update" parameterType="<@namespace/>.${simpleClassName}">
		 UPDATE ${tableName} 
			<set>
	        <#list propertyColumns as propertyColumn>
	        <#if propertyColumn.isAssociationType==true>
		        <#list propertyColumn.propertyColumns as innerPropertyColumn>
		        	<#if innerPropertyColumn.isIdProperty==true>
		        	<if test="${propertyColumn.property}!=null and ${propertyColumn.property}.${innerPropertyColumn.property}!=null">
		        	${innerPropertyColumn.column} = <@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        	</if>
		        	</#if>
		        </#list>
		    <#elseif propertyColumn.isComponentType==true>
		        	<#list propertyColumn.propertyColumns as innerPropertyColumn>
		        		<if test="${propertyColumn.property}!=null  and ${propertyColumn.property}.${innerPropertyColumn.property}!=null ">
			        	${innerPropertyColumn.column} = <@mapperEl propertyColumn.property+"."+innerPropertyColumn.property/> <#if propertyColumn_has_next>,</#if>
			        	</if>
		        	</#list>
		     <#elseif propertyColumn.isBaseType==true>
		         <if test="${propertyColumn.property}!=null">
		        	${propertyColumn.column} = <@mapperEl propertyColumn.property/> <#if propertyColumn_has_next>,</#if>
		        </if>
		    </#if>
	        </#list>
	        </set>
        WHERE 
        	${idColumnName}=<@mapperEl idPropertyName/>    
	</update>
	<!-- update语句的生成结束=============================================================================================== -->

	

	<!-- select语句的生成开始=============================================================================================== -->	
	<!-- 用于select查询公用抽取的列 -->
	<sql id="columns">
	    <![CDATA[
		${idColumnName},
		<#list propertyColumns as propertyColumn> 	
		    <#if propertyColumn.isAssociationType==true>
		        <#list propertyColumn.propertyColumns as innerPropertyColumn>
		        ${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        </#list>
		        <#elseif propertyColumn.isComponentType==true>
		        <#list propertyColumn.propertyColumns as innerPropertyColumn>
		        ${innerPropertyColumn.column} <#if propertyColumn_has_next>,</#if>
		        </#list>
		        <#elseif propertyColumn.isBaseType==true>
		        ${propertyColumn.column} <#if propertyColumn_has_next>,</#if>
		    </#if>
		</#list>
	    ]]>
	</sql>
	<select id="queryPage" resultType="<@namespace/>.${simpleClassName}" parameterType="map">
    	SELECT <include refid="columns" />
	    FROM ${tableName} 
		<where>
			<if test="wheres!=null">
        	<foreach collection="wheres" index="index" item="item" separator=" and ">
				${r"${item.prop} ${item.op} #{item.value}"}
			</foreach>
			</if>
        </where>
    </select>
    
    <select id="get" resultType="<@namespace/>.${simpleClassName}" parameterType="${idType}">
    	SELECT <include refid="columns" />
	    FROM ${tableName} 
		WHERE 
        	${idColumnName}=<@mapperEl idPropertyName/>    
    </select>
	<!-- select语句的生成结束=============================================================================================== -->
</mapper>

