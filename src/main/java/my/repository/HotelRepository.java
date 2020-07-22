package my.repository;

import my.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
    List<Hotel>  findAllByCountry(String country);
}
