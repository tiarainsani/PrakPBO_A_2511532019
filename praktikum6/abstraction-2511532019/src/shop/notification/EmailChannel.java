package shop.notification;

public class EmailChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "EMAIL";
    }

    @Override
    protected boolean isValidRecipient(CustomerContact contact) {
        String email = contact.getEmail();
        return email != null && email.contains("@") && !email.isBlank();
    }

    @Override
    protected String getRecipientAddress(CustomerContact contact) {
        return contact.getEmail();
    }

    @Override
    protected String prepareMessage(String message) {
        return message;
    }

    @Override
    protected void deliver(String address, String preparedMessage) {
        System.out.println("[EMAIL to " + address + "] " + preparedMessage);
    }
}