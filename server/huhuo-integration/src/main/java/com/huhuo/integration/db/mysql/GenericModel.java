package com.huhuo.integration.db.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huhuo.integration.cust.JsonEntity;

public abstract class GenericModel extends JsonEntity implements IModel<Long> {

	private static final long serialVersionUID = 8669799836108015546L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * primary key 测试哦
	 */
	protected Long id;
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	
}
