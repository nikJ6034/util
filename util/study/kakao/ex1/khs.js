var a=9;
var b=30;
var c=a|b;
var bin = c.toString(2); // === "1111011"

var n=5;
var arr1="9,20,28,18,11";
var arr2="30,1,21,17,28";
var arr1Split = arr1.split(',');
var arr2Split = arr2.split(',');

for(var i in arr1Split) {
    var arr1Num=parseInt(arr1Split[i]);
    var arr2Num=parseInt(arr2Split[i]);
    var resNum = arr1Num| arr2Num;    
    var bin = resNum.toString(2); // === "1111011"
    var decode=bin.replace(/1/gi, "#"); 
    var decode=decode.replace(/0/gi, " "); 
    if (decode.length==4)
        decode=" "+decode;
    else if (decode.length==3)
        decode="  "+decode;
    else if (decode.length==2)
        decode="   "+decode;
    else if (decode.length==1)
        decode="    "+decode;    

    console.log(decode);

}






