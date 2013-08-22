package com.mawujun.user.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class SwitchUserGrantedAuthorityImpl implements GrantedAuthority{
	private boolean isMaster;
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    //~ Instance fields ================================================================================================
    private final String role;
    private final Authentication source;

    //~ Constructors ===================================================================================================

    public SwitchUserGrantedAuthorityImpl(String role, Authentication source) {
        this.role = role;
        this.source = source;
    }

    //~ Methods ========================================================================================================

    /**
     * Returns the original user associated with a successful user switch.
     *
     * @return The original <code>Authentication</code> object of the switched user.
     */
    public Authentication getSource() {
        return source;
    }

    public String getAuthority() {
        return role;
    }

    public int hashCode() {
        //return 31 ^ source.hashCode() ^ role.hashCode();
    	return 31 ^  role.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SwitchUserGrantedAuthorityImpl) {
        	SwitchUserGrantedAuthorityImpl swa = (SwitchUserGrantedAuthorityImpl) obj;
            //return this.role.equals(swa.role) && this.source.equals(swa.source);
        	return this.role.equals(swa.role);
        }

        return false;
    }

    public String toString() {
        //return "Switch User Authority [" + role + "," + source + "]" ;
    	return "Switch User Authority [" + role + "]" ;
    }
    
    /**
	 * 判断是不是第一个用户，就是主用户
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

}
