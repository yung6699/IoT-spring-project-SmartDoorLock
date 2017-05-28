package com.sl.web.tags;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SelectTag extends TagSupport{
	/*
	<select id="test" class="form-control" tabindex="-1" style="width: 100%" >
		<option></option>
	</select>

	<script>
	$(document).ready(function() {
		$.ajax({
		    type : "POST",
		    url : "/tag/select",
		    cache : false,
		    async : true,
		    data : {g_code:"PAGE_SELECT_CATEGORY_TYPE"},
		    dataType : "json",
		    success : function(response) {
		      $("#test").select2({data:response});
		    }
		});
	});
	</script>  
	*/
	
	private static final long serialVersionUID = -6832008556056624674L;
	/**
	 @Param1 : id  [필수]
	 html 태그 상의 id 값으로 쓰일 예정이다
	 form 태그에 사용될 name 값을 적기 위해 필요하다.
	 @Param2 : g_code [필수]
	 page_select_xxxxxx  여기에서 xxxx 값에 의해 DataBase 에 있는 그룹코드의 내용을 가져 오도록 한다.
	 @Param3 : className
	 사용자가 별도로 클래스를 추가 하고 싶다면 여기에서 가져다 쓰면 된다.
	 @param4 : style
	 사용자가 별도로 style을 추가하고 싶다면 작성해서 쓰면 된다.
 	 @param5 : onChange
 	 사용자가 onChange의 메서드를 넣고 싶다면 여기에 삽입하면 된다.
	 */
	
	private String id=""; 
	private String g_code="";
	private String className="";
	private String style="";
	private String onChange="";
	private String state="";
	
	private static StringBuffer sb = null;

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try{
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			ServletContext app = request.getSession().getServletContext();
			
			JspWriter out = pageContext.getOut();
			sb = new StringBuffer();

			sb.append("<select ");
			if(!(getId().equals("")||getId().isEmpty())){
				sb.append("id='"+getId()+"' name='"+getId()+"' ");
			}
			if(!(getOnChange().equals("")||getOnChange().isEmpty())){
				sb.append("onChange='"+getOnChange()+"' ");
			}
			if(!(getStyle().equals("")||getStyle().isEmpty())){
				sb.append("style='width: 100%; "+getStyle()+"' ");
			}else{
				sb.append("style='width: 100%; ");
			}
			if(!(getClassName().equals("")||getClassName().isEmpty())){
				sb.append("class='select2_single form-control "+getClassName()+"'");
			}else{
				sb.append("class='select2_single form-control'");	
			}
			
			if(!(getState().equals("")||getState().isEmpty())){
				sb.append(getState());
			}
			sb.append(">\n");
			appendln("<option></option>");
			appendln("</select>");
			

			//자바 스크립트 구문
			appendln("<script>");
			appendln("$(document).ready(function() {");
			appendln("		$.ajax({");
			appendln("		    type : 'POST',");
			appendln("		    url : '/tag/select',");
			appendln("		    cache : false,");
			appendln("		    async : true,");
			if(!(getG_code().equals("")||getG_code().isEmpty())){
				appendln("		    allowClear: true, placeholder: '카테고리의 타입을 선택하세요',data : {g_code:'"+getG_code()+"'},");
			}
			appendln("		    dataType : 'json',");
			appendln("		    success : function(res) {");
			appendln("		    	$('#"+getId()+"').select2({data:res});");
			appendln("		    }");
			appendln("		});");
			appendln("});");
			appendln("</script>");

			out.print(sb.toString());
		}catch(IOException ioe){
			ioe.printStackTrace();
			throw new JspException("Error: "+ ioe.getMessage());
		}
		
		
		return EVAL_BODY_INCLUDE;
	}

	
	
	public void appendln(String str) {
		sb.append(str).append("\n");
	}
	
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getG_code() {
		return g_code;
	}

	public void setG_code(String g_code) {
		this.g_code = g_code;
	}


	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}	
}

