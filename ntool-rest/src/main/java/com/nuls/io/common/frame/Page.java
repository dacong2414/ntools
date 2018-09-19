/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.common.frame;

/**
 *页类
 * @author chaoyuhang
 * @version $Id: Page.java, v 0.1 2012-2-17 下午02:26:19 chaoyuhang Exp $
 */
public class Page {
    /**
     * 当前页
     */
    private int curPage;
    /**
     * 每页显示条数
     */
    private int perRows = 20;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总条数
     */
    private int totalRows;

    /**
     * 
     *获取当前页
     * @return int 当前页
     */
    public int getCurPage() {
        if (curPage < 1) {
            curPage = curPage + 1;
        }
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPerRows() {
        return perRows;
    }

    public void setPerRows(int perRows) {
        this.perRows = perRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    /**
     * 
     *设置总页数
     * @param totalRows
     */
    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        int i = this.totalRows % this.perRows;
        if (i == 0) {
            this.totalPages = this.totalRows / this.perRows;
        } else {
            this.totalPages = this.totalRows / this.perRows + 1;
        }

    }

}
