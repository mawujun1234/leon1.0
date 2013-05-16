package com.mawujun.repository.idEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import com.mawujun.annotation.Label;

/**
 * 要对Entity类进行改造。因为Oracle一般使用SEQUCENCE作为主键生成策略，而且每种Entity类使用一个独立的Sequence。
 * 此时统一的IdEntity基类就不再合适了，最好把它变为一个Id接口，然后在每个Entity中定义id及其Sequence。
 * 注意这里有个性能优化的地方，Hibernate一次问Oracle要了20个id自己慢慢用。相应的，sequence创建时需要修改increment by=20
 * create sequence SEQ_USER start with 100 increment by 20;这是要在oracle中执行的

 * @author mawujun
 *
 */
@MappedSuperclass
public class OracleAutoId implements IdEntity<Long> {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserSequence")
	@SequenceGenerator(name = "UserSequence", sequenceName = "SEQ_USER", allocationSize=20)
	@Label(name="id")
	private Long id;

	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
