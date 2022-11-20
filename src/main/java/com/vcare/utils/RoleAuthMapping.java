package com.vcare.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RoleAuthMapping {
	public static List<String> AdminMap = new ArrayList<>();
	public static List<String> ManagerMap = new ArrayList<>();
	public static List<String> createUserRole = new ArrayList<>();
	public static List<String> editRole = new ArrayList<>();
	
	{
		String catBaseURL = "/api/category/";
		AdminMap.add(catBaseURL + "get");
		AdminMap.add(catBaseURL + "save");
		AdminMap.add(catBaseURL + "edit");
		String callBaseURL = "/api/calltype/";
		AdminMap.add(callBaseURL + "get");
		AdminMap.add(callBaseURL + "save");
		AdminMap.add(callBaseURL + "edit");
		String clientBaseURL = "/api/client/";
		AdminMap.add(clientBaseURL + "get");
		AdminMap.add(clientBaseURL + "save");
		AdminMap.add(clientBaseURL + "edit");
		String employeeBaseURL = "/api/employee/";
		AdminMap.add(employeeBaseURL + "get");
		AdminMap.add(employeeBaseURL + "save");
		AdminMap.add(employeeBaseURL + "edit");
		String entryBase = "/api/entry/";
		ManagerMap.add(catBaseURL + "get");
		ManagerMap.add(catBaseURL + "save");
		ManagerMap.add(catBaseURL + "edit");
		ManagerMap.add(callBaseURL + "get");
		ManagerMap.add(callBaseURL + "save");
		ManagerMap.add(callBaseURL + "edit");
		ManagerMap.add(clientBaseURL + "get");
		ManagerMap.add(clientBaseURL + "save");
		ManagerMap.add(employeeBaseURL + "get");
		ManagerMap.add(employeeBaseURL + "save");
		ManagerMap.add(entryBase + "get/data");
		createUserRole.add("admin");
		editRole.add("admin");
		editRole.add("manager");
	}
}
