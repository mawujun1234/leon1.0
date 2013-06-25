package com.mawujun.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.exten.TreeNode;
import com.mawujun.fun.Fun;

@Entity
@Table(name="leon_Role")
public class Role extends TreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=40)
	private String name;
	@Column(length=100)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(length=10,nullable=false)
	private RoleEnum roleEnum;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Role category;
	
//	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
//	private List<RoleFunAssociation> roleFunAssociations;
	
//	//所对应的都是父角色或者是护斥的角色
//	@OneToMany(mappedBy="current",fetch=FetchType.LAZY)
//	private List<RoleRole> currents=new ArrayList<RoleRole>();
//	
//	//所对应的都是子角色或者是护斥的角色
//	@OneToMany(mappedBy="other",fetch=FetchType.LAZY)
//	private List<RoleRole> others=new ArrayList<RoleRole>();
	
	@ManyToMany(cascade = CascadeType.REFRESH) 
	@JoinTable(
			name = "leon_role_inherit",
			inverseJoinColumns = @JoinColumn(name = "parent_id"), 
			joinColumns = @JoinColumn(name = "child_id")
	) 
	Set<Role> parents=new HashSet<Role>();
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="parents")
	Set<Role> children=new HashSet<Role>();
	
	@ManyToMany(cascade = CascadeType.REFRESH) 
	@JoinTable(
			name = "leon_role_mutex",
			inverseJoinColumns = @JoinColumn(name = "own_id"), 
			joinColumns = @JoinColumn(name = "mutex_id")
	) 
	Set<Role> mutex=new HashSet<Role>();
	
	//拥有的权限
	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
	private List<RoleFun> funes=new ArrayList<RoleFun>();
	
	public Role(){
		
	}
	public Role(String id){
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RoleEnum getRoleEnum() {
		return roleEnum;
	}
	public void setRoleEnum(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}
	
	public void setRoleEnum(String roleEnum) {
		this.roleEnum = RoleEnum.valueOf(roleEnum);
	}
	
	public String getIconCls() {
		//System.out.println(funEnum);
		if(RoleEnum.roleCategory==roleEnum){
			return "role-category-iconCls";
		} else if(RoleEnum.role==roleEnum){
			return "role-role-iconCls";
		}
		return null;
	}
	public boolean isLeaf() {
		 if(RoleEnum.role==roleEnum){
			return true;
		}
		 return false;
	}
	public Role getCategory() {
		return category;
	}
	public void setCategory(Role category) {
		this.category = category;
	}
	public Set<Role> getParents() {
		return parents;
	}
	public void setParents(Set<Role> parents) {
		this.parents = parents;
	}
	public void addParent(Role parent) {
		this.parents.add(parent);
	}
	public void removeParent(Role parent) {
		this.parents.remove(parent);
	}
	public Set<Role> getChildren() {
		return children;
	}
	public void setChildren(Set<Role> children) {
		this.children = children;
	}
	public void addChild(Role child) {
		this.children.add(child);
	}
	public void removeChild(Role child) {
		this.children.remove(child);
	}
	public Set<Role> getMutex() {
		return mutex;
	}
	public void setMutex(Set<Role> mutex) {
		this.mutex = mutex;
	}
	public void addMutex(Role mutex) {
		this.mutex.add(mutex);
	}
	public void removeMutex(Role child) {
		this.mutex.remove(child);
	}
	

	/**
	 * 判断参数节点是否是当前节点的祖先节点了
	 * @author mawujun 16064988@qq.com 
	 * @param parent
	 * @return
	 */
	public boolean isAncestor(Role ancertor) {
		if (this.getId().equals(ancertor.getId())) {
			return true;
		}
		for (Role parent : this.parents) {
			if (parent.getId().equals(ancertor.getId())) {
				return true;
			} else {
				return parent.isAncestor(ancertor);
			}
		}
		return false;
	}
	/**
	 * 判断参数节点是否是当前节点的子孙节点了
	 * @author mawujun 16064988@qq.com 
	 * @param category
	 * @return
	 */
	public boolean isChild(Role grandson){
		if(this.getId().equals(grandson.getId())){
			return true;
		}
		for (Role child : this.children) {
			if (child.getId().equals(grandson.getId())) {
				return true;
			} else {
				return child.isChild(grandson);
			}
		}
		return false;
	}
	/**
	 * 判断是否具有继承关系了，无论是孙子还是祖先都返回true
	 * @author mawujun 16064988@qq.com 
	 * @param ancertor
	 * @return
	 */
	public boolean isInherit(Role inherit) {
		return isChild(inherit)?true:isAncestor(inherit);
	}
	
	/**
	 * 判断父角色冲是否有排斥
	 * @author mawujun 16064988@qq.com 
	 * @param mutex
	 * @return
	 */
	public boolean isMutexAncestor(Role mutex) {
		for (Role parent : this.parents) {
			return parent.isMutexAncestor(mutex);
		}
		
		return false;
	}
	/**
	 * 判断子角色中 有没有冲突，
	 * @author mawujun 16064988@qq.com 
	 * @param mutex
	 * @return
	 */
	public boolean isMutexGrandson(Role mutex) {
		for (Role child : this.children) {
			return child.isMutexGrandson(mutex);
		}
		
		return false;
	}
	/**
	 * 只判断 是否和这个角色冲突
	 * @author mawujun 16064988@qq.com 
	 * @param mutex
	 * @return
	 */
	public boolean isMutexOwn(Role mutex) {
		for(Role role:this.mutex) {
			if (role.getId().equals(mutex.getId())) {
				return true;
			} 
		}
		return false;
	}
	/**
	 * 判断是否排斥，包括父类和子类，都要检查
	 * @author mawujun 16064988@qq.com 
	 * @param mutex
	 * @return
	 */
	public boolean isMutex(Role mutex) {
		if(isMutexOwn(mutex)){
			return true;
		}
		if(isMutexAncestor(mutex)){
			return true;
		}
		if(isMutexGrandson(mutex)){
			return true;
		}
		return false;
	}
	public List<RoleFun> getFunes() {
		return funes;
	}
	/**
	 * 获取经过计算后的权限
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public List<FunRoleVO> geetFunes() {
		//首先获取父角色的权限,如果有多个父角色就要先进行父角色的 冲突处理
		List<FunRoleVO> funRoleVOs_parent=new ArrayList<FunRoleVO>();
		Map<String,ArrayList<FunRoleVO>> map=new HashMap<String,ArrayList<FunRoleVO>>();
		if(this.getParents().size()>0){
			for(Role parent:this.getParents()){
				List<FunRoleVO> parentFunes=parent.geetFunes();
				for(FunRoleVO funRoleVO:parentFunes){
					if (!map.containsKey(funRoleVO.getFunId())) {
						map.put(funRoleVO.getFunId(),new ArrayList<FunRoleVO>());
					}

					map.get(funRoleVO.getFunId()).add(funRoleVO);
				}
			}
			
			//进行父角色的权限冲突处理
			if (RoleCacheHolder.getAccessDecisionEnum() == AccessDecisionEnum.AffirmativeBased) {
				funRoleVOs_parent= calAffirmativeBased(map);
			}
		}
		
		Map<String,Integer> nowFunIds_parent=new HashMap<String,Integer>();
		int aaaaIndex=0;
		for(FunRoleVO funRoleVO_parent:funRoleVOs_parent){
			nowFunIds_parent.put(funRoleVO_parent.getFunId(), aaaaIndex);
			aaaaIndex++;
		}
		List<FunRoleVO> funRoleVOs=new ArrayList<FunRoleVO>();
		//boolean haveSame=false;
		for(RoleFun roleFun:getFunes()){
			String funId=roleFun.getFun().getId();
			FunRoleVO roleFunVo=new FunRoleVO();
			roleFunVo.setFunId(funId);
			roleFunVo.setPermissionEnum(roleFun.getPermissionEnum());
			
			RoleSource roleVO=new RoleSource();
			roleVO.setId(roleFun.getRole().getId());
			roleVO.setName(roleFun.getRole().getName());
			roleVO.setPermissionEnum(roleFun.getPermissionEnum());
			roleVO.setSelf(true);
			roleFunVo.addRoleSource(roleVO);
			
			if(nowFunIds_parent.containsKey(funId)){
				//roleFunVo.addRoles(funRoleVOs_parent.get(nowFunIds_parent.get(funId)).getRoles())
				//把父节点的的角色加进来
				roleFunVo.getRoleSources().addAll(funRoleVOs_parent.get(nowFunIds_parent.get(funId)).getRoleSources());
				nowFunIds_parent.remove(roleFun.getFun().getId());
			}
			funRoleVOs.add(roleFunVo);
		}
		//把父节点的加进来
		for(Entry<String,Integer> entry:nowFunIds_parent.entrySet()){
			//这里判断这个权限是否是继承过来的，加一个字段？？？？？？？？？？？？？？？？？
			funRoleVOs.add(funRoleVOs_parent.get(entry.getValue()));
		}
		
		return funRoleVOs;
		
	}

	/**
	 * 有一个允许就允许
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param map
	 * @return
	 */
	private List<FunRoleVO> calAffirmativeBased(Map<String,ArrayList<FunRoleVO>> map) {
		//对于某个功能来说 只要有一个角色是公有权限，那这个功能就要返回
		//如果都是拒绝的话，那就要返回拒绝，新建一个RoleFunVO，里面有funId，权限类型：PUBLIC,DENY，还有这个功能从哪个角色来的lIST<Role>
		List<FunRoleVO> funRoleVOs=new ArrayList<FunRoleVO>();
		for(String funId:map.keySet()){
			//经过计算后的权限存放
			FunRoleVO roleFunVo=new FunRoleVO();
			roleFunVo.setFunId(funId);
			//默认的权限类型是PRIVATE
			roleFunVo.setPRIVATE();
			boolean ispublic=false;
			boolean isDeny=false;
			for(FunRoleVO roleFunVO_temp:map.get(funId)){
				if(roleFunVO_temp.getPermissionEnum()==PermissionEnum.PUBLIC){
					ispublic=true;
					//如果权限是私有的话，就不继承过来
					//这里角色的权限有冲突怎么办？？？？？？？？？？？？？？？？？
					roleFunVo.getRoleSources().addAll( roleFunVO_temp.getRoleSources());
				} else if(roleFunVO_temp.getPermissionEnum()==PermissionEnum.DENY){
					isDeny=true;
					//如果权限是私有的话，就不继承过来
					roleFunVo.getRoleSources().addAll( roleFunVO_temp.getRoleSources());
				}
				
			}
			if(ispublic){
				roleFunVo.setPUBLIC();
				funRoleVOs.add(roleFunVo);
			} else if(isDeny){
				roleFunVo.setDENY();
				funRoleVOs.add(roleFunVo);
			}
			
			
		}
		return funRoleVOs;
	}
	
	public void setFunes(List<RoleFun> funes) {
		this.funes = funes;
	}
	
	public void addFun(RoleFun rolefun) {
		this.funes.add(rolefun);
	}
	
	public void removeFun(RoleFun rolefun) {
		this.funes.remove(rolefun);
	}
	
	public RoleFun removeFun(Fun fun) {
		RoleFun aa=null;
		for(RoleFun rolefun:this.funes){
			if(rolefun.getFun().getId().equals(fun.getId())){
				aa=rolefun;
				break;
			}
		}
		if(aa!=null){
			this.funes.remove(aa);
		}
		return aa;
	}
	public RoleFun removeFun(String funId) {
		RoleFun aa=null;
		for(RoleFun rolefun:this.funes){
			if(rolefun.getFun().getId().equals(funId)){
				aa=rolefun;
				break;
			}
		}
		if(aa!=null){
			this.funes.remove(aa);
		}
		return aa;
	}

}
