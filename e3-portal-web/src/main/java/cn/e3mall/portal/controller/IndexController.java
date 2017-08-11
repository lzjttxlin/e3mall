package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 页面信息展示
 * @author pc
 *
 */
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@Value("${index.slider.cid}")
	private Long indexSilderCid;

	@RequestMapping("/index")
	public String showIndex(Model model){
		List<TbContent> list = contentService.showIndexContentById(indexSilderCid);
		//把结果传递给jsp
		model.addAttribute("ad1List", list);
		//返回逻辑视图
		return "index";
	}
}
