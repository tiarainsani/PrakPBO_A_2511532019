package pekan4;

public class RekeningVIP extends Rekening {

    public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
        super(nomor, nama, saldoAwal, pinAwal);

        double bonusVIP = 100000.0;
        this.saldo += bonusVIP; 
        String idTrx = "TRX-BONUS-" + System.currentTimeMillis();
        this.riwayatTransaksi.add(new Transaksi(idTrx, "Bonus VIP", bonusVIP));

        System.out.println("Selamat! Anda mendapatkan Bonus Pembukaan Rekening VIP sebesar Rp" + bonusVIP);
    }
}