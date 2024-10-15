package com.igriss.ListIn.security.verification;

import org.springframework.stereotype.Component;

@Component
public class Message {
    public static String PASSWORD;
    public static String VerificationMessage(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <title>Email Verification</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 40px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #007bff;\n" +
                "            color: white;\n" +
                "            padding: 10px;\n" +
                "            border-top-left-radius: 8px;\n" +
                "            border-top-right-radius: 8px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 22px;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content p {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .code {\n" +
                "            font-size: 22px;\n" +
                "            font-weight: bold;\n" +
                "            color: #007bff;\n" +
                "            margin: 20px 0;\n" +
                "            background-color: #e9f7ff;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 8px;\n" +
                "            display: inline-block;\n" +
                "        }\n" +
                "        .note {\n" +
                "            font-size: 14px;\n" +
                "            color: #666;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 30px;\n" +
                "            font-size: 12px;\n" +
                "            color: #999;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        @media screen and (max-width: 600px) {\n" +
                "            .container {\n" +
                "                margin: 20px;\n" +
                "                padding: 15px;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Email Verification</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Your email verification code is:</p>\n" +
                "            <div class=\"code\">"+ Message.PASSWORD +"</div>\n" +
                "            <p class=\"note\">Please do not share this code with anyone for your security.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>If you didn’t request this, please ignore this email.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

}
