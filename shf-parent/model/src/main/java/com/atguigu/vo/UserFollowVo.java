package com.atguigu.vo;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@ToString
public class UserFollowVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long houseId;
	private Date createTime;
	private String communityName;
	private String name;
	private String buildArea;
	private BigDecimal totalPrice;
	private String defaultImageUrl;
	private Long houseTypeId;
	private Long floorId;
	private Long directionId;

	private String houseTypeName;
	private String floorName;
	private String directionName;

	public String getCreateTimeString() {
		Date date = this.getCreateTime();
		if(null == date) return "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}
}

