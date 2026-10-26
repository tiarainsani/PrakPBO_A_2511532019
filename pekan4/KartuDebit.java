package pekan4;

public class KartuDebit {
    private RekeningTabungan rekeningTerkait;
    private final double LIMIT_SEKALI_TARIK = 500000.0;

    public KartuDebit(RekeningTabungan rekening) {
        this.rekeningTerkait = rekening;
    }

    public void tarikTunaiKartu(double nominal, String pinInput) {
        if (!rekeningTerkait.otentikasi(pinInput)) {
            System.out.println("Gagal Kartu Debit: PIN yang Anda masukkan salah!");
            return;
        }
        if (nominal > LIMIT_SEKALI_TARIK) {
            System.out.println("Gagal Kartu Debit: Transaksi melebihi limit sekali tarik (Maksimal Rp500.000)");
            return;
        }
        rekeningTerkait.tarikTunai(nominal);
    }
}