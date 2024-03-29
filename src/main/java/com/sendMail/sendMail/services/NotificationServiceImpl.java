package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;
import com.sendMail.sendMail.datas.repositories.MessageRepository;
import com.sendMail.sendMail.datas.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Override
    public Notification create(Message message) {
        Notification notification= new Notification();
        notification.setTitle("You have a new mail");
        notification.setId(message.getId());
        notification.setMessageBody(message.getMessageBody());
        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public Notification read(String messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow();
        message.getNotification().getId();
        Notification notification = notificationRepository.findById(message.getNotification().getId()).get();
        notification.setRead(true);
        message.setNotification(notification);
        messageRepository.save(message);
        notificationRepository.save(notification);

        return notification;

    }
}
