package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalContent;
    private int totalPage;
    private int pageLimited;
    private int currentPage;

    private List<T> eleList;

    public int getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(int totalContent) {
        this.totalContent = totalContent;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageLimited() {
        return pageLimited;
    }

    public void setPageLimited(int pageLimited) {
        this.pageLimited = pageLimited;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getEleList() {
        return eleList;
    }

    public void setEleList(List<T> eleList) {
        this.eleList = eleList;
    }

    public PageBean() {
    }

    public PageBean(int totalContent, int totalPage, int pageLimited, int currentPage, List<T> eleList) {
        this.totalContent = totalContent;
        this.totalPage = totalPage;
        this.pageLimited = pageLimited;
        this.currentPage = currentPage;
        this.eleList = eleList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalContent=" + totalContent +
                ", totalPage=" + totalPage +
                ", pageLimited=" + pageLimited +
                ", currentPage=" + currentPage +
                ", eleList=" + eleList +
                '}';
    }
}
