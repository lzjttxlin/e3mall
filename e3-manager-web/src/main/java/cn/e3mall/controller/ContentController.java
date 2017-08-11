package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGridResult getContentList(long categoryId,Integer page,Integer rows){
		DataGridResult dataGridResult = contentService.getContentList(categoryId, page, rows);
		return dataGridResult;
	}
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content){
		E3Result e3Result = contentService.addContent(content);
		return e3Result;
	}
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result editContent(TbContent content){
		E3Result e3Result = contentService.editContent(content);
		return e3Result;
	}
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(@RequestParam(value="ids") long id){
		E3Result e3Result = contentService.deleteContent(id);
		return e3Result;
	}
	
}
