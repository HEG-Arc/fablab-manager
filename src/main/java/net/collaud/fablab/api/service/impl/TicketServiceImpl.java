package net.collaud.fablab.api.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.collaud.fablab.api.dao.TicketRepository;
import net.collaud.fablab.api.data.TicketEO;
import net.collaud.fablab.api.security.Roles;
import net.collaud.fablab.api.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the service implementation class for a <tt>Ticket</tt>.
 *
 * @author Fabien Vuilleumier
 */
@Service
@Transactional
@Secured({Roles.TICKET_VIEW, Roles.TICKET_MANAGE})
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketDAO;

    @Override
    @Secured({Roles.TICKET_VIEW, Roles.TICKET_MANAGE})
    public List<TicketEO> findAll() {
        return new ArrayList(new HashSet(ticketDAO.findAll()));
    }

    @Override
    @Secured({Roles.TICKET_VIEW, Roles.TICKET_MANAGE})
    public Optional<TicketEO> getById(Integer id) {
        return ticketDAO.findOneDetails(id);
    }

    @Override
    @Secured({Roles.TICKET_VIEW, Roles.TICKET_MANAGE})
    public TicketEO save(TicketEO ticket) {
        if (ticket.getId() == null) {
            ticket.setId(0);
        }
        if (ticket.getId() > 0) {
            TicketEO old = ticketDAO.findOne(ticket.getId());
            old.setTitle(ticket.getTitle());
            old.setDescription(ticket.getDescription());
            old.setCreationDate(ticket.getCreationDate());
            old.setPrevisionCloseDate(ticket.getPrevisionCloseDate());
            old.setCloseDate(ticket.getCloseDate());
            old.setMachine(ticket.getMachine());
            old.setStatus(ticket.getStatus());
            old.setCreationUser(ticket.getCreationUser());
            old.setCloseUser(ticket.getCloseUser());
            return ticketDAO.saveAndFlush(old);
        } else {
            return ticketDAO.saveAndFlush(ticket);
        }
    }

    @Override
    @Secured({Roles.TICKET_MANAGE})
    public void remove(Integer id) {
        ticketDAO.delete(id);
    }

    @Override
    @Secured({Roles.TICKET_MANAGE})
    public void softRemove(Integer id) {
        TicketEO current = ticketDAO.findOne(id);
        current.setActive(false);
        ticketDAO.saveAndFlush(current);
    }

    @Override
    public List<TicketEO> listByMachine(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
