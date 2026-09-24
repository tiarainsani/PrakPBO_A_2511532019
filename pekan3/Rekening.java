package pekan3;
import java.util.*;
public class Rekening {
	//1. mengunci atribut dengan private
	private String nomorRekening;
	private String namaPemilik;
	private double saldo;
	private String pin;
	
	//implementasi asosiasi (1-to-many}
	ArrayList<Transaksi>riwayatTransaksi;
	public Rekening (String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening=nomor;
		this.namaPemilik=nama;
		this.saldo=saldoAwal;
		
		//validasi pin di dalam construktor
		if (pinAwal.length()==6) {
			this.pin=pinAwal;
		}else {
			System.out.println("Peringatan: PIN harus 6 digit! menggunakan PIN default 123456");
			this.pin="123456";
		}
		
		//wajib menginisialisasi ArrayList di dalam construktor agar tidak NullPointerException
		this.riwayatTransaksi=new ArrayList<>();
		System.out.println("Rekening atas nama "+ namaPemilik+ " berhasil dibuat.");
	}
	//3. Getter untuk atribut yang diizinkan dibaca publik
	public String getnomorRekening() {
		return nomorRekening;
	}
	public String getnamaPemilik() {
		return namaPemilik;
	}
	//4. method otentikasi internal (validasi enkapsulasi)
	public boolean otentikasi(String inputPin) {
		return this.pin.equals(inputPin);
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
