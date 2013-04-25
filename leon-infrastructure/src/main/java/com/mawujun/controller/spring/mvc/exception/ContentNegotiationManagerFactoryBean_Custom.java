package com.mawujun.controller.spring.mvc.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.FixedContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.accept.ServletPathExtensionContentNegotiationStrategy;
import org.springframework.web.context.ServletContextAware;

//<bean class="org.springframework.web.servlet.view.ContentNegotiatingViewResolver">
//<property name="contentNegotiationManager"> 
//   <!--  <bean class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean"> -->
//   <bean class="com.mawujun.utils.spring.mvc.exception.ContentNegotiationManagerFactoryBean_Custom">
//       <property name="mediaTypes"> 
//             <props>
//             	<prop key="json">application/json</prop>
//             	<prop key="html">text/html</prop>
//             	<prop key="xml">application/xml</prop>
//             </props>
//       </property> 
//       <!-- 默认展现形式 -->  
//		  <property name="defaultContentType" value="application/json"/>  
//		  <!-- 设置为true以忽略对Accept Header的支持-->
//		  <property name="ignoreAcceptHeader" value="false" />
//		  <!-- 用于开启 /userinfo/123?format=json 的支持 -->  
//		  <property name="favorParameter" value="true"/>
//		  <!-- 用于关闭 /userinfo/123.json 的支持，扩展名至mimeType的映射,即 /user.json => application/json -->  
//		  <!-- 现在变成这三种方式，只能选一个的情况了，看ContentNegotiationManager的resolveMediaTypes方法，主要还是因为启用后缀这个
//		  的优先级比较高，而这个ServletPathExtensionContentNegotiationStrategy又会返回默认值，所以一直都走不到其他两种方案了
//		  现在重新实现了两个类，注意这个bug的解决 -->
//		  <property name="favorPathExtension" value="true"/>
//   </bean> 
//	</property> 
//这样重载过后，就可以同时使用三种方式了
/**
 * 第220行变了
 * @author mawujun
 *
 */
public class ContentNegotiationManagerFactoryBean_Custom implements FactoryBean<ContentNegotiationManager>, InitializingBean, ServletContextAware {

	private boolean favorPathExtension = true;

	private boolean favorParameter = false;

	private boolean ignoreAcceptHeader = false;

	private Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();

	private Boolean useJaf;

	private String parameterName = "format";

	private MediaType defaultContentType;

	private ContentNegotiationManager contentNegotiationManager;

	private ServletContext servletContext;

	/**
	 * Indicate whether the extension of the request path should be used to determine
	 * the requested media type with the <em>highest priority</em>.
	 * <p>By default this value is set to {@code true} in which case a request
	 * for {@code /hotels.pdf} will be interpreted as a request for
	 * {@code "application/pdf"} regardless of the {@code Accept} header.
	 */
	public void setFavorPathExtension(boolean favorPathExtension) {
		this.favorPathExtension = favorPathExtension;
	}

	/**
	 * Add mappings from file extensions to media types represented as strings.
	 * <p>When this mapping is not set or when an extension is not found, the Java
	 * Action Framework, if available, may be used if enabled via
	 * {@link #setFavorPathExtension(boolean)}.
	 *
	 * @see #addMediaType(String, MediaType)
	 * @see #addMediaTypes(Map)
	 */
	public void setMediaTypes(Properties mediaTypes) {
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			for (Entry<Object, Object> entry : mediaTypes.entrySet()) {
				String extension = ((String)entry.getKey()).toLowerCase(Locale.ENGLISH);
				this.mediaTypes.put(extension, MediaType.valueOf((String) entry.getValue()));
			}
		}
	}

	/**
	 * Add a mapping from a file extension to a media type.
	 * <p>If no mapping is added or when an extension is not found, the Java
	 * Action Framework, if available, may be used if enabled via
	 * {@link #setFavorPathExtension(boolean)}.
	 */
	public void addMediaType(String fileExtension, MediaType mediaType) {
		this.mediaTypes.put(fileExtension, mediaType);
	}

	/**
	 * Add mappings from file extensions to media types.
	 * <p>If no mappings are added or when an extension is not found, the Java
	 * Action Framework, if available, may be used if enabled via
	 * {@link #setFavorPathExtension(boolean)}.
	 */
	public void addMediaTypes(Map<String, MediaType> mediaTypes) {
		if (mediaTypes != null) {
			this.mediaTypes.putAll(mediaTypes);
		}
	}

	/**
	 * Indicate whether to use the Java Activation Framework as a fallback option
	 * to map from file extensions to media types. This is used only when
	 * {@link #setFavorPathExtension(boolean)} is set to {@code true}.
	 * <p>The default value is {@code true}.
	 *
	 * @see #parameterName
	 * @see #setMediaTypes(Properties)
	 */
	public void setUseJaf(boolean useJaf) {
		this.useJaf = useJaf;
	}

	/**
	 * Indicate whether a request parameter should be used to determine the
	 * requested media type with the <em>2nd highest priority</em>, i.e.
	 * after path extensions but before the {@code Accept} header.
	 * <p>The default value is {@code false}. If set to to {@code true}, a request
	 * for {@code /hotels?format=pdf} will be interpreted as a request for
	 * {@code "application/pdf"} regardless of the {@code Accept} header.
	 * <p>To use this option effectively you must also configure the MediaType
	 * type mappings via {@link #setMediaTypes(Properties)}.
	 *
	 * @see #setParameterName(String)
	 */
	public void setFavorParameter(boolean favorParameter) {
		this.favorParameter = favorParameter;
	}

	/**
	 * Set the parameter name that can be used to determine the requested media type
	 * if the {@link #setFavorParameter} property is {@code true}.
	 * <p>The default parameter name is {@code "format"}.
	 */
	public void setParameterName(String parameterName) {
		Assert.notNull(parameterName, "parameterName is required");
		this.parameterName = parameterName;
	}

	/**
	 * Indicate whether the HTTP {@code Accept} header should be ignored altogether.
	 * If set the {@code Accept} header is checked at the
	 * <em>3rd highest priority</em>, i.e. after the request path extension and
	 * possibly a request parameter if configured.
	 * <p>By default this value is set to {@code false}.
	 */
	public void setIgnoreAcceptHeader(boolean ignoreAcceptHeader) {
		this.ignoreAcceptHeader = ignoreAcceptHeader;
	}

	/**
	 * Set the default content type.
	 * <p>This content type will be used when neither the request path extension,
	 * nor a request parameter, nor the {@code Accept} header could help
	 * determine the requested content type.
	 */
	public void setDefaultContentType(MediaType defaultContentType) {
		this.defaultContentType = defaultContentType;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void afterPropertiesSet() throws Exception {
		List<ContentNegotiationStrategy> strategies = new ArrayList<ContentNegotiationStrategy>();

		if (this.favorPathExtension) {
			PathExtensionContentNegotiationStrategy strategy;
			if (this.servletContext != null) {
				strategy = new ServletPathExtensionContentNegotiationStrategy(this.servletContext, this.mediaTypes);
			} else {
				strategy = new PathExtensionContentNegotiationStrategy(this.mediaTypes);
			}
			if (this.useJaf != null) {
				strategy.setUseJaf(this.useJaf);
			}
			strategies.add(strategy);
		}

		if (this.favorParameter) {
			ParameterContentNegotiationStrategy strategy = new ParameterContentNegotiationStrategy(this.mediaTypes);
			strategy.setParameterName(this.parameterName);
			strategies.add(strategy);
		}

		if (!this.ignoreAcceptHeader) {
			strategies.add(new HeaderContentNegotiationStrategy());
		}

		if (this.defaultContentType != null) {
			strategies.add(new FixedContentNegotiationStrategy(this.defaultContentType));
		}

		ContentNegotiationStrategy[] array = strategies.toArray(new ContentNegotiationStrategy[strategies.size()]);
		
		
		//========================================
		this.contentNegotiationManager = new ContentNegotiationManager_Custom(array);//这里变了，变成自己定义的ContentNegotiationManager_Custom
		//========================================
	}

	public Class<?> getObjectType() {
		
		//========================================
		return ContentNegotiationManager_Custom.class;
		//========================================
	}

	public boolean isSingleton() {
		return true;
	}

	public ContentNegotiationManager getObject() throws Exception {
		return this.contentNegotiationManager;
	}

}
