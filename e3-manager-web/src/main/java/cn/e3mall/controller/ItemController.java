package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	
	@RequestMapping("/item/{itemid}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemid) {
		TbItem item = itemService.getItemById(itemid);
		return item;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult getItemList(Integer page,Integer rows){
		DataGridResult dataGridResult = itemService.getItemList(page, rows);
		return dataGridResult;
	}
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item,String desc){
		E3Result e3Result = itemService.addItem(item, desc);
		return e3Result;
	}
	@RequestMapping("/rest/item/delete")
	public E3Result deleteItem(@RequestParam(value="ids")long id){
		//System.out.println(id);
		E3Result e3Result = itemService.deleteItem(id);
		return e3Result;
	}
	@RequestMapping("/rest/item/instock")
	public E3Result instockItem(@RequestParam(value="ids")long id){
		E3Result e3Result = itemService.instockItem(id);
		return e3Result;
	}
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public E3Result editItem(@PathVariable long id){
		System.out.println(id);
		E3Result e3Result = itemService.editItem(id);
		return e3Result;
	}
	
}
