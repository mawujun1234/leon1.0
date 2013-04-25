package com.mawujun.controller.spring.mvc.exception;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.MediaTypeFileExtensionResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class ContentNegotiationManager_Custom  extends ContentNegotiationManager{

	private final List<ContentNegotiationStrategy> contentNegotiationStrategies =
			new ArrayList<ContentNegotiationStrategy>();

	private final Set<MediaTypeFileExtensionResolver> fileExtensionResolvers =
			new LinkedHashSet<MediaTypeFileExtensionResolver>();

	/**
	 * Create an instance with the given ContentNegotiationStrategy instances.
	 * <p>Each instance is checked to see if it is also an implementation of
	 * MediaTypeFileExtensionResolver, and if so it is registered as such.
	 * @param strategies one more more ContentNegotiationStrategy instances
	 */
	public ContentNegotiationManager_Custom(ContentNegotiationStrategy... strategies) {
		Assert.notEmpty(strategies, "At least one ContentNegotiationStrategy is expected");
		this.contentNegotiationStrategies.addAll(Arrays.asList(strategies));
		for (ContentNegotiationStrategy strategy : this.contentNegotiationStrategies) {
			if (strategy instanceof MediaTypeFileExtensionResolver) {
				this.fileExtensionResolvers.add((MediaTypeFileExtensionResolver) strategy);
			}
		}
	}

	/**
	 * Create an instance with a {@link HeaderContentNegotiationStrategy}.
	 */
	public ContentNegotiationManager_Custom() {
		this(new HeaderContentNegotiationStrategy());
	}

	/**
	 * Add MediaTypeFileExtensionResolver instances.
	 * <p>Note that some {@link ContentNegotiationStrategy} implementations also
	 * implement {@link MediaTypeFileExtensionResolver} and the class constructor
	 * accepting the former will also detect implementations of the latter. Therefore
	 * you only need to use this method to register additional instances.
	 * @param resolvers one or more resolvers
	 */
	public void addFileExtensionResolvers(MediaTypeFileExtensionResolver... resolvers) {
		this.fileExtensionResolvers.addAll(Arrays.asList(resolvers));
	}

	/**
	 * Delegate to all configured ContentNegotiationStrategy instances until one
	 * returns a non-empty list.
	 * @param webRequest the current request
	 * @return the requested media types or an empty list, never {@code null}
	 * @throws HttpMediaTypeNotAcceptableException if the requested media types cannot be parsed
	 */
	public List<MediaType> resolveMediaTypes(NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException {
		List<MediaType> aaaaa=new ArrayList<MediaType>();
		for (ContentNegotiationStrategy strategy : this.contentNegotiationStrategies) {//修改了这里，让他们可以选择所有的情况，而不是遇见一个就跑出
//			List<MediaType> mediaTypes = strategy.resolveMediaTypes(webRequest);
//			if (!mediaTypes.isEmpty()) {
//				return mediaTypes;
//			}
			List<MediaType> mediaTypes = strategy.resolveMediaTypes(webRequest);
			if (!mediaTypes.isEmpty()) {		
				aaaaa.addAll(mediaTypes);
			}
		}
		return aaaaa;
	}

	/**
	 * Delegate to all configured MediaTypeFileExtensionResolver instances and aggregate
	 * the list of all file extensions found.
	 */
	public List<String> resolveFileExtensions(MediaType mediaType) {
		Set<String> result = new LinkedHashSet<String>();
		for (MediaTypeFileExtensionResolver resolver : this.fileExtensionResolvers) {
			result.addAll(resolver.resolveFileExtensions(mediaType));
		}
		return new ArrayList<String>(result);
	}

	/**
	 * Delegate to all configured MediaTypeFileExtensionResolver instances and aggregate
	 * the list of all known file extensions.
	 */
	public List<String> getAllFileExtensions() {
		Set<String> result = new LinkedHashSet<String>();
		for (MediaTypeFileExtensionResolver resolver : this.fileExtensionResolvers) {
			result.addAll(resolver.getAllFileExtensions());
		}
		return new ArrayList<String>(result);
	}

}
