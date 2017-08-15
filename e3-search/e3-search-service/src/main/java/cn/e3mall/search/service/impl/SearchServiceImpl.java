package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchItemDao;
import cn.e3mall.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchItemDao searchItemDao;
	
	
	@Override
	public SearchResult search(String keyWords, Integer page, Integer rows) throws Exception {
		//创建一个solrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(keyWords);
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		//调用dao执行查询
		SearchResult searchResult = searchItemDao.search(query);
		long recourdCount = searchResult.getRecourdCount();
		long pageTotals=recourdCount/rows;
		if (recourdCount%rows!=0) {
			pageTotals=pageTotals+1;
		}
		searchResult.setTotalPages(pageTotals);
		return searchResult;
	}

}
