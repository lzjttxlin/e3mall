package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCatServiceImpl implements ContentCatService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<TreeNode> addContentCatList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<TreeNode> treeNodes=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	@Override
	public E3Result createContentCat(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		contentCategoryMapper.insert(contentCategory);
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result updateContentCat(long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCat(long id) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		Long parentId = contentCategory.getParentId();
		TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		Boolean isParent = tbContentCategory.getIsParent();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//先判断它是否是一个父节点
			if (contentCategory.getIsParent()) {
				
				return E3Result.ok();
			}
			//说明它里面只有一个子节点
			if (list.size()==1) {
				
				contentCategoryMapper.deleteByPrimaryKey(id);
				tbContentCategory.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
			}else{
				//说明它里面还有子节点
				contentCategoryMapper.deleteByPrimaryKey(id);
			}
		
	
		return E3Result.ok();
	}

}
