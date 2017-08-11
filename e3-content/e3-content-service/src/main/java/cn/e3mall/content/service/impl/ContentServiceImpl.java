package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public DataGridResult getContentList(long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo=new PageInfo<>(list);
		DataGridResult dataGridResult = new DataGridResult();
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(list);
		return dataGridResult;
	}

	@Override
	public E3Result addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return E3Result.ok();
	}

	@Override
	public E3Result editContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContent(long id) {
		contentMapper.deleteByPrimaryKey(id);
		return E3Result.ok();
	}

	@Override
	public List<TbContent> showIndexContentById(long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		return list;
	}

}
