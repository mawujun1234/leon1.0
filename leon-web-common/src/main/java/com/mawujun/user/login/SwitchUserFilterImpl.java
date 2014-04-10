package com.mawujun.user.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.security.web.authentication.switchuser.SwitchUserAuthorityChanger;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.mawujun.utils.ReflectionUtils;

/**
 * 支持多用户切换，不单单是一个
 * 因为SwitchUserFilter很多方法都是私有的，不能覆盖，所以全部拷贝过来再修改了
 * 每次切换的时候，都会重新获取用户的权限，这样修改权限后就可以立马呈现新的quanxian
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class SwitchUserFilterImpl extends GenericFilterBean implements ApplicationEventPublisherAware,
MessageSourceAware {
	 //~ Static fields/initializers =====================================================================================

    public static final String SPRING_SECURITY_SWITCH_USERNAME_KEY = "j_username";
    public static final String ROLE_PREVIOUS_ADMINISTRATOR = "ROLE_PREVIOUS_ADMINISTRATOR";

    //~ Instance fields ================================================================================================

    private ApplicationEventPublisher eventPublisher;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private String exitUserUrl = "/j_spring_security_exit_user";
    private String switchUserUrl = "/j_spring_security_switch_user";
    private String targetUrl;
    private String switchFailureUrl;
    private String usernameParameter = SPRING_SECURITY_SWITCH_USERNAME_KEY;
    private SwitchUserAuthorityChanger switchUserAuthorityChanger;
    private UserDetailsService userDetailsService;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    //~ Methods ========================================================================================================

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(userDetailsService, "userDetailsService must be specified");
        Assert.isTrue(successHandler != null || targetUrl != null, "You must set either a successHandler or the targetUrl");
        if (targetUrl != null) {
            Assert.isNull(successHandler, "You cannot set both successHandler and targetUrl");
            successHandler = new SimpleUrlAuthenticationSuccessHandler(targetUrl);
        }

        if (failureHandler == null) {
            failureHandler = switchFailureUrl == null ? new SimpleUrlAuthenticationFailureHandler() :
                new SimpleUrlAuthenticationFailureHandler(switchFailureUrl);
        } else {
            Assert.isNull(switchFailureUrl, "You cannot set both a switchFailureUrl and a failureHandler");
        }
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // check for switch or exit request
        if (requiresSwitchUser(request)) {
            // if set, attempt switch and store original
            try {
                Authentication targetUser = attemptSwitchUser(request);

                // update the current context to the new target user
                SecurityContextHolder.getContext().setAuthentication(targetUser);

                // redirect to target url
                successHandler.onAuthenticationSuccess(request, response, targetUser);
            } catch (AuthenticationException e) {
                logger.debug("Switch User failed", e);
                failureHandler.onAuthenticationFailure(request, response, e);
            }

            return;
        } else if (requiresExitUser(request)) {
            // get the original authentication object (if exists)
            Authentication originalUser = attemptExitUser(request);

            // update the current context back to the original user
            SecurityContextHolder.getContext().setAuthentication(originalUser);

            // redirect to target url
            successHandler.onAuthenticationSuccess(request, response, originalUser);

            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Attempt to switch to another user. If the user does not exist or is not active, return null.
     *
     * @return The new <code>Authentication</code> request if successfully switched to another user, <code>null</code>
     *         otherwise.
     *
     * @throws UsernameNotFoundException If the target user is not found.
     * @throws LockedException if the account is locked.
     * @throws DisabledException If the target user is disabled.
     * @throws AccountExpiredException If the target user account is expired.
     * @throws CredentialsExpiredException If the target user credentials are expired.
     */
    protected Authentication attemptSwitchUser(HttpServletRequest request) throws AuthenticationException {
        UsernamePasswordAuthenticationToken targetUserRequest;

        String username = request.getParameter(usernameParameter);

        if (username == null) {
            username = "";
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempt to switch to user [" + username + "]");
        }
        
        //当自己切换自己的时候，就返回自己,也就是说 当主用户切换到
        Authentication currentAuth=SecurityContextHolder.getContext().getAuthentication();
        if(isSameUserName(username,currentAuth)){
        	return currentAuth;
        }
        //如果是切换会主用户
        Authentication masterAuth=getMasterAuthentication();
        if(isSameUserName(username, masterAuth)){
        	return masterAuth;
        }

        UserDetails targetUser = userDetailsService.loadUserByUsername(username);
        userDetailsChecker.check(targetUser);

        // OK, create the switch user token
        targetUserRequest = createSwitchUserToken(request, targetUser);

        if (logger.isDebugEnabled()) {
            logger.debug("Switch User Token [" + targetUserRequest + "]");
        }

        // publish event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new AuthenticationSwitchUserEvent(
                    SecurityContextHolder.getContext().getAuthentication(), targetUser));
        }

        return targetUserRequest;
    }

    /**
     * Attempt to exit from an already switched user.
     *
     * @param request The http servlet request
     *
     * @return The original <code>Authentication</code> object or <code>null</code> otherwise.
     *
     * @throws AuthenticationCredentialsNotFoundException If no <code>Authentication</code> associated with this
     *         request.
     */
    protected Authentication attemptExitUser(HttpServletRequest request)
            throws AuthenticationCredentialsNotFoundException {
        // need to check to see if the current user has a SwitchUserGrantedAuthority
        Authentication current = SecurityContextHolder.getContext().getAuthentication();

        if (null == current) {
            throw new AuthenticationCredentialsNotFoundException(messages.getMessage(
                    "SwitchUserFilter.noCurrentUser", "No current user associated with this request"));
        }
        

        // check to see if the current user did actual switch to another user
        // if so, get the original source user so we can switch back
        Authentication original = getSourceAuthentication(current);

//        if (original == null) {
//            logger.debug("Could not find original user Authentication object!");
//            throw new AuthenticationCredentialsNotFoundException(messages.getMessage(
//                    "SwitchUserFilter.noOriginalAuthentication",
//                    "Could not find original Authentication object"));
//        }
      //如果没有主用户，就i傲视当前用户就是主用户
        if (null == original) {
        	return current;
        }

        // get the source user details
        UserDetails originalUser = null;
        Object obj = original.getPrincipal();

        if ((obj != null) && obj instanceof UserDetails) {
            originalUser = (UserDetails) obj;
        }

        // publish event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new AuthenticationSwitchUserEvent(current, originalUser));
        }

        return original;
    }
    
    /**
     * 获取主用户,如果当前用户没有主用户，说明还没有切换过用户，就把当前用户做为主用户
     * @author mawujun email:16064988@163.com qq:16064988
     * @return
     */
    public static Authentication getMasterAuthentication(){
    	//如果本来就是主用户，那就返回主用户
    	Authentication current = SecurityContextHolder.getContext().getAuthentication();
    	// Authentication original = current;
    	Authentication original =current;

         // iterate over granted authorities and find the 'switch user' authority
         Collection<? extends GrantedAuthority> authorities = current.getAuthorities();

         for (GrantedAuthority auth : authorities) {
             // check for switch user type of authority
             if (auth instanceof SwitchUserGrantedAuthorityImpl) {
            	 if(((SwitchUserGrantedAuthorityImpl)auth).isMaster()) {
            		 original = ((SwitchUserGrantedAuthorityImpl) auth).getSource();
                     //logger.debug("Found original switch user granted authority [" + original + "]");
            	 } 
             }
         }

         return original;
    }
    /**
     * 判断当前用户和要切换的用户是不是同个用户
     * @author mawujun email:16064988@163.com qq:16064988
     * @param request
     * @return
     */
    public boolean isSameUserName(String username,Authentication currentAuth){
    	//Authentication currentAuth=SecurityContextHolder.getContext().getAuthentication();
    	// String username = request.getParameter(usernameParameter);
         if(username.equals(currentAuth.getName())){
         	return true;
         } else {
        	 return false;
         }
    }

    /**
     * Create a switch user token that contains an additional <tt>GrantedAuthority</tt> that contains the
     * original <code>Authentication</code> object.
     *
     * @param request The http servlet request.
     * @param targetUser The target user
     *
     * @return The authentication token
     *
     * @see SwitchUserGrantedAuthority
     */
    private UsernamePasswordAuthenticationToken createSwitchUserToken(HttpServletRequest request,
            UserDetails targetUser) {

        UsernamePasswordAuthenticationToken targetUserRequest;

        // grant an additional authority that contains the original Authentication object
        // which will be used to 'exit' from the current switched user.

        Authentication marsterAuth;
        //ParameterEnum.;
//        try {
//            // SEC-1763. Check first if we are already switched.
//            currentAuth = attemptExitUser(request);
//        } catch (AuthenticationCredentialsNotFoundException e) {
//            currentAuth = SecurityContextHolder.getContext().getAuthentication();
//        }
        //获取第一个用户，主用户,都是从主用户出发
        marsterAuth=getSourceAuthentication(SecurityContextHolder.getContext().getAuthentication());
        //没有主用户就把当前用户当做主用户，这个时候就可以确保currentAuth是第一次登陆的用户
        if(marsterAuth==null){
        	marsterAuth= SecurityContextHolder.getContext().getAuthentication();
        } 
        
        
        
        //在其他用户上面添加进主用户的引用
        SwitchUserGrantedAuthorityImpl switchAuthority = new SwitchUserGrantedAuthorityImpl(ROLE_PREVIOUS_ADMINISTRATOR+"_"+marsterAuth.getName(), marsterAuth);
        switchAuthority.setMaster(true);

        // get the original authorities
        Collection<? extends GrantedAuthority> orig = targetUser.getAuthorities();

        // Allow subclasses to change the authorities to be granted
        if (switchUserAuthorityChanger != null) {
            orig = switchUserAuthorityChanger.modifyGrantedAuthorities(targetUser, marsterAuth, orig);
        }

        // add the new switch user authority
        List<GrantedAuthority> newAuths = new ArrayList<GrantedAuthority>(orig);
        //自己赚到自己的时候就不添加
        //boolean isSameUser=targetUser.getUsername().equals(currentAuth.getName());
        //if(!isSameUser){
        	newAuths.add(switchAuthority);
        //}
        

        // create the new authentication token
        targetUserRequest = new UsernamePasswordAuthenticationToken(targetUser, targetUser.getPassword(), newAuths);

        // set details
        targetUserRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        
        //把新切换的用户添加到主用户但中，这样就可以获取到该主用户切换了几个用户
        //主用户里面会存在多个其他用户
        //if(isSameUser){
        	SwitchUserGrantedAuthorityImpl masterAuthority = new SwitchUserGrantedAuthorityImpl(ROLE_PREVIOUS_ADMINISTRATOR+"_"+targetUser.getUsername(), targetUserRequest);
            masterAuthority.setMaster(false);
            Set<GrantedAuthority> masterAuths = new HashSet<GrantedAuthority>(marsterAuth.getAuthorities());
            masterAuths.add(masterAuthority);
            //反射更新到主用户里面去
            ArrayList<GrantedAuthority> aa=new ArrayList<GrantedAuthority>();
            aa.addAll(masterAuths);
            ReflectionUtils.setFieldValue(marsterAuth, "authorities", aa );
        //}
        

      

        return targetUserRequest;
    }

    /**
     * Find the original <code>Authentication</code> object from the current user's granted authorities. A
     * successfully switched user should have a <code>SwitchUserGrantedAuthority</code> that contains the original
     * source user <code>Authentication</code> object.
     *
     * @param current The current  <code>Authentication</code> object
     *
     * @return The source user <code>Authentication</code> object or <code>null</code> otherwise.
     */
    private Authentication getSourceAuthentication(Authentication current) {
        Authentication original = null;

        // iterate over granted authorities and find the 'switch user' authority
        Collection<? extends GrantedAuthority> authorities = current.getAuthorities();

        for (GrantedAuthority auth : authorities) {
            // check for switch user type of authority
            if (auth instanceof SwitchUserGrantedAuthorityImpl) {
//                original = ((SwitchUserGrantedAuthorityImpl) auth).getSource();
//                logger.debug("Found original switch user granted authority [" + original + "]");
            	if(((SwitchUserGrantedAuthorityImpl)auth).isMaster()) {
	           		 original = ((SwitchUserGrantedAuthorityImpl) auth).getSource();
	                 logger.debug("Found original switch user granted authority [" + original + "]");
	           	 } 
            }
        }

        return original;
    }

    /**
     * Checks the request URI for the presence of <tt>exitUserUrl</tt>.
     *
     * @param request The http servlet request
     *
     * @return <code>true</code> if the request requires a exit user, <code>false</code> otherwise.
     *
     * @see SwitchUserFilter#exitUserUrl
     */
    protected boolean requiresExitUser(HttpServletRequest request) {
        String uri = stripUri(request);

        return uri.endsWith(request.getContextPath() + exitUserUrl);
    }

    /**
     * Checks the request URI for the presence of <tt>switchUserUrl</tt>.
     *
     * @param request The http servlet request
     *
     * @return <code>true</code> if the request requires a switch, <code>false</code> otherwise.
     *
     * @see SwitchUserFilter#switchUserUrl
     */
    protected boolean requiresSwitchUser(HttpServletRequest request) {
        String uri = stripUri(request);

        return uri.endsWith(request.getContextPath() + switchUserUrl);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher)
            throws BeansException {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    /**
     * Sets the authentication data access object.
     *
     * @param userDetailsService The <tt>UserDetailService</tt> which will be used to load information for the user
     *                           that is being switched to.
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Set the URL to respond to exit user processing.
     *
     * @param exitUserUrl The exit user URL.
     */
    public void setExitUserUrl(String exitUserUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(exitUserUrl),
                "exitUserUrl cannot be empty and must be a valid redirect URL");
        this.exitUserUrl = exitUserUrl;
    }

    /**
     * Set the URL to respond to switch user processing.
     *
     * @param switchUserUrl The switch user URL.
     */
    public void setSwitchUserUrl(String switchUserUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(switchUserUrl),
                "switchUserUrl cannot be empty and must be a valid redirect URL");
        this.switchUserUrl = switchUserUrl;
    }

    /**
     * Sets the URL to go to after a successful switch / exit user request.
     * Use {@link #setSuccessHandler(AuthenticationSuccessHandler) setSuccessHandler} instead if you need more
     * customized behaviour.
     *
     * @param targetUrl The target url.
     */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
     * Used to define custom behaviour on a successful switch or exit user.
     * <p>
     * Can be used instead of setting <tt>targetUrl</tt>.
     */
    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    /**
     * Sets the URL to which a user should be redirected if the switch fails. For example, this might happen because
     * the account they are attempting to switch to is invalid (the user doesn't exist, account is locked etc).
     * <p>
     * If not set, an error message will be written to the response.
     * <p>
     * Use {@link #setFailureHandler(AuthenticationFailureHandler) failureHandler} instead if you need more
     * customized behaviour.
     *
     * @param switchFailureUrl the url to redirect to.
     */
    public void setSwitchFailureUrl(String switchFailureUrl) {
        Assert.isTrue(StringUtils.hasText(switchUserUrl) && UrlUtils.isValidRedirectUrl(switchFailureUrl),
                "switchFailureUrl cannot be empty and must be a valid redirect URL");
        this.switchFailureUrl = switchFailureUrl;
    }

    /**
     * Used to define custom behaviour when a switch fails.
     * <p>
     * Can be used instead of setting <tt>switchFailureUrl</tt>.
     */
    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    /**
     * @param switchUserAuthorityChanger to use to fine-tune the authorities granted to subclasses (may be null if
     * SwitchUserFilter should not fine-tune the authorities)
     */
    public void setSwitchUserAuthorityChanger(SwitchUserAuthorityChanger switchUserAuthorityChanger) {
        this.switchUserAuthorityChanger = switchUserAuthorityChanger;
    }

    public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
        this.userDetailsChecker = userDetailsChecker;
    }

    /**
     * Allows the parameter containing the username to be customized.
     *
     * @param usernameParameter the parameter name. Defaults to {@code j_username}
     */
    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    /**
     * Strips any content after the ';' in the request URI
     *
     * @param request The http request
     *
     * @return The stripped uri
     */
    private String stripUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int idx = uri.indexOf(';');

        if (idx > 0) {
            uri = uri.substring(0, idx);
        }

        return uri;
    }
}
