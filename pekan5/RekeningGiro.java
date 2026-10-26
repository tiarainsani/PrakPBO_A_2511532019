package pekan5;

public class RekeningGiro  extends Rekening {
	private double batasOverdraft;
	public RekeningGiro (String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		//memanggil inisialisasi dasar dari superclass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft=batasOverdraft;
	}
	//IMPLEMENTASI POLIMORFISME(OVERRIDING)
	@Override
	public boolean tarikTunai(double nominal) {
		if (nominal <=0) {
			System.out.println("Gagal:Nominal tarik harus lebih dari 0");
			return false;
		}else if (nominal>(saldo+batasOverdraft)) {
			System.out.println("Transaksi gagal:Melebihi batas Overdraft! Maksimal penarikan: Rp"+(saldo+batasOverdraft));
			return false;
		}else {
			saldo-=nominal;//saldo bisa bernilai negatif disini 
			String idTrx="TRX-G-"+System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx,"Debit",nominal));
			System.out.println("Tarik tunai Rp"+nominal+" berhasil. Sisa saldo: Rp"+saldo);
			return true;//Transaksi sukses
		}
	}
	
	//getter khusus giro
	public double getBatasOverdraft(){
		return batasOverdraft;
	}
}
