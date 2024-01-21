package nyproje;

import java.io.Serializable;


public class DateInfo implements Serializable{
	private int startMonth;
	private int endMonth;
	private int startYear;
        private int endYear;
	public DateInfo(int startMonth, int startYear) {
		this.startMonth = startMonth;
                this.endYear = startYear; //Ocak 2023 baslayan abonelik 31 Aralik 2023de biter bize verilen problem domainde bu sekilde denmis.
		this.endMonth = startMonth+11; //startmonth da dahil oldugundan +11 ekliyoruz.
                if(endMonth > 12){
                    this.endYear++; //endmonth 12yi gectiyse bitis yili bir atar endmonth 12 azalir.
                    endMonth -=12;
                }
		this.startYear = startYear;
	}
	public int getStartMonth() {
		return startMonth;
	}

    public int getEndYear() {
        return endYear;
    }
        
	public int getEndMonth() {
		return endMonth;
	}
	public int getStartYear() {
		return startYear;
	}

    @Override
    public String toString() {
        return "DateInfo{" + "startMonth=" + startMonth + ", startYear=" + startYear + ", endMonth=" + endMonth + ", endYear ="+endYear + '}';
    }

	
	

}
