package cn.e3mall.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(long itemId);
	public DataGridResult getItemList(int page,int rows);
}
