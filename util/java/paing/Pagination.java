/**
 * 1. FileName	: Pagination.java
 * 2. Package		: vgip.biz.service.custom.util
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: Nov 6, 2018 4:50:14 PM
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		Nov 6, 2018		:	신규 개발.
 */

/**
 * @author line
 *
 * @sess <pre>
 * == 개정이력(Modification information) ==
 * 수정일 		수정자 		수정내용
 * -------------------------------------------------------
 * Nov 6, 2018 	line	최초 생성
 *
 * </pre>
 */
public class Pagination {
	
	private PagingVo pagingVo;
	private int totalPages;
	private int startPage;
	private int endPage;
	
	public Pagination(PagingVo pagingVo) {
		this.pagingVo = pagingVo;
		setCurrentPage();
	}
	
	public void setTotalRecords(Integer totalRecords) {
		totalPages = (int) Math.ceil(totalRecords / (double) pagingVo.getScreenSize());
	}

	private void setCurrentPage() {
		// currentPage가 세팅될 때, 나머지 모든 값들도 같이 세팅 되도록 해준다.
		int currentPage = pagingVo.getCurrentPage();
		int endRow = currentPage * pagingVo.getScreenSize();
		pagingVo.setEndRow(endRow);
		int startRow = endRow - (pagingVo.getScreenSize() - 1);
		pagingVo.setStartRow(startRow);
		this.startPage = (pagingVo.getCurrentPage()-1)/pagingVo.getBlockSize()*pagingVo.getBlockSize()+1;
		this.endPage = this.startPage + (pagingVo.getBlockSize() -1);
	}
	
	public String getPagingHTML(){
		StringBuffer sb = new StringBuffer("");
		if(endPage > totalPages){
			endPage = totalPages;
		}
		if(startPage > 1){ // [이전]에 링크 걸어주기
			sb.append("<a href='javascript:goPage("+ (startPage-pagingVo.getBlockSize()) +")'>[이전]</a>");
		}
		for(int i=startPage; i<=endPage; i++){
			if(pagingVo.getCurrentPage() == i){ // i가 현재 페이지라면 링크를 걸지 말고, 다르다면 링크를 걸어라.
				sb.append(i + "&nbsp;&nbsp;");
			}else{
				sb.append("&nbsp;&nbsp;<a href='javascript:goPage(" + i + ")'>" + i + "&nbsp;&nbsp;&nbsp;</a>");
			}
		}
		if(endPage < totalPages){ // [다음]에 링크 걸어주기
			sb.append("<a href='javascript:goPage(" + (endPage + 1) + ")'>[다음]</a>");
		}
		return sb.toString();
	}
}
