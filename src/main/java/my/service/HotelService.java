package my.service;

import my.entity.Hotel;
import my.repository.BookingRepository;
import my.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    HotelRepository hotelRepository;

    public Hotel findHotelById(Long hotelId) {
        Optional<Hotel> hotelFromDb = hotelRepository.findById(hotelId);
        return hotelFromDb.orElse(new Hotel());
    }

    public List<Hotel> allHotels() {
        return hotelRepository.findAll(Sort.by("name"));
    }

    public List<Hotel> allHotelsIn(String country) {
        System.out.println(country);
        System.out.println(hotelRepository.findAllByCountry(country));
        return hotelRepository.findAllByCountry(country);
    }

    public boolean saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return true;
    }

    public boolean deleteHotel(Long hotelId) {
        if (hotelRepository.findById(hotelId).isPresent()) {
            hotelRepository.deleteById(hotelId);
            return true;
        }
        return false;
    }
}
