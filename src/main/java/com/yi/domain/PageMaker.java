package com.yi.domain;

public class PageMaker {
	private int totalCount;// 게시글 전체 갯수
	private int startPage;// 현재 보이는 페이지 시작 번호
	private int endPage;// 현재 보이는 페이지 끝 번호
	private boolean prev;// 이전 10개 존재여부
	private boolean next;// 이후 10개 존재여부
	private int displayPageNum = 10;// 보이는 페이지번호 갯수
	private Criteria cri;

	private void calcData() {
		//현재 페이지의 끝 번호를 구한다.
		//ex) 15 / 10 = 1.5 = 2 * 10 = 20
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		
		//현재 페이지의 시작 번호를 구한다.
		//ex) 20-10 =10 +1 = 11
		startPage = (endPage - displayPageNum) + 1;
		//ex) totalCount = 151
		//cri.getPerPageNum() : 한페이지에 보여질 게시물 갯수
		//전체 게시물이 151이고 현재페이지가 15일때, 마지막 end는 16로 나타나야 한다.
		//Math.ceil(151/10) = 16
		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
		//만약, 끝 페이지 번호가 실제 끝 번호보다 크다면 변경해 준다.
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		//이전 페이지 여부
		if (startPage == 1) {
			prev = false;
		} else {
			prev = true;
		}
		//다음 페이지 여부
		if (endPage * cri.getPerPageNum() >= totalCount) {
			next = false;
		} else {
			next = true;
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}

}
