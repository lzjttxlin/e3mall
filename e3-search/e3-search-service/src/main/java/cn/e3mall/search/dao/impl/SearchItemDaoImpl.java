package cn.e3mall.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchItemDao;

@Repository
public class SearchItemDaoImpl implements SearchItemDao {
	
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult search(SolrQuery solrQuery) throws Exception {
		//根据solrQuery进行查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//根据queryResponse取得查询结果
		SolrDocumentList list = queryResponse.getResults();
		//根据查询结果取得总记录数
		long numFound = list.getNumFound();
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> searchItems=new ArrayList<>();
		for (SolrDocument solrDocument : list) {
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮结果显示集
			String name="";
			List<String> titleList = highlighting.get(solrDocument.get("id")).get("item_title");
			if (titleList!=null&&titleList.size()>0) {
				name=titleList.get(0);
			}else{
				name=(String) solrDocument.get("item_title");
			}
			searchItem.setTitle(name);
			searchItems.add(searchItem);
		}
		SearchResult searchResult = new SearchResult();
		searchResult.setItemList(searchItems);
		searchResult.setRecourdCount(numFound);
		return searchResult;
	}

}
