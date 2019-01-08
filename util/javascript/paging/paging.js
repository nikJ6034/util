/**
 * 사용 방법
 * var devicePaing = new Paging(); 페이징 인스턴스 생성.
 * devicePaing.setSearch(search); 조회 함수를 넣는다.
 * 예제 조회 함수
 * function search(page){
 * 		var _this = this;
		var data = {};
		data.currentPage = page||1;
// 		data.startRow = _this.getStartRow(); //시작줄 과 종료줄은 page만 있어도 계산이 가능함.
// 		data.endRow = _this.getEndRow(); //시작줄 과 종료줄은 page만 있어도 계산이 가능함.
		data.screenSize = _this.getScreenSize();
		$.ajax({
			url : "<c:url value='/smc/device/listJson' />",
			data : data,
			type : "POST",
			dataType : "json",
			success : function(result){
				_this.urlChange(data); //선택
				_this.init(result.totalCount);
			}
		})
	}
	조회가 끝난 뒤 devicePaing.init(전체 행수); 콜백 함수 또는 마지막에 실행.
	
	<div id="paginate"></div>에 페이징 네비게이션이 출력됨.(기본값 : paginate)
	
	초기 페이지 실행 devicePaing.goPage();로 실행
 */
var Paging = function(){
	var blockSize = 10; // 한 화면에 보여줄 링크 수
	var screenSize = 10; // 한 화면에 보여줄 게시물 수
	var totalRecords; // 전체 게시물의 수
	var totalPages; // 전체 페이지 수
	var currentPage = 1; // 현재 페이지
	var startRow; // 페이징 처리할 때, 시작 행 
	var endRow; // 페이징 처리할 때, 끝 행
	var startPage; // 한 구간의 첫 페이지
	var endPage; // 한 구간의 마지막 페이지
	var searchFun; // 비어있는 검색 이벤트
	var pageNaviId = "paginate";
	
	this.getBlockSize = function(){
		return blockSize;
	}
	
	this.getCurrentPage = function(){
		return currentPage;
	}
	
	this.setBlockSize = function(bs){
		blockSize = bs;
	}
	
	this.getScreenSize = function(){
		return screenSize;
	}
	
	this.setScreenSize = function(ss){
		screenSize = ss;
	}
	
	this.setPageNaviId = function(pn){
		pageNaviId = pn;
	}
	
	this.init = function(totalrow){
		this.setTotalRecords(totalrow);
		this.getPagingHTML();
	}
	
	//동기식에서는 이것만 사용하세요.
//	this.initSync = function(){
//		
//		var currentPage = $("#currentPage").val()||1;
//		var totalPages = $("#totalPages").val()||0;
//		
//		this.setCurrentPage(currentPage);
//		this.setTotalRecords(totalPages);
//		this.getPagingHTML();
//	}
	
	this.setSearch = function(search){
		if(search == null){
			searchFun = function(){};
		}else{
			searchFun = search;
		}
		
	}
	
	this.setCurrentPage = function(currenPa){
		currentPage = currenPa;
		endRow = currentPage  * screenSize;
		startRow = endRow - (screenSize - 1);
		startPage = (Math.floor((currentPage-1)/blockSize)*blockSize)+1;
		if(Math.floor((currentPage-1)/blockSize) == 0){
			startPage = 1;
		}
		endPage = startPage + (blockSize -1);
		
		
	}
	
	this.setTotalRecords = function (totalRe){
		totalRecords = totalRe;
		totalPages  = Math.ceil(totalRecords / screenSize);
	}
	
	this.getTotalRecords = function(){
		return totalRecords;
	}
	
	this.getPagingHTML = function(){
		var _this = this;
		var html = "";
		var pageNavi = document.getElementById(pageNaviId);
		var aTag = null;
		if(pageNavi){
			while ( pageNavi.hasChildNodes() ) { 
				pageNavi.removeChild( pageNavi.firstChild ); 
			}
		}

		if(endPage > totalPages){
			endPage = totalPages;
		}
		
		if(startPage > blockSize){ // [이전]에 링크 걸어주기
			aTag = document.createElement("a");
			aTag.href = "#";
			aTag.classList.add("pre_end");
			aTag.title = "처음";
			aTag.innerHTML = "<i class='fa fa-angle-double-left'></i>";
			aTag.addEventListener("click", function(e){
				_this.goPage(1);
				e.preventDefault();
			})
			pageNavi.appendChild(aTag);
		}
		
		if(startPage > 1){ // [이전]에 링크 걸어주기
			aTag = document.createElement("a");
			aTag.href = "#";
			aTag.classList.add("pre");
			aTag.title = "이전";
			aTag.innerHTML = "<i class='fa fa-caret-left'></i>";
			aTag.addEventListener("click", function(e){
				_this.goPage(startPage-blockSize);
				e.preventDefault();
			})
			pageNavi.appendChild(aTag);
		}
		for(var i=startPage; i<=endPage; i++){
			if(currentPage == i){ // i가 현재 페이지라면 링크를 걸지 말고, 다르다면 링크를 걸어라.
				aTag = document.createElement("a");
				aTag.classList.add("select");
				aTag.innerHTML = i;
				pageNavi.appendChild(aTag);
			}else{
				aTag = document.createElement("a");
				aTag.href = "#";
				aTag.innerHTML = i;
				// i변수가 계속해서 공유되는 것을 막기위해 함수 내에서 이벤트를 설정해줌.
				(function(page){
					aTag.addEventListener("click", function(e){
						_this.goPage(page);
						e.preventDefault();
					})
				}(i));
				pageNavi.appendChild(aTag);
			}
		}
		
		if(endPage < totalPages){ // [다음]에 링크 걸어주기
			aTag = document.createElement("a");
			aTag.href = "#";
			aTag.classList.add("next");
			aTag.title = "다음";
			aTag.innerHTML = "<i class='fa fa-caret-right'></i>";
			(function(page){
				aTag.addEventListener("click", function(e){
					_this.goPage(page);
					e.preventDefault();
				})
			}(endPage + 1));
			pageNavi.appendChild(aTag);
		}
		if(endPage < totalPages){ // [다음]에 링크 걸어주기
			aTag = document.createElement("a");
			aTag.href = "#";
			aTag.classList.add("next");
			aTag.title = "끝";
			aTag.innerHTML = "<i class='fa fa-angle-double-right'>";
			(function(page){
				aTag.addEventListener("click", function(e){
					_this.goPage(page);
					e.preventDefault();
				})
			}(totalPages));
			pageNavi.appendChild(aTag);
		}
//		
//		
//		
//		pageNavi.innerHTML = html;
//		pageNavi.appendChild(aTag);
//		return html;
	}
	
	this.getStartRow = function(){
		return startRow;
	}
	
	this.getEndRow = function(){
		return endRow;
	}
	
	this.goPage = function(page){
		var cPage = page|| currentPage || 1;
		this.setCurrentPage(cPage);
		searchFun.call(this,cPage);
	};
	
	this.urlChange = function(param){
		var queryString = "";
		Object.keys(param).forEach(function(key){
			if(param[key]){
				if(queryString != ""){
					queryString +="&";
				}
				queryString +=key+"="+param[key];
			}
		})
		
		var url = location.href.split("?")[0];
		
		history.replaceState(param,"",url+"?"+queryString);
	}
	
	this.getPageHTML =function(page){
		this.getPagingHTML();
	}

};