package com.augustojbe.client.integration;

public interface MailIntegration {

    void send(String mailTo, String message, String subject);

}
