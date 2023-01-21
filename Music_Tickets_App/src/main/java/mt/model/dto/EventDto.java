package mt.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class EventDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sellingStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    private String place;

    private String name;
    public Date getSellingStart() {
        return sellingStart;
    }

    public void setSellingStart(Date sellingStart) {
        this.sellingStart = sellingStart;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
