package com.SEAM.backend.services;

import com.SEAM.backend.models.Event;
import com.SEAM.backend.models.UserModel;
import com.SEAM.backend.repo.EventRepo;
import com.SEAM.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventServices {
    @Autowired
    private EventRepo repo;
    @Autowired
    private UserRepo up;

    public Event createEvent(Event event){
        return repo.save(event);
    }
    public Optional<Event>  getEventById(String eventId){
        return repo.findById(eventId);
    }
    public List<Event> getEventByName (String clubName){
        return repo.findByClubName(clubName);
    }






    private final ZoneId ZONE_ID = ZoneId.of("Asia/Kolkata");
    private final LocalDate currentDate = LocalDate.now(ZONE_ID);



    public List<Event> getPastEvents() {
        List<Event> allEvents = repo.findAll();
        List<Event> pastEvents = new ArrayList<Event>();
        for (Event event : allEvents) {
            LocalDate eventDate = event.eventDate.toInstant().atZone(ZONE_ID).toLocalDate();
            if (eventDate.isBefore(currentDate) ||
                    (eventDate.isEqual(currentDate) && event.endTime.isBefore(LocalTime.now(ZONE_ID)))) {
                pastEvents.add(event);
            }
        }
        return pastEvents;
    }

    public List<Event> getFutureEvents() {
        List<Event> allEvents = repo.findAll();
        List<Event> futureEvents = new ArrayList<Event>();
        for (Event event : allEvents) {
            LocalDate eventDate = event.eventDate.toInstant().atZone(ZONE_ID).toLocalDate();
            if (eventDate.isAfter(currentDate) ||
                    (eventDate.isEqual(currentDate) && event.startTime.isAfter(LocalTime.now(ZONE_ID)))) {
                futureEvents.add(event);
            }
        }
        return futureEvents;
    }

    public List<Event> getPresentEvents() {
        List<Event> allEvents = repo.findAll();
        List<Event> presentEvents = new ArrayList<Event>();
        for (Event event : allEvents) {
            LocalDate eventDate = event.eventDate.toInstant().atZone(ZONE_ID).toLocalDate();
            if (eventDate.isEqual(currentDate) &&
                    !event.endTime.isBefore(LocalTime.now(ZONE_ID)) &&
                    !event.startTime.isAfter(LocalTime.now(ZONE_ID))) {
                presentEvents.add(event);
            }
        }
        return presentEvents;
    }








    public List<Event> getPastEventsByClub(String clubName) {
        List<Event> allEvents = repo.findByClubName(clubName);
        List<Event> pastEvents = new ArrayList<Event>();
        for (Event event : allEvents) {
            LocalDate eventDate = event.eventDate.toInstant().atZone(ZONE_ID).toLocalDate();
            if (eventDate.isBefore(currentDate) ||
                    (eventDate.isEqual(currentDate) && event.endTime.isBefore(LocalTime.now(ZONE_ID)))) {
                pastEvents.add(event);
            }
        }
        return pastEvents;
    }

    public List<Event> getFutureEventsByClub(String clubName) {
        List<Event> allEvents = repo.findByClubName(clubName);
        List<Event> futureEvents = new ArrayList<Event>();
        for (Event event : allEvents) {
            LocalDate eventDate = event.eventDate.toInstant().atZone(ZONE_ID).toLocalDate();
            if (eventDate.isAfter(currentDate) ||
                    (eventDate.isEqual(currentDate) && event.startTime.isAfter(LocalTime.now(ZONE_ID)))) {
                futureEvents.add(event);
            }
        }
        return futureEvents;
    }

    public List<Event> getPresentEventsByClub(String clubName) {
        List<Event> allEvents = repo.findByClubName(clubName);
        List<Event> presentEvents = new ArrayList<Event>();
        for (Event event : allEvents) {
            LocalDate eventDate = event.eventDate.toInstant().atZone(ZONE_ID).toLocalDate();
            if (eventDate.isEqual(currentDate) &&
                    !event.endTime.isBefore(LocalTime.now(ZONE_ID)) &&
                    !event.startTime.isAfter(LocalTime.now(ZONE_ID))) {
                presentEvents.add(event);
            }
        }
        return presentEvents;
    }

    public boolean register(Map<String,String> map){
        String admNo = map.get("admNo");
        String eventId = map.get("eventId");

        Optional<UserModel> u = up.findById(admNo);
        Optional<Event> e = repo.findById(eventId);
        if(e.isPresent() && u.isPresent()){
            e.get().registered.add(admNo);
            u.get().attended.add(eventId);
            return true;
        }
        return false;
    }





}