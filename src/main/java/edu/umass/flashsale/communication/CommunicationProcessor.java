package edu.umass.flashsale.communication;

import com.azure.communication.email.*;
import com.azure.communication.email.models.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.CompletableFuture;



@Service
public class CommunicationProcessor {

    @Value("${email.connection.string}")
    private String emailConnectionString;

    @Async
    public CompletableFuture<String> sendEmailCommunication(boolean fulfillmentStatus, String orderId, String emailAddress, String  itemDetails){
        String htmlBody = """
                <html>
                    <body>
                       <h1>
                           %s
                       </h1>
                    </body>
                </html>""".formatted(fulfillmentStatus ?
                            "Your Order is Confirmed!!!" :
                            "Your Order is Not fulfilled!!!");

        EmailClient emailClient = new EmailClientBuilder().connectionString(emailConnectionString).buildClient();
        EmailAddress toAddress = new EmailAddress(emailAddress);
        EmailMessage emailMessage = new EmailMessage()
                .setSenderAddress("DoNotReply@8284cf72-4230-4a86-81df-442d3e20664e.azurecomm.net")
                .setToRecipients(toAddress)
                .setSubject("Staus of your order#: "+orderId+"+ for "+itemDetails)
                .setBodyPlainText("Your Order Details:")
                .setBodyHtml(htmlBody);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage, null);
        PollResponse<EmailSendResult> result = poller.waitForCompletion();
        System.out.println("Email sending status :" + result.getStatus());
        return CompletableFuture.completedFuture(result.getStatus().toString());

    }
}