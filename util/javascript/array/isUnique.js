/**
 * jquery 필수
 */
//배열 객체에 중복인지 아닌지 체크  jquery객체도 가능함.
function isUnique(array){
	var result = [];
	var isUnique = true;
	
	$.each(array,function(index,obj){
		var value = null;
		if("object" === typeof obj ){
			value = $(obj).val();
		}else{
			value = obj;
		}
		
		
		if($.inArray(value, result) == -1){
			result.push(value);
		}else{
			isUnique = false;
			return false;
		}
		
	});
	
	return isUnique;
}