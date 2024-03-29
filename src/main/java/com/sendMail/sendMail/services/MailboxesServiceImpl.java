package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailbox;
import com.sendMail.sendMail.datas.models.MailboxType;
import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MailboxesServiceImpl implements MailboxesService {

    @Autowired
    MailBoxesRepository mailBoxesRepository;

    @Override
    public Mailboxes create(String email) {
        List<Mailbox> mailboxList = new ArrayList<>();
        List<Message> inboxList = new ArrayList<>();
        List<Message> sentList = new ArrayList<>();
        Mailbox inbox = new Mailbox(MailboxType.INBOX,inboxList);
        Mailbox sent = new Mailbox(MailboxType.SENT,sentList);
        mailboxList.add(inbox);
        mailboxList.add(sent);
        Mailboxes mailboxes = new Mailboxes();
        mailboxes.setOwnerId(email);
        mailboxes.setMailboxes(mailboxList);
        mailBoxesRepository.save(mailboxes);
        return mailboxes;
    }

    @Override
    public List<Message> viewAllInbox(String email) {
        Mailboxes mailboxes = mailBoxesRepository.findById(email).get();
        return mailboxes.getMailboxes().get(0).getMessages();
    }

    @Override
    public List<Message> viewAllOutbox(String email) {
        Mailboxes mailboxes = mailBoxesRepository.findById(email).get();
        return mailboxes.getMailboxes().get(1).getMessages();
    }
}
