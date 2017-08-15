package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchSeavice;
	@Value("${page_num}")
	private Integer pageNum;
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception{
		keyword=new String(keyword.getBytes("iso8859-1"), "utf-8");
		SearchResult searchResult = searchSeavice.search(keyword, page, pageNum);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecourdCount());
		model.addAttribute("itemList", searchResult.getItemList());
		return "search";
	}
}
