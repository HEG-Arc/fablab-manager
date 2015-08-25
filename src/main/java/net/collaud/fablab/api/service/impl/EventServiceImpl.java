package net.collaud.fablab.api.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.collaud.fablab.api.dao.EventRepository;
import net.collaud.fablab.api.data.CertificationEO;
import net.collaud.fablab.api.data.EventEO;
import net.collaud.fablab.api.security.Roles;
import net.collaud.fablab.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the service implementation class for a <tt>Event</tt>.
 *
 * @author Fabien Vuilleumier
 */
@Service
@Transactional
@Secured({Roles.EVENT_VIEW})
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventDAO;

    @Override
    @Secured({Roles.EVENT_VIEW})
    public List<EventEO> findAll() {
        return new ArrayList(new HashSet(eventDAO.findAll()));
    }

    @Override
    @Secured({Roles.EVENT_VIEW})
    public Optional<EventEO> getById(Integer id) {
        return eventDAO.findOneDetails(id);
    }

    @Override
    @Secured({Roles.EVENT_VIEW})
    public EventEO save(EventEO event) {
        if (event.getId() == null) {
            event.setId(0);
        }
        if (event.getId() > 0) {
            EventEO old = eventDAO.findOneDetails(event.getId()).get();
            old.setDateStart(event.getDateStart());
            old.setDateEnd(event.getDateEnd());
            old.setTimeStart(event.getTimeStart());
            old.setTimeEnd(event.getTimeEnd());
            old.setTitle(event.getTitle());
            old.setTheme(event.getTheme());
            old.setPlace(event.getPlace());
            old.setDescription(event.getDescription());
            old.setEventType(event.getEventType());
            old.setSupervisor(event.getSupervisor());
            old.setPrice(event.getPrice());
            old.setModules(event.getModules());
            old.setParticipants(event.getParticipants());
            old.setOrganizers(event.getOrganizers());
            return eventDAO.saveAndFlush(old);
        } else {
            return eventDAO.saveAndFlush(event);
        }
    }

    @Override
    @Secured({Roles.EVENT_VIEW})
    public void remove(Integer id) {
        eventDAO.delete(id);
    }

    @Override
    @Secured({Roles.EVENT_VIEW})
    public void softRemove(Integer id) {
        EventEO current = eventDAO.findOne(id);
        current.setActive(false);
        eventDAO.saveAndFlush(current);
    }

    @Override
    @Secured({Roles.EVENT_VIEW})
    public EventEO getId(String title) {
        return eventDAO.getId(title);
    }
}
