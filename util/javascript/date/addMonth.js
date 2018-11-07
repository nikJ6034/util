/**
 * 
 */
/**
 * 오늘 날로부터 month 개월 더한다.
 */
Date.prototype.addMonth = function(month){
	   var date = this.getDate();
	   this.setMonth(this.getMonth()+month);
	   var nextMonthDate = this.getDate();
	   
	   if(date != nextMonthDate){
	      this.setDate(0);
	   }
	   
	   return this;
	}