package shop.app;

import java.util.List;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;
import shop.notification.*;
import shop.StudentInfo;
import shop.payment.RefundablePayment;

public class TugasApp {
    public static void main(String[] args) {
        // Produk keranjang contoh
        Product coffee = new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300);
        Product rendang = new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400);
        Product ebook = new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000, "https://toko-umkm.example/unduh/ebk-003");
        List<Product> cart = List.of(coffee, rendang, ebook);

        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        PaymentMethod wallet = new EWalletPayment("Dompetku", 300_000);
        PaymentMethod transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Siti Rahma", 1_000_000);

        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");
        System.out.println("== T1: Total dengan biaya admin (Ekspres) ==");
        System.out.println(wallet.getName() + " Rp" + checkout.grandTotal(cart, express, wallet) + " (admin Rp" + wallet.adminFee(checkout.total(cart, express)) + ")");
        System.out.println(transfer.getName() + " Rp" + checkout.grandTotal(cart, express, transfer) + " (admin Rp" + transfer.adminFee(checkout.total(cart, express)) + ")");
        System.out.println(card.getName() + ": Rp" + checkout.grandTotal(cart, express, card) + " (admin Rp" + card.adminFee(checkout.total(cart, express)) + ")");

        System.out.println("Kebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet     : " + ((RefundablePayment)wallet).policy());

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        CustomerContact siti = new CustomerContact("Siti Rahma", "siti@example.com", "081234567890");
        CustomerContact budi = new CustomerContact("Budi", null, "+6281298765432");

        String longMessage = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. Nomor resi akan muncul di aplikasi dalam 1x24 jam. Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami!";

        NotificationService service = new NotificationService();
        service.register(new EmailChannel());
        service.register(new SmsChannel());
        service.register(new WhatsAppChannel());
        
        // Anonymous class untuk log konsol
        service.register(new NotificationChannel() {
            @Override
            public String channelName() {
                return "LOG";
            }
            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        int sentSiti = service.broadcast(siti, longMessage);
        System.out.println("Terkirim ke Siti lewat " + sentSiti + " kanal");

        int sentBudi = service.broadcast(budi, longMessage);
        System.out.println("Terkirim ke Budi lewat " + sentBudi + " kanal");
    }
}