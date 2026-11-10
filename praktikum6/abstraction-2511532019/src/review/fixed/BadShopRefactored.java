package review.fixed;

import java.util.ArrayList;
import java.util.List;

interface ShippingService {
    String getName();
    long calculateCost(int weightGrams);
}

class RegularShipping implements ShippingService {
    @Override
    public String getName() {
        return "REGULER";
    }

    @Override
    public long calculateCost(int weightGrams) {
        int weightKg = (int) Math.ceil(weightGrams / 1000.0);
        return weightKg * 10000L;
    }
}

class ExpressShipping implements ShippingService {
    @Override
    public String getName() {
        return "EXPRES";
    }

    @Override
    public long calculateCost(int weightGrams) {
        int weightKg = (int) Math.ceil(weightGrams / 1000.0);
        return weightKg * 20000L;
    }
}

interface Shippable {
    int getWeightGrams();
}

abstract class Product {
    private final String code;
    private final String name;
    private final long price;

    public Product(String code, String name, long price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public long getPrice() { return price; }
}

class PhysicalProduct extends Product implements Shippable {
    private final int weightGrams;

    public PhysicalProduct(String code, String name, long price, int weightGrams) {
        super(code, name, price);
        this.weightGrams = weightGrams;
    }

    @Override
    public int getWeightGrams() {
        return weightGrams;
    }
}

class GiftVoucher extends Product {
    public GiftVoucher(String code, String name, long price) {
        super(code, name, price);
    }
}

class Address {
    private final String street;
    private final String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class Order {
    private final String orderId;
    private final Address address;
    private final List<Product> items;

    public Order(String orderId, Address address, List<Product> items) {
        this.orderId = orderId;
        // Defensive copy / Immutable reference
        this.address = new Address(address.getStreet(), address.getCity());
        this.items = new ArrayList<>(items);
    }

    public String getOrderId() { return orderId; }
    public Address getAddress() { return address; }
    public List<Product> getItems() { return items; }
}

interface PaymentMethod {
    String getName();
    boolean pay(long amount);
}

class VoucherPayment implements PaymentMethod {
    private double balance;

    public VoucherPayment(double balance) {
        this.balance = balance;
    }

    @Override
    public String getName() {
        return "Voucher";
    }

    @Override
    public boolean pay(long amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        ShippingService express = new ExpressShipping();
        int cartWeight = 2000; // 2 kg
        long expressCost = express.calculateCost(cartWeight);

        System.out.println("Total cart: 170000");
        System.out.println("Ongkir " + express.getName() + " 2 kg: " + expressCost);

        Address addr1 = new Address("Jl. Sudirman 10", "Bukittinggi");
        Order ord1 = new Order("ORD-1", addr1, List.of());

        Address addr2 = new Address("Jl. M. Hatta 15", "Padang");
        Order ord2 = new Order("ORD-2", addr2, List.of());

        System.out.println(ord1.getOrderId() + " -> " + ord1.getAddress());
        System.out.println(ord2.getOrderId() + " -> " + ord2.getAddress());

        Product flashSaleItem = new PhysicalProduct("FLASH-1", "Sepatu Lokal", 70000, 500);
        Product voucherItem = new GiftVoucher("VOUCH-100", "Voucher Belanja 100k", 100000);

        System.out.println("Harga flash sale: " + flashSaleItem.getPrice());

        VoucherPayment voucherPayment = new VoucherPayment(70000.0);
        boolean payResult = voucherPayment.pay(flashSaleItem.getPrice());

        System.out.println(payResult + " | " + voucherPayment.getName() + ", remaining balance " + voucherPayment.getBalance());
        System.out.println("Proses berhasil! Produk digital '" + voucherItem.getName() + "' diproses tanpa crash.");
    }
}
