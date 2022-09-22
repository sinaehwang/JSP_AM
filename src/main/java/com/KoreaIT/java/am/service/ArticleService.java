package com.KoreaIT.java.am.service;

import java.sql.Connection;
import java.util.List;

import com.KoreaIT.java.am.dao.ArticleDao;
import com.KoreaIT.java.am.dto.Article;

public class ArticleService {
	
	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		articleDao = new ArticleDao(conn);
	}

	public int getitemsInAPage() {
		return 10;
	}

	public int getForPrintListTotalPage() {

		int itemsInAPage = getitemsInAPage();

		int totalCount = articleDao.getTotalCount();

		int totalPage = (int) Math.ceil((double) totalCount / itemsInAPage);

		return totalPage;

	}

	public List<Article> getForPrintArticles(int page) {

		int itemsInAPage = getitemsInAPage();

		int limitFrom = (page - 1) * itemsInAPage;

		List<Article> articles = articleDao.getArticles(itemsInAPage, limitFrom);

		return articles;
	}

}