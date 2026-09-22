package pekan2;
import java.util.*;
public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	//implementasi asosiasi (1-to-many}
	ArrayList<Transaksi>riwayatTransaksi;
	public Rekening (String nomor, String nama, double saldoAwal) {
		this.nomorRekening=nomor;
		this.namaPemilik=nama;
		this.saldo=saldoAwal;
		//wajib menginisialisasi ArrayList di dalam construktor agar tidak NullPointerException
		this.riwayatTransaksi=new ArrayList<>();
		System.out.println("Rekening atas nama "+ namaPemilik+ " berhasil dibuat.");
	}
	public void setorTunai(double nominal) {
		if (nominal>0) {
			saldo+=nominal;
			//merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx="TRX-S"+System.currentTimeMillis();
			Transaksi trxBaru=new Transaksi (idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Setor tunai Rp"+nominal+" berhasil. saldo saat ini: Rp"+saldo);
		}else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	public void tarikTunai(double nominal) {
		if (nominal>0 && saldo >= nominal) {
			saldo -= nominal;
			
			String idTrx="TRX-T"+System.currentTimeMillis();
			Transaksi trxBaru=new Transaksi (idTrx, "Debit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Tarik tunai Rp"+nominal+" berhasil. saldo saat ini: Rp"+saldo);
		}else {
			System.out.println("Gagal: nominal tidak valid atau saldo tidak mencukupi");
		}
	}
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini");
		}else {
			for (Transaksi trx:riwayatTransaksi) {
				trx.cetakDetail();
			}
		}
	}
	public void cekInformasi() {
		System.out.println("---INFO REKENING---");
		System.out.println("No.Rekening : "+nomorRekening);
		System.out.println("Nama Pemilik : "+namaPemilik);
		System.out.println("Saldo Akhir : Rp"+saldo);
	}
}
