package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	@Override
	public DataGridResult getItemList(int page, int rows) {
		//取分页信息
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		DataGridResult result = new DataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		//获取itemid
		long itemId = IDUtils.genItemId();
		//补全属性
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		E3Result e3Result = new E3Result();
		return e3Result.ok();
	}

	@Override
	public E3Result editItem(long id) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		return E3Result.ok(itemDesc);
	}

	@Override
	public E3Result deleteItem(long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		item.setStatus((byte) 3);
		itemMapper.updateByPrimaryKeySelective(item);
		E3Result e3Result = new E3Result();
		return e3Result.ok();
	}

	@Override
	public E3Result instockItem(long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		item.setStatus((byte) 2);
		itemMapper.updateByPrimaryKeySelective(item);
		return E3Result.ok();
	}

}
