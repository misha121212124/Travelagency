package my.entity;

import my.myclasses.Place;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "t_hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private Integer room_count;
    private Integer max_booking_lenght;

    @OneToMany(fetch =FetchType.EAGER, mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public Hotel() {
    }

    public Hotel(String name, String country, Integer rooms, Integer max_booking_lenght) {
        this.name = name;
        this.country = country;
        this.room_count = rooms;
        this.max_booking_lenght = max_booking_lenght;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getRoom_count() {
        return room_count;
    }

    public void setRoom_count(Integer room_count) {
        this.room_count = room_count;
    }

    public Integer getMax_booking_lenght() {
        return max_booking_lenght;
    }

    public void setMax_booking_lenght(Integer max_booking_lenght) {
        this.max_booking_lenght = max_booking_lenght;
    }

    public List<Booking> getBookingList() {
        return bookings;
    }

    public List<List<Place>> getPlaces(){
        List<Booking> booked = getBookingList();
        List<List<Place>> places = new ArrayList<>();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new java.util.Date());
        for (int i = 1; i <= room_count; i++){
            places.add(new ArrayList<>());
            c.setTime(new java.util.Date());
            for (int j = 0; j < max_booking_lenght; j++){
                places.get(i-1).add(new Place(i, new Date(c.getTime().getTime())));
                c.add(java.util.Calendar.DATE, 1);
            }
        }
        for (Booking temp : booked) {
            for (int i = 1; i <= room_count; i++) {
                if (places.get(i-1).remove(new Place(temp.getRoom(), temp.getDate()))) {
                    Place bokedPlace = new Place(temp.getRoom(), temp.getDate());
                    bokedPlace.setBooked(true);
                    places.get(i-1).add(bokedPlace);
                    break;
                }
            }
        }
        for (int i = 1; i <= room_count; i++) {
            places.get(i-1).sort(Comparator.comparing(Place::getDate));
        }
        return places;
    }

    public List<List<Place>> getPlacesInPeriod(Date date1, Date date2){
        List<List<Place>> places = getPlaces();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new java.util.Date());
        if(date1.toLocalDate().isBefore(c.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
            date1 = new Date(c.getTime().getTime());
        }
        for (List<Place> room: places) {
            List<Place> removed = new ArrayList<>();
            for (Place temp:room) {
                if( temp.getDate().toLocalDate().isBefore(date1.toLocalDate()) ||
                        temp.getDate().toLocalDate().isAfter(date2.toLocalDate()) )
                    removed.add(temp);
            }
            room.removeAll(removed);
        }
        return places;
    }

    public List<Place> getPlace(){
        List<Booking> booked = getBookingList();
        List<Place> Places = new ArrayList<>();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new java.util.Date());
        for (int i = 1; i <= room_count; i++){
            c.setTime(new java.util.Date());
            for (int j = 0; j < max_booking_lenght; j++){
                Places.add(new Place(i, new Date(c.getTime().getTime())));
                c.add(java.util.Calendar.DATE, 1);
            }
        }
        for (Booking temp : booked) {
            Places.remove(new Place(temp.getRoom(),temp.getDate()));
        }
        return Places;
    }

    @Override
    public String toString() {
        return "entity.Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", rooms=" + room_count +
                "}\n";
    }
}