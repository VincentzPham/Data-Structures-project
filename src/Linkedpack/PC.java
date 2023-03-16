package Linkedpack;

public class PC {
    private static int count = 0; // static counter for generating unique IDs
    private int id;
    private Integer SourcePort;
	private Integer DestinationPort;
	private Integer SequenceNumber;
    private Integer AcknowledgementNumber;
    private String Message;

    public PC(int sp, int dp, int sn, int an, String ms) {
        id = count++;
        SourcePort = sp;
	    DestinationPort = dp;
        AcknowledgementNumber = an;
        Message = ms;
	    SequenceNumber = letterCount(ms);
    }

    public static int letterCount(String message) {
        return message.length();
    }

    @Override
	public String toString() {
        return "ID: " + id + ", source port: " + SourcePort 
                + ", destination port: " + DestinationPort + ", sequence number: " + SequenceNumber 
                + ", acknowledgement number: " + AcknowledgementNumber + ", message: " + Message;
    }

    public int getSourcePort() {
        return SourcePort;
    }

    public int getDestinationPort() {
        return DestinationPort;
    }

    public int getSequenceNumber() {
        return SequenceNumber;
    }

    public int getAcknowledgementNumber() {
        return AcknowledgementNumber;
    }

    public String getMessage() {
        return Message;
    }

    public int getMessageId() {
        return id;
    }

    public void setSourcePort(int sourcePort) {
        this.SourcePort = sourcePort;
    }

    public void setDestinationPort(int destinationPort) {
        this.DestinationPort = destinationPort;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.SequenceNumber = sequenceNumber;
    }

    public void setAcknowledgementNumber(int acknowledgementNumber) {
        this.AcknowledgementNumber = acknowledgementNumber;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public void setMessageId(int id) {
        this.id = id;
    }
}
