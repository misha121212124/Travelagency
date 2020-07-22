package my.controller;

import my.entity.Booking;
import my.entity.Hotel;
import my.entity.User;
import my.myclasses.Place;
import my.service.BookingService;
import my.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/hotel_search")
    public String hotelList(Model model) {
        model.addAttribute("allHotels", hotelService.allHotels());
        return "hotel_search";
    }

    @GetMapping("/hotel_search/{country}")
    public String  countryHotelList(@RequestParam String country, Model model) {
        if(country.equals("")){
            model.addAttribute("allHotels", hotelService.allHotels());
        }else{
            model.addAttribute("allHotels", hotelService.allHotelsIn(country));
        }
        return "hotel_search";
    }

    @PostMapping("/hotel_search")
    public String  searchHotels(@RequestParam(required = true, defaultValue = "" ) Long hotelId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("showHotel")){
            Hotel hotel = hotelService.findHotelById(hotelId);
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(new java.util.Date());

            Date date1 = new Date(c.getTime().getTime());
            c.add(java.util.Calendar.DATE, hotel.getMax_booking_lenght()-1);
            Date date2 = new Date(c.getTime().getTime());

            List<Date> dates = new ArrayList<>();
            dates.add(date1);
            dates.add(date2);
            model.addAttribute(dates);

            model.addAttribute(hotel);
            model.addAttribute("allPlacess", hotel.getPlaces());

            return "hotel_booking";
        }else {
            hotelService.deleteHotel(hotelId);
            return "redirect:/hotel_search";
        }
    }


    @GetMapping("/hotel_booking/{date1}{date2}")
    public String bookingOnPeriod(@RequestParam("date1")Date date1,@RequestParam("date2")Date date2,
                                  @RequestParam(required = true, defaultValue = "" ) Long hotelId,
                                  Model model){
        Hotel hotel = hotelService.findHotelById(hotelId);
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new java.util.Date());

        if(date1.toLocalDate().isBefore(c.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
            date1 = new Date(c.getTime().getTime());
        }
        if(date1.after(date2)){
            date2 = date1;
        }

        List<List<Place>> places = hotel.getPlacesInPeriod(date1,date2);
        model.addAttribute("allPlacess", places);

        List<Date> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        model.addAttribute(dates);
        model.addAttribute(hotel);

        boolean[] availableDate = new boolean[places.get(0).size()];
        for (List<Place> room: places) {
            for (int i = 0; i<places.get(0).size(); i++){
                if(!room.get(i).isBooked())availableDate[i]=true;
            }
        }

        boolean isAvailable = true;
        for (Boolean temp:availableDate) {
            isAvailable = isAvailable && temp;
        }

        model.addAttribute("searchingResult",(isAvailable)?"Знайдено вільні кімнати на вказаний період":
                "Нажаль не знайдено вільних кімнат на весь період");
        return "hotel_booking";
    }

    @PostMapping("/hotel_booking")
    public String  processingHotel(@RequestParam(required = true, defaultValue = "" ) Long hotelId,
                                   @RequestParam(required = true, defaultValue = "" ) Integer room,
                                   @RequestParam(required = true, defaultValue = "" ) Date date,
                                   @RequestParam(required = true, defaultValue = "" ) String action,
                                   @RequestParam("date1")Date date1,@RequestParam("date2")Date date2,
                                   Model model) {
        if (action.equals("book")){
            Hotel hotel = hotelService.findHotelById(hotelId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)authentication.getPrincipal();
            try {
                Booking booking = new Booking(hotel,room,date,user);
                bookingService.addBooking(booking);
            } catch (Exception e) {
                System.err.println( e.getMessage() );
                model.addAttribute("bookingError", "Бронювання неможливе, перезавантажте сторінку.");
            }

            List<Date> dates = new ArrayList<>();
            dates.add(date1);
            dates.add(date2);
            model.addAttribute(dates);

            model.addAttribute("allPlacess", hotel.getPlacesInPeriod(date1, date2));
            model.addAttribute(hotel);
        }

        return "/hotel_booking";
    }

    @PostMapping("/hotel_addRooms")
    public String  processingHotel(@RequestParam(required = true, defaultValue = "" ) Long hotelId,
                                   @RequestParam(required = true, defaultValue = "" ) Integer additionalRooms,
                                   @RequestParam("date1")Date date1,@RequestParam("date2")Date date2,
                                   Model model) {
        Hotel hotel = hotelService.findHotelById(hotelId);
        hotel.setRoom_count(hotel.getRoom_count()+additionalRooms);
        hotelService.saveHotel(hotel);

        hotel = hotelService.findHotelById(hotelId);
        model.addAttribute(hotel);

        List<Date> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        model.addAttribute(dates);

        model.addAttribute("allPlacess", hotel.getPlacesInPeriod(date1, date2));

        return "/hotel_booking";
    }

    @GetMapping("/addHotel")
    public String registration(Model model) {
        model.addAttribute("hotelForm", new Hotel());
        return "addHotel";
    }

    @PostMapping("/addHotel")
    public String addHotel(@ModelAttribute("hotelForm") @Valid Hotel hotelForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "addHotel";
        }

        if (!hotelService.saveHotel(hotelForm)){
            model.addAttribute("hotelidError", "Помилка. Перезавантажте сторінку та спробуйте ще раз.");
            return "addHotel";
        }

        return "redirect:/hotel_search";
    }


}
