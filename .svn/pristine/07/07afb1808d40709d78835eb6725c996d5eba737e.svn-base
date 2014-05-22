package com.teaminternational.mail.service;

import com.teaminternational.mail.domain.Folder;
import com.teaminternational.mail.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: Vladislav Povedyuk
 * Date: 22.11.13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = {"classpath:/Test-context.xml"})
public class MailServiceImplTest {

    MailService mockedService = mock(MailServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Test
    public void testGetDeliveryList() {
        List<Message> deliveryList = mailService.getDeliveryList(userService.getUser(2));
        for(Message message : deliveryList){
            assertTrue(message.getFolder().getFolderName().equals("OUTBOX"));
        }
    }

    @Test
    public void testAddMessageToDelivery() {
        Message messageMock = mock(Message.class);
        mockedService.addMessageToDelivery(messageMock);
        verify(mockedService).addMessageToDelivery(messageMock);
    }

    @Test
    public void testGetMessagesInbox() {
        List<Message> inbox = mailService.getMessages(userService.getUser(2), new Folder(1, "INBOX"));
        for (Message m : inbox){
            assertTrue(m.getFolder().getFolderName().equals("INBOX"));
        }
    }

    @Test
    public void testGetMessagesSent() {
        List<Message> outbox = mailService.getMessages(userService.getUser(2), new Folder(3, "SENT"));
        for (Message m : outbox){
            assertTrue(m.getFolder().getFolderName().equals("SENT"));
        }
    }
}
