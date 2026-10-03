package pekan4;

public class RekeningVIP extends Rekening {

    public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
        // Panggil constructor superclass (Rekening)
        super(nomor, nama, saldoAwal, pinAwal);

        // Poin Kuis 2: Bonus otomatis Rp100.000 saat pendaftaran VIP
        double bonusVIP = 100000.0;
        this.saldo += bonusVIP; // Mengakses atribut protected saldo

        // Catat transaksi bonus
        String idTrx = "TRX-BONUS-" + System.currentTimeMillis();
        this.riwayatTransaksi.add(new Transaksi(idTrx, "Bonus VIP", bonusVIP));

        System.out.println("Selamat! Anda mendapatkan Bonus Pembukaan Rekening VIP sebesar Rp" + bonusVIP);
    }
}