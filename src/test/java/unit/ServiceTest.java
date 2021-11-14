package unit;

import model.Category;
import model.Event;
import model.Ticket;
import model.User;
import model.impl.EventImpl;
import model.impl.TicketImpl;
import model.impl.UserImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import service.impl.EventServiceImpl;
import service.impl.TicketServiceImpl;
import service.impl.UserServiceImpl;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTest {

    private static UserServiceImpl userService;
    private static EventServiceImpl eventService;
    private static TicketServiceImpl ticketService;
    private static User testUser;
    private static Event testEvent;
    private static Ticket testTicket;

    @Before
    public void init() {
        userService = Mockito.mock(UserServiceImpl.class);
        eventService = Mockito.mock(EventServiceImpl.class);
        ticketService = Mockito.mock(TicketServiceImpl.class);
        testUser = new UserImpl("Ivan", "ivan@mail.ru");
        testEvent = new EventImpl("Swan Lake", new Date());
        testTicket = new TicketImpl(testEvent.getId(), testUser.getId(), Category.BAR, 5);
    }

    @Test
    public void createUserTest() {
        Mockito.when(userService.createUser(testUser)).thenReturn(testUser);
        Mockito.when(userService.getUserById(0)).thenReturn(testUser);
        User actualUser = userService.createUser(testUser);
        SoftAssertions userAssertions = new SoftAssertions();
        userAssertions.assertThat(actualUser).isEqualTo(userService.getUserById(0));
        userAssertions.assertThat(actualUser.getName()).isEqualTo("Ivan");
        userAssertions.assertThat(actualUser.getEmail()).isEqualTo("ivan@mail.ru");
        userAssertions.assertAll();
    }

    @Test
    public void updateEventTest() {
        EventImpl updatedEvent = new EventImpl("The Nutcracker", new Date());
        Mockito.when(eventService.updateEvent(testEvent, updatedEvent)).thenReturn(updatedEvent);
        Mockito.when(eventService.getEventById(0)).thenReturn(testEvent);
        assertThat(eventService.getEventById(0).getTitle())
                .isEqualTo(eventService.updateEvent(testEvent, updatedEvent).getTitle())
                .isEqualTo("Swan Lake");
    }

    @Test
    public void testBookTicket() {
        Mockito.when(ticketService.cancelTicket(testTicket.getId())).thenReturn(true);
        ticketService.cancelTicket(testTicket.getId());
        assertThat(testTicket).isEqualTo(null);
    }
}
