/**
 * javascript 오브젝트를 쿼리 스트링 형식으로 반환함.
 */
function objectForParam(obj) {

	var strParam = "";

	var keyList = Object.keys(obj);
	var obj_length = keyList.length;

	for (var i = 0; i < obj_length; i++) {
		if ((obj[keyList[i]] || null) != null) {
			strParam += keyList[i] + "=" + obj[keyList[i]];
			if (i != obj_length - 1) {
				strParam += "&";
			}
		}
	}

	return strParam;
}