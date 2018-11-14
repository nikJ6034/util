/**
 * 리스트에 담긴 오브젝트의 키값이 일치하면 그 오브젝트를 삭제한다.
 */
Array.prototype.removeObject = function(key,value){
	for(var i = 0; i < this.length; i++){
		if(this[i][key]==value){
			this.splice(i,1);
		}
	}
}