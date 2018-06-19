package com.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
* @ClassName: HelloController  
* <p>Description: 单个文件、多个文件上传下载  </p>
* http://localhost:8080/HelloSpringMVC/hello/mvc
* 类级别的RequestMapping，告诉DispatcherServlet由这个类负责处理以及URL。HandlerMapping依靠这个标签来工作
* @date 2018年6月19日 下午4:23:30  
*
 */
@Controller  
@RequestMapping(value="/hello")  
public class HelloController {
	
	private static Logger log = LoggerFactory.getLogger(HelloController.class);
		
	
	    /**
	     * 
	    * <p>Title: HelloWorld  </p>
	    * Description: 
	    * 方法级别的RequestMapping， 限制并缩小了URL路径匹配，同类级别的标签协同工作，最终确定拦截到的URL由那个方法处理
	    * 并指定访问方法为GET
	    * 视图渲染，/WEB-INF/view/HelloWorld.jsp
	    * @date 2018年6月19日 下午4:24:47  
	    * @param model
	    * @return
	     */
	    @RequestMapping(value="/mvc",method=RequestMethod.GET)  
	    public String HelloWorld(Model model){  
		  
	        model.addAttribute("message","Hello Spring MVC!!!");  //传参数给前端

	        return "HelloWorld";  
	    }  
	    
	    /**
	     * 
	    * <p>Title: showUploadPage  </p>
	    * Description: 定位到上传文件界面 /hello/upload
	    * @date 2018年6月19日 下午4:24:25  
	    * @return
	     */
		@RequestMapping(value="/upload", method=RequestMethod.GET)
		public String showUploadPage(){	
			return "uploadFile";
		}
		
		/**
		 * 
		* <p>Title: doUploadFile  </p>
		* Description: 上传单个文件操作  这里将上传得到的文件保存至 d:\\temp\\file 目录
		* @date 2018年6月19日 下午4:27:27  
		* @param file
		* @return
		 */
		@RequestMapping(value="/doUpload", method=RequestMethod.POST)
		public String doUploadFile(@RequestParam("file") MultipartFile file){

			if(!file.isEmpty()){
				log.debug("Process file: {}", file.getOriginalFilename());
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File("d:\\temp\\file\\", 
							System.currentTimeMillis()+ file.getOriginalFilename()));
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e.toString());
				}
				
			}

			return "success";
		}
		/**
		 * 
		* <p>Title: showUploadPage2  </p>
		* Description: 多个文件上传界面 /hello/uploadMulti  view文件夹下的上传多个文件的页面
		* @date 2018年6月19日 下午4:26:26  
		* @return
		 */
		@RequestMapping(value="/uploadMulti", method=RequestMethod.GET)
		public String showUploadPage2(){	
			return "uploadMultifile";
		}
		
		/**
		 * 
		* <p>Title: doUploadFile2  </p>
		* Description: 上传多个附件的操作类
		* @date 2018年6月19日 下午4:27:00  
		* @param multiRequest
		* @return
		* @throws IOException
		 */
		@RequestMapping(value="/doMultiUpload", method=RequestMethod.POST)
		public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws IOException{

			Iterator<String> filesNames = multiRequest.getFileNames();
			while(filesNames.hasNext()){
				String fileName =filesNames.next();
				MultipartFile file =  multiRequest.getFile(fileName);
				if(!file.isEmpty()){
					log.debug("Process file: {}", file.getOriginalFilename());
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File("d:\\temp\\file\\", 
							System.currentTimeMillis()+ file.getOriginalFilename()));
				}

			}
			return "success";
		}
		
}
