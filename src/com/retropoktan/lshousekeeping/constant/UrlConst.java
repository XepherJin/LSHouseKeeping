package com.retropoktan.lshousekeeping.constant;

public class UrlConst {

	// get method
	public static final String BaseUrl = "http://115.29.138.80";
	public static final String ApiVersion = "";
	public static final String BaseApiUrl = BaseUrl + ApiVersion;
	public static final String GetAdUrl = BaseApiUrl + "/getad/";
	public static final String GetAreaTelUrl = BaseApiUrl + "/area_tel/";
	public static final String GetCategoryUrl = BaseApiUrl + "/getcategory/";
	public static final String GetDetailItemUrl = BaseApiUrl + "/getitem/?category_id=";
	// post method
	public static final String GetNearestCityUrl = BaseApiUrl + "/getnearest/";
	public static final String MakeOrderUrl = BaseApiUrl + "/mkappoint/";
	public static final String GetVerifyUrl = BaseApiUrl + "/getverify/";
	public static final String PhoneVerifyUrl = BaseApiUrl + "/phoneverify/";
	public static final String AppointmentPicUrl = BaseApiUrl + "/appointpic/";
}
