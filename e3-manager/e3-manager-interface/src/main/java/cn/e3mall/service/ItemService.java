package cn.e3mall.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
	public TbItem getItemById(long itemId);
	public DataGridResult getItemList(int page,int rows);
	public E3Result addItem(TbItem item,String desc);
	public E3Result editItem(long id);
	public E3Result deleteItem(long id);
	public E3Result instockItem(long id);
	TbItemDesc getTbItemDescById(long itemId);
}
