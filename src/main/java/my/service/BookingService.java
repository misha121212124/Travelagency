package my.service;

import my.entity.Booking;
import my.entity.Hotel;
import my.entity.User;
import my.repository.BookingRepository;
import my.repository.HotelRepository;
import my.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class BookingService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    UserRepository userRepository;

    public boolean addBooking(Booking booking){
        Hotel hotel = hotelRepository.findById(booking.getHotel().getId()).get();
        User user = userRepository.findById(booking.getUser().getId()).get();
        if(hotel == null || user == null ){
            return false;
        }
        bookingRepository.save(booking);
        System.out.println("added successfully");
        return true;
    }
    public boolean deleteBooking(Long bookingId) {
        if (bookingRepository.findById(bookingId).isPresent()) {
            bookingRepository.deleteById(bookingId);
            return true;
        }
        return false;
    }

}
