public class PagingVo {
	private Integer blockSize = 10; // 한 화면에 보여줄 링크 수
	private Integer screenSize = 15; // 한 화면에 보여줄 게시물 수
	private Integer currentPage = 1; // 현재 페이지
	private Integer startRow; // 페이징 처리할 때, 시작 행 
	private Integer endRow; // 페이징 처리할 때, 끝 행
	private Integer totalRecords; // 전체 게시물의 수
	
	private String sortCondition;		//정렬조건
	private String orderCondition;		//차순조건
	
	
	//mysql전용
	public int getOffset() {
		int offset = (this.currentPage-1)*this.screenSize;
		return offset;
	}
	
	public Integer getBlockSize() {
		return blockSize;
	}


	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}


	public Integer getScreenSize() {
		return screenSize;
	}


	public void setScreenSize(Integer screenSize) {
		this.screenSize = screenSize;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}


	public Integer getStartRow() {
		return startRow;
	}


	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}


	public Integer getEndRow() {
		return endRow;
	}


	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}


	public Integer getTotalRecords() {
		return totalRecords;
	}


	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}


	public String getSortCondition() {
		return sortCondition;
	}


	public void setSortCondition(String sortCondition) {
		this.sortCondition = sortCondition;
	}


	public String getOrderCondition() {
		return orderCondition;
	}


	public void setOrderCondition(String orderCondition) {
		this.orderCondition = orderCondition;
	}
}
