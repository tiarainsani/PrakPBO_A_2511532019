package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "WA";
    }

    @Override
    protected boolean isValidRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        if (phone == null) return false;
        return (phone.startsWith("08") || phone.startsWith("+62")) && phone.length() >= 10;
    }

    @Override
    protected String getRecipientAddress(CustomerContact contact) {
        return contact.getPhone();
    }

    @Override
    protected String prepareMessage(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override
    protected void deliver(String address, String preparedMessage) {
        System.out.println("[WA to " + address + "] " + preparedMessage);
    }
}