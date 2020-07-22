package my.entity;

        import my.myclasses.Place;
        import javax.persistence.*;
        import java.sql.Date;

@Entity
@Table(name = "t_bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hotel hotel;

    private Integer room;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Booking() {
    }

    public Booking(Hotel hotel, Integer room, Date date, User user) throws Exception {
        if ( room > hotel.getRoom_count() ) throw
                new Exception("Room " + room + " isn't exist in hotel: " + hotel.getName());
        if ( !hotel.getPlace().contains(new Place(room, date)) )
            throw new Exception("Room " + room + " from hotel: " + hotel.getName() + " is already booked for " + date);
        this.hotel = hotel;
        this.room = room;
        this.date = date;
        this.user = user;
        hotel.addBooking(this);
        user.addBooking(this);
        System.err.println("Booking of the room " + room + " is possible.");
    }

    public Long getId() {
        return id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Integer getRoom() {
        return room;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "entity.Booking{" +
                "id=" + id +
                ", hotel='" + hotel + '\'' +
                ", room: " + room +
                ", date: " + date +
                ", user=" + user +
                "}\n";
    }
}