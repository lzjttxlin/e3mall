package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;

@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> addContentCatList(@RequestParam(value="id",defaultValue="0")long parentId){
		List<TreeNode> contentCatList = contentCatService.addContentCatList(parentId);
		return contentCatList;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result createContentCat(long parentId,String name){
		E3Result e3Result = contentCatService.createContentCat(parentId, name);
		return e3Result;
	}
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCat(long id,String name){
		E3Result e3Result = contentCatService.updateContentCat(id, name);
		return e3Result;
	}
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public E3Result deleteContentCat(long id){
		E3Result e3Result = contentCatService.deleteContentCat(id);
		return e3Result;
	}
}
