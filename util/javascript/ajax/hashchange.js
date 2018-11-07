/**
 * ajax방식에서 뒤로가기 기능 구현.
 */
$(window).bind('hashchange', function() {

	// var paramObj = hashToParam();
	// var ajaxObj = JqueryAjax.objParam;
	// ajaxObj.data = paramObj;
	// ajaxObj.complete = ajaxComplete(paramObj);
	// JqueryAjax.search(ajaxObj,true);

	if (location.href.indexOf("#") > -1) {
		JqueryAjax.ajaxSearch();
	} else {
		location.reload();
	}

});

function hashToParam() {
	
	var setValue = function(jqueryDom){
		var tagName = jqueryDom.prop("tagName");
		var type = jqueryDom.attr("type") || null;
		// radio 테스트 못함.
		if (tagName == "INPUT" && (type == "radio" || type == "checkbox")) {
			if (jqueryDom.val() == decodeURI(param[1])) {
				jqueryDom.prop("checked", true);
			} else {
				jqueryDom.prop("checked", false);
			}
		} else {
			jqueryDom.val(decodeURI(param[1]));
		}
	}
	
	var hash = window.location.hash.replace("#", "");
	var params = hash.split("&");
	for (var i = 0; i < params.length; i++) {
		var param = params[i].split("=");
		var jqueryDom = $("#" + param[0]);
		JqueryAjax.ajaxData[param[0]] = decodeURI(param[1]);
		if (jqueryDom.length > 0) {
			setValue(jqueryDom);
		} else {
			//같은 아이디가 없을 경우 name으로 다시 검색.
			jqueryDom = $("[name=" + param[0] + "]");
			
			//name으로 선택된 태그 가 여러개인지 1개인지 확인.
			if(jqueryDom.length > 1){
				//여러개면 다시 반복
				for(var k = 0; k < jqueryDom.length; k++){
					var jqueryObj = $(jqueryDom[k]);
					var arrayBox = param[1].split(",");
					for(var j = 0; j < arrayBox.length; j++){
						if(jqueryObj.val() == arrayBox[j]){
							jqueryObj.prop("checked", true);
							break;
						}else{
							jqueryObj.prop("checked", false);
						}
					}
				}
				
			}else{
				//1개면 그대로 처리
				setValue(jqueryDom);
			}
		}

	}
	return JqueryAjax.ajaxData;
}

var JqueryAjax = (function() {
	var returnObj = {};
	var ajaxData = {};
	
	var ajax = function(ajaxData){
		//기본적인 ajax 호출 전후처리를 해주는 곳.
		var protectDisplay = null;
		//처리 전 화면 막음.
		ajaxData.beforeSend = function(){
			var width = document.body.scrollWidth;
			var height = document.body.scrollHeight;
			var hWidth = width/2;
			var hHeight = document.body.offsetHeight/2;
			
			var ajaxImg = $("<img src='/images/common/ajax-loader2.gif'/>");
			ajaxImg.css({position:"fixed",left:hWidth,top:hHeight})
			protectDisplay = $("<div></div>");
			protectDisplay.html(ajaxImg)
			protectDisplay.css({width:width,height:height,background:"#e2e2e2",position: "absolute",top:0,left:0,opacity:0.5,zIndex:99999});
			$("body").append(protectDisplay);
		}
		//처리 후 화면 막음 제거.
		ajaxData.complete = function(){
			$(protectDisplay).remove();
		}
		
		$.ajax(ajaxData);
	};

	var ajaxSearch = function() {
		// 비동기식 처리 후 해시태그 생성.
		ajaxData.data = hashToParam();
		
		ajax(ajaxData);

	}

//	var noneChangeHachSearch = function() {
//		// 비동기식 처리 후 해시태그 생성.
//		$.ajax(ajaxData);
//
//	}

	var ajaxIniSearch = function() {
		if (location.href.indexOf("#") > -1) {
			// 비동기식 처리 후 해시태그 생성.
			ajaxData.data = hashToParam();
			
			ajax(ajaxData);
		} else {
			ajax(ajaxData);
		}

	}

	var hashChang = function(jsonData, initYn) {
		ajaxData = jsonData;
		var data = jsonData.data;
		var dataParam = "";
		if (initYn) {
			ajaxIniSearch(ajaxData);
		} else {
			var keyList = Object.keys(data);
			var obj_length = keyList.length;

			for (var i = 0; i < obj_length; i++) {

				// if((data[keyList[i]]||null) != null){
				dataParam += keyList[i] + "=" + data[keyList[i]];
				if (i != obj_length - 1) {
					dataParam += "&";
				}
				// }

			}
			window.location.hash = dataParam;
			location.href = window.location;
		}

	}

	returnObj.search = hashChang;
	returnObj.ajaxSearch = ajaxSearch;
	returnObj.ajaxData = ajaxData;
//	returnObj.noneChangeHachSearch = noneChangeHachSearch;
	return returnObj;

})();