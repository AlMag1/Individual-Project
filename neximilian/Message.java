package neximilian;

public class Message {
	private String dateTime;
	private String body;
	private int id;
	private User sender;
	private User receiver;
	
	public Message() {
	}
	
	public Message(String dateTime, String body, User sender) {
		this.dateTime = dateTime;
		this.body = body;
		this.sender = sender;
	}
	
	public Message(String dateTime, String body, int id, User sender) {
		this.dateTime = dateTime;
		this.body = body;
		this.id = id;
		this.sender = sender;
	}
	
	public Message(String dateTime, String body, User sender, User receiver) {
		this.dateTime = dateTime;
		this.body = body;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getMessageId() {
		return id;
	}

	public void setMessageId(int id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public String toString() {
		return "Message [id:" + id +", Date:" + dateTime + ", message:" + body + ", sender: " + sender + "]";
	}

}
