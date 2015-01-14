package com.retropoktan.lshousekeeping.constant;

public class UrlConst {

	// get method
	public static final String BaseUrl = "http://120.27.37.53";
	public static final String ApiVersion = "";
	public static final String BaseApiUrl = BaseUrl + ApiVersion;
	public static final String GetAdUrl = BaseApiUrl + "/getad/";
	public static final String GetAreaTelUrl = BaseApiUrl + "/area_tel/";
	public static final String GetCategoryUrl = BaseApiUrl + "/getcategory/";
	public static final String GetDetailItemUrl = BaseApiUrl + "/getitem/?category_id=";
	// post method
	public static final String GetNearestCityUrl = BaseApiUrl + "/getnearest/";
	public static final String MakeOrderUrl = BaseApiUrl + "/mkappoint/";
	
}
