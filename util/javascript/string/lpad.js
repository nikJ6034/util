/**
 * lpad함수 구현
 * s : 문자열
 * padLength : 채워질 문자길이
 * padString : 채워질 문자
 */
function lpad(s, padLength, padString){
	
	if(typeof s === 'number'){
		s =s.toString();
	}
	
	
    while(s.length < padLength)
        s = padString + s;
    return s;
}