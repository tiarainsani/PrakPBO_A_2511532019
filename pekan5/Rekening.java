package pekan5;
import java.util.*;

public class Rekening {
	//1. mengunci atribut dengan private
	private String nomorRekening;
	private String namaPemilik;
	private String pin;
	
	//gunakan protected agar subclass bisa mengaksesnya langsung
	protected double saldo;
	
	protected ArrayList<Transaksi>riwayatTransaksi;
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
	public boolean tarikTunai(double nominal) {
		if (nominal<= 0) {
			System.out.println("Gagal:Nominal tarik harus lebih dari 0");
			return false;
		}else if (nominal>saldo) {
			System.out.println("Transaksi gagal: Saldo tidak mencukupi. Saldo Anda: Rp"+saldo);
			return false;
		}else {
			saldo-=nominal;
			String idTrx="TRX-T-"+System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx,"Debit",nominal));
			System.out.println("Tarik tunai Rp"+nominal+"berhasil. Sisa saldo: Rp"+saldo);
			return true;
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
	public void transfer(Rekening tujuan, double nominal, String pinInput) {
        // 1. Lakukan otentikasi PIN. Jika false, batalkan.
        if (!otentikasi(pinInput)) {
            System.out.println("Transfer Gagal: PIN yang Anda masukkan salah!");
            return;
        }
        
        // 2. Panggil this.tarikTunai(nominal) (menggunakan overriding dinamis)
        boolean tarikBerhasil = this.tarikTunai(nominal);
        
        // 3. Periksa kembalian nilai tarikTunai. Jika true, panggil tujuan.setorTunai(nominal)
        if (tarikBerhasil) {
            tujuan.setorTunai(nominal);
            System.out.println("Transfer sebesar Rp" + nominal + " ke rekening " + tujuan.getnomorRekening() + " berhasil!");
        } else {
            System.out.println("Transfer Gagal: Penarikan dana tidak dapat diproses.");
        }
    }
}
