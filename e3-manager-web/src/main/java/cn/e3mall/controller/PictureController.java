package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {
	
	@Value("${image.url}")
	private String imageUrl;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile){
		
		try {
			//获取原始文件
			String originalFilename = uploadFile.getOriginalFilename();
			//获取原始文件的文件扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//获取配置文件
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.properties");
			//上传文件，获得文件路径
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url=imageUrl+url;
			//假如添加成功就会给页面返回一个信心，而这个信息是通过map进行封装
			Map map=new HashMap<>();
			map.put("error", 0);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			Map map=new HashMap<>();
			map.put("error", 1);
			map.put("message", "上传图片失败");
			return JsonUtils.objectToJson(map);
		}
		
	}
}
