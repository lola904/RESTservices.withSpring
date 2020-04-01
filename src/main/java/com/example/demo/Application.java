package com.example.demo;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@Component
class BookingCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		for (Booking b : this.bookingRepository.findAll())
			System.out.println(b.toString());
	}

	@Autowired
	BookingRepository bookingRepository;

}

interface BookingRepository extends JpaRepository<Booking, Long> {
	Collection<Booking> findByBookingName(String bookingName);
}

@RestController
class BookingRestController {
	@RequestMapping("/booking")
	Collection<Booking> bookings() {
		return this.bookingRepository.findAll();
	}

	@Autowired
	BookingRepository bookingRepository;
}

@Entity
class Booking {
	@Id
	@GeneratedValue
	private Long id;
	private String bookingName;

	public Booking(String bookingName) {
		super();
		this.bookingName = bookingName;
	}

	public Booking() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getBookingName() {
		return bookingName;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", bookingName=" + bookingName + "]";
	}

	
	
	
}
