package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount = 0;
    private String lastDelivered = "";

    protected abstract boolean isValidRecipient(CustomerContact contact);
    protected abstract String getRecipientAddress(CustomerContact contact);
    protected abstract String prepareMessage(String message);
    protected abstract void deliver(String address, String preparedMessage);

    @Override
    public final boolean send(CustomerContact contact, String message) {
        if (contact == null || message == null || message.isBlank()) {
            return false;
        }
        if (!isValidRecipient(contact)) {
            return false;
        }
        String address = getRecipientAddress(contact);
        if (address == null || address.isBlank()) {
            return false;
        }
        String prepared = prepareMessage(message);
        deliver(address, prepared);
        
        sentCount++;
        lastDelivered = prepared;
        return true;
    }

    public int getSentCount() {
        return sentCount;
    }

    public String getLastDelivered() {
        return lastDelivered;
    }
}