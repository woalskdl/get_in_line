package com.jay.getinline.service;

import com.jay.getinline.constant.ErrorCode;
import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.domain.Place;
import com.jay.getinline.dto.EventDto;
import com.jay.getinline.exception.GeneralException;
import com.jay.getinline.repository.EventRepository;
import com.jay.getinline.repository.PlaceRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author jay
 */

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public List<EventDto> getEvents(Predicate predicate) {
        try {
            return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
                    .map(EventDto::of)
                    .toList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        try {
//            return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
            return null;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<EventDto> getEvent(Long eventId) {
        try {
//            return eventRepository.findEvent(eventId);
            return eventRepository.findById(eventId).map(EventDto::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional
    public boolean createEvent(EventDto eventDTO) {
        try {
//            return eventRepository.insertEvent(eventDTO);
            if (eventDTO == null) {
                return false;
            }

            Place place = placeRepository.findById(eventDTO.placeDTO().id())
                    .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
            eventRepository.save(eventDTO.toEntity(place));

            return true;

        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional
    public boolean modifyEvent(Long eventId, EventDto dto) {
        try {
//            return eventRepository.updateEvent(eventId, eventDTO);
            if (eventId == null || dto == null) {
                return false;
            }

            eventRepository.findById(eventId)
                    .ifPresent(event -> eventRepository.save(dto.updateEntity(event)));

            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional
    public boolean removeEvent(Long eventId) {
        try {
//            return eventRepository.deleteEvent(eventId);
            if (eventId == null) {
                return false;
            }

            eventRepository.deleteById(eventId);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
