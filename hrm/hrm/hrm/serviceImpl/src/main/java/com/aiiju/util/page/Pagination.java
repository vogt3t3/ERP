package com.aiiju.util.page;
import java.io.Serializable;

/**
 * 
 * @ClassName: Pagination 
 * @Description: 分页工具类
 * @author 哪吒 
 * @date 2016年8月20日 上午10:46:47 
 *
 */
public class Pagination implements Serializable {

	private static final long serialVersionUID = 5997689267350950263L;
	
	private int pageNum; // 当前页数，默认为1
	
	private int pageSize; // 每页显示记录的条数，默认为3
	
	private int totalCount; // 总的记录条数
	
	private int totalPageCount; // 总的页数
	
	private int startPos; // 开始位置，从0开始
	
	private boolean hasFirst; // 是否有首页
	
	private boolean hasPre; // 是否有前一页
	
	private boolean hasNext; // 是否有后一页
	
	private boolean hasLast; // 是否有最后一页
	
	public Pagination (int totalCount, Integer pageNum, Integer pageSize) {
		this.totalCount = totalCount;
		if (null == pageNum | ("").equals(pageNum) | 0 == pageNum) {
		    this.pageNum = 1;
		}else {
		    this.pageNum = pageNum;
		}
		if (null == pageSize | ("").equals(pageSize) | 0 == pageSize) {
		    this.pageSize = 3;
        }else {
            this.pageSize = pageSize;
        }
	}
	
	public Pagination (int totalCount, String pageNum, String pageSize) {
        this.totalCount = totalCount;
        if (null == pageNum | ("").equals(pageNum)) {
            pageNum = "1";
        }
        if (null == pageSize | ("").equals(pageSize)) {
            pageSize = "3";
        }
        this.pageNum = Integer.parseInt(pageNum);
        this.pageSize = Integer.parseInt(pageSize);
        this.totalPageCount = getTotalPageCount();
        this.startPos = getStartPos();
    }
	
	public Pagination(int totalCount) {
	    this.pageNum = 1; // 默认当前页数为1
	    this.pageSize = 3; // 默认每页显示记录数为3
	    this.totalCount = totalCount;
	}
	
	/**
	 * 
	 * @Title: getTotalPageCount 
	 * @Description: 取得总页数 
	 * @param 
	 * @return int    返回类型 
	 * @throws
	 */
	public int getTotalPageCount() {
		totalPageCount = getTotalCount() / getPageSize();
		return (totalCount % pageSize == 0) ? totalPageCount : totalPageCount + 1;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 
	 * @Title: getStartPos 
	 * @Description: 取得选择记录的初始位置
	 * @param 
	 * @return int    返回类型 
	 * @throws
	 */
	public int getStartPos() {
		return (pageNum - 1) * pageSize;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	/**
	 * 
	 * @Title: isHasFirst 
	 * @Description: 是否是第一页
	 * @param 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isHasFirst() {
		return (pageNum == 1) ? false : true;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	/**
	 * 
	 * @Title: isHasPre 
	 * @Description: 是否有首页
	 * @param 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isHasPre() {
		// 如果有首页就有前一页，因为有首页就说明不是第一页
		return isHasFirst() ? true : false;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	/**
	 * 
	 * @Title: isHasNext 
	 * @Description: 是否有下一页 
	 * @param 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isHasNext() {
		return isHasLast() ? true : false;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	/**
	 * 
	 * @Title: isHasLast 
	 * @Description: 是否有尾页
	 * @param  
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isHasLast() {
		// 如果不是最后一页就有尾页
		return (pageNum == getTotalPageCount()) ? false : true;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}

	@Override
	public String toString() {
		return "Pagination [pageNum=" + pageNum + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPageCount=" + totalPageCount + ", startPos=" + startPos + ", hasFirst=" + hasFirst
				+ ", hasPre=" + hasPre + ", hasNext=" + hasNext + ", hasLast=" + hasLast + "]";
	}
}
