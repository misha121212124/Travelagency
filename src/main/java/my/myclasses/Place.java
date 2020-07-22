package my.myclasses;
import java.sql.Date;

public class Place {
    private Integer room;
    private Date date;
    private boolean isBooked = false;

    public Place(Integer room, Date date) {
        this.room = room;
        this.date = date;
    }

    public Integer getRoom() {
        return room;
    }

    public Date getDate() {
        return date;
    }

    public void setBooked( boolean isBooked ){this.isBooked = isBooked;}

    public boolean isBooked() {
        return isBooked;
    }

    @Override
    public String toString() {
        return "myclasses.Place{" +
                "room: " + room +
                ", date: '" + date + '\'' +
                ", isBoked: " + isBooked + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object obj) {
        return room.equals(((Place) obj).room) && date.toLocalDate().equals(((Place) obj).date.toLocalDate());
    }
}