package com.gec.demotest;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.h2.util.New;
import org.junit.Test;

public class DemoTest13 {
	
	// 流程引擎对象
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.流程部署
	@Test
	public void fun1() {
		
		//小弟(各种服务)
		//返回得到部署对象
//		InputStream in = new FileInputStream(file);
		
		//通过反射方式将一个文件转为inputstream
//		InputStream in = this.getClass().getClassLoader().getResourceAsStream("digram/helloworldProcess.zip");
//		ZipInputStream zipInputStream = new ZipInputStream(in);
		
		
		Deployment deployment = processEngine.getRepositoryService()
		.createDeployment()
		.name("排他网关测试")
		.addClasspathResource("digram/ExclusiveProcessTest.bpmn")
		.addClasspathResource("digram/ExclusiveProcessTest.png")
		.deploy();
		
		System.out.println("部署id:"+deployment.getId());
		System.out.println("部署name:"+deployment.getName());
		
	}
	
	
	//2.启动流程(从开始推向到第一个任务这里来)
	@Test
	public void fun2(){
		String processDefinitionKey = "ExclusiveProcessTest";
		
		ProcessInstance processInstance = processEngine.getRuntimeService()
		.startProcessInstanceByKey(processDefinitionKey);	
		
		System.out.println("启动流程成功...");
	}
	
	
	
	//3.完成任务 complete(String taskId)2303
	@Test
	public void fun4(){
		String taskId = "1104";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("money", 5000);
		this.processEngine.getTaskService()
		.complete(taskId, map);
		System.out.println("任务已经完成...");
	}
	
	
	//5.获取到流程变量的值
	@Test
	public void getValiableTest() {
		String taskId="2002";
		Object value = this.processEngine
		.getTaskService()
		.getVariable(taskId, "金额");
		System.out.println("获取到的流程变量的值:"+value);
		
		Object value1 = this.processEngine
		.getTaskService()
		.getVariable(taskId, "日期");
		System.out.println("获取到的流程变量的值:"+value1);
	}
	
	
}
