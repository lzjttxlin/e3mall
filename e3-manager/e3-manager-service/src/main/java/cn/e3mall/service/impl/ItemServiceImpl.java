package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
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
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	@Value("${item.expire}")
	private Integer itemExpire;
	
	@Override
	public TbItem getItemById(long itemId) {
		//先从缓存中查找
		try {
			String json = jedisClient.get("item-info:"+itemId+":base");
			if (StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		//添加缓存
		try {
			jedisClient.set("item-info:"+itemId+":base", JsonUtils.objectToJson(tbItem));
			jedisClient.expire("item-info:"+itemId+":base", itemExpire);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		final long itemId = IDUtils.genItemId();
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
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
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

	@Override
	public TbItemDesc getTbItemDescById(long itemId) {
		//先从缓存中查找
				try {
					String json = jedisClient.get("item-info:"+itemId+":desc");
					if (StringUtils.isNotBlank(json)) {
						TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
						return tbItemDesc;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//添加缓存
				try {
					jedisClient.set("item-info:"+itemId+":desc", JsonUtils.objectToJson(tbItemDesc));
					jedisClient.expire("item-info:"+itemId+":desc", itemExpire);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		return tbItemDesc;
	}

}
