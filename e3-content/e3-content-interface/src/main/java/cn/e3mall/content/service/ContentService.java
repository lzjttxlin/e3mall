package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {
	DataGridResult getContentList(long categoryId,Integer page,Integer rows);
	E3Result addContent(TbContent content);
	E3Result editContent(TbContent content);
	E3Result deleteContent(long id);
	List<TbContent> showIndexContentById(long categoryId);
}
