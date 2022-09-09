package applications.watsappanalyzer;

import java.time.LocalDateTime;

public class WatsAppMessage {
    private String message = "";
    private String sender = "";
    private LocalDateTime localDateTime;

    public WatsAppMessage(String watsAppMessage) {
        message = watsAppMessage.substring(watsAppMessage.indexOf(": ") + 2);
        sender = watsAppMessage.substring(watsAppMessage.indexOf(" - ") + 3, watsAppMessage.indexOf(": "));
        String dateTime = watsAppMessage.substring(0, watsAppMessage.indexOf(" - "));
    }

    public String toString() {
        return "time: " + localDateTime.toString() +  System.lineSeparator() +
                "sender: " + sender +  System.lineSeparator() +
                "message: " + message +  System.lineSeparator() +
                "----------------------------------------" +  System.lineSeparator();
    }
}