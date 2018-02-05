package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private long id;
    private String title;
    private String message;
    private long fromId;
    private long toId;
    private MessageStatus status;
    private String timestamp;

    public Message(String title, String message, long fromId, long toId, MessageStatus status) {
        this.title = title;
        this.message = message;
        this.fromId = fromId;
        this.toId = toId;
        this.status = status;
    }
}
