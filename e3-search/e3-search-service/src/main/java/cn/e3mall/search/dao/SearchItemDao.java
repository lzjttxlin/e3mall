package cn.e3mall.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.e3mall.common.pojo.SearchResult;

public interface SearchItemDao {
	SearchResult search(SolrQuery solrQuery) throws Exception;
}
