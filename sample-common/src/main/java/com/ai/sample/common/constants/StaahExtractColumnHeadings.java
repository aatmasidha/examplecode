package com.ai.sample.common.constants;

public enum StaahExtractColumnHeadings {
	BOOKING_NO("ChannelRefID"),
	ARRIVAL_DATE("CheckInDate"),
	DEPARTURE_DATE("CheckOutDate"),
	BOOKING_DATE("CreateDate"),
	CHANNEL_NAME("MemberName"),
	BOOKING_STATUS("MemberName"),
	CURRENCY("Currency"),
	AMOUNT_PAID("AmountPaid");
	
	 private final String columnName;       

    private StaahExtractColumnHeadings(String s) {
        columnName = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : columnName.equals(otherName);
    }

    public String toString() {
       return this.columnName;
    }
}
