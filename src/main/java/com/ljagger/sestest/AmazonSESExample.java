package com.ljagger.sestest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AmazonSESExample {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

    private static final String HTML_BODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>" +
            "<p>" +
            "This email was sent with <a href='https://aws.amazon.com/ses/'>" +
            "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" +
            "AWS SDK for Java</a>" +
            "</p> " +
            "<p>" +
            "<img src=\"https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg\" alt=\"Guilty dog\" style=\"width:300px;height:200px;\" />" +
            "</p>";

    private static final String TEXT_BODY = "This email was sent through Amazon SES using the AWS SDK for Java.";

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            throw new InvalidParameterException("Required parameters not supplied. Expected from address and to address.");
        }

        String fromAddress = args[0];
        String toAddress = args[1];

        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.EU_WEST_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(toAddress))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content().withCharset(UTF_8).withData(HTML_BODY))
                                    .withText(new Content().withCharset(UTF_8).withData(TEXT_BODY)))
                            .withSubject(new Content().withCharset(UTF_8).withData(SUBJECT)))
                    .withSource(fromAddress);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: " + ex.getMessage());
        }
    }
}
