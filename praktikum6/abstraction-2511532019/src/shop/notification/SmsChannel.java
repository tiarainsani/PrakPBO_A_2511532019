package shop.notification;

import shop.StudentInfo;

public class SmsChannel extends AbstractChannel {
    private final int maxLength;

    public SmsChannel() {
        this.maxLength = StudentInfo.SMS_MAX_LENGTH;
    }

    public SmsChannel(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    protected boolean isValidRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        return phone != null && phone.startsWith("08");
    }

    @Override
    protected String getRecipientAddress(CustomerContact contact) {
        return contact.getPhone();
    }

    @Override
    protected String prepareMessage(String message) {
        if (message.length() > maxLength) {
            return message.substring(0, maxLength - 3) + "...";
        }
        return message;
    }

    @Override
    protected void deliver(String address, String preparedMessage) {
        System.out.println("[SMS to " + address + "] " + preparedMessage);
    }
}