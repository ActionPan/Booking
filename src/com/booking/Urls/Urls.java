package com.booking.Urls;

import org.json.JSONArray;

public class Urls {
	static String URL="http://10.10.16.141:3000";
	
	public static String UserName=null;
	public static String CompanyName=null;
	public static String lobby_num=null;
	public Urls(){
		super();
		this.URL=URL;
		this.UserName=UserName;
		this.CompanyName=CompanyName;
		this.lobby_num=lobby_num;
	}
	public Urls(String URL,String UserName,String CompanyName,String lobby_num){
		this.URL=URL;
		this.UserName=UserName;
		this.CompanyName=CompanyName;
		this.lobby_num=lobby_num;
			}
	public static String getURL(){
		return URL;
	}
	public void setURL(String URL){
		this.URL=URL;
	}
	
	public static String getUserName(){
		return UserName;
	}
	public void setUserName(String UserName){
		this.UserName=UserName;
	}
	public static String getCompanyName(){
		return CompanyName;
	}
	public void setCompanyName(String CompanyName){
		this.CompanyName=CompanyName;
	}
	public static String getlobby_num(){
		return lobby_num;
	}
	public void setlobby_num(String lobby_num){
		this.lobby_num=lobby_num;
	}
}
