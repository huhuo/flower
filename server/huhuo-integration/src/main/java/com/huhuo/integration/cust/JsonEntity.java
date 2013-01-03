package com.huhuo.integration.cust;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant.DateFormat;

/**
 * entity parent class need to be serial as json string
 * @author wuyuxuan
 */
public class JsonEntity {

	@Override
	public String toString() {
		return JSONObject.toJSONStringWithDateFormat(this, DateFormat.LONG_FORMAT);
	}

}
