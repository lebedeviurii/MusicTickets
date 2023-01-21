package mt.model.dto;


public class TicketDto {

    private int price;
    private int seat;
    private String sector;
    private int eventId;

    public int getPrice() {
        return price;
    }

    public int getSeat() {
        return seat;
    }

    public String getSector() {
        return sector;
    }

    public Long getEventId() {
        return (long) eventId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
