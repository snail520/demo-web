package com.lezhi.demo.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lezhi.demo.biz.service.LoginService;
import com.lezhi.demo.model.validation.User;


@Controller
@RequestMapping("")
public class LoginAction  extends BaseAction {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	public void login(String userName,String password,HttpServletResponse response,HttpServletRequest request){
		String msg="";
		JSONObject json = new JSONObject();
		try {
				User user = loginService.findUser(userName);
				if(null != user){
					 if(password.equals(user.getPassword())){
			 			msg="登录成功！";
			 			json.put("state", "0");
					 }else{
						msg="密码错误！";
						json.put("state", "2");
					 }
				}
				else{
					msg="用户不存在！";
					json.put("state", "1");
				}	
			json.put("msg", msg);
			response.setContentType("application/json");  
			response.setCharacterEncoding("UTF-8");
          	PrintWriter writer = response.getWriter();
          	writer.write(json.toString());
          	writer.flush();
          	writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/userRegister",method=RequestMethod.POST)
	public void userRegister(String userName,String password,HttpServletResponse response,HttpServletRequest request){
		String msg="";
		JSONObject json = new JSONObject();
		try {
			  User user = new User();
			  user.setUserName(userName);
			  user.setPassword(password);
			  boolean result = loginService.insert(user);
				if(result){
			 			msg="注册成功！";
			 			json.put("state", "0");
				}else{
					msg="注册失败！";
					json.put("state", "1");
				}	
			json.put("msg", msg);
			response.setContentType("application/json");  
			response.setCharacterEncoding("UTF-8");
          	PrintWriter writer = response.getWriter();
          	writer.write(json.toString());
          	writer.flush();
          	writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/userDelete",method=RequestMethod.POST)
	public void userDelete(String id,HttpServletResponse response,HttpServletRequest request){
		String msg="";
		JSONObject json = new JSONObject();
		try {
			  boolean result = loginService.deleteUser(id);
				if(result){
			 			msg="删除成功！";
			 			json.put("state", "0");
				}else{
					msg="删除失败！";
					json.put("state", "1");
				}	
			json.put("msg", msg);
			response.setContentType("application/json");  
			response.setCharacterEncoding("UTF-8");
          	PrintWriter writer = response.getWriter();
          	writer.write(json.toString());
          	writer.flush();
          	writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/login" )
	public ModelAndView login(String userId,HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/login");
		return mv;
	}

}
