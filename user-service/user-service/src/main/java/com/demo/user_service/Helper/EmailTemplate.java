package com.demo.user_service.Helper;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplate {

    public String EmailBody(String name,String content){

        return "<html>" +
                "<head>" +
                "<style>" +
                "body {font-family: Arial, sans-serif; line-height:1.6;}" +
                ".header { background-color: #007BFF; color: white; padding: 10px; text-align: center; }" +
                ".content { margin: 20px; color: green; }" +
                ".footer { font-size: 0.8em; text-align: center; color: gray; }" +
                ".disclaimer{font-size:0.8em; color:red; text-align:center; padding: 3px;}"+
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<h1>Welcome, " + name + "</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>" + content + "</p>" +
                "</div>" +
                "<div class='disclaimer'>"+
                "<p>Note : This is an Auto Generated mail ... <br/> Please Dont Reply to this mail.</p>"+
                "</div>"+
                "<div class='footer'>" +
                "<p>Thank you for being with us!</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
