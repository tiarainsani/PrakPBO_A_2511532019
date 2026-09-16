package pekan1;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	public Rekening (String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
	}
	public void setorTunai (double nominal) {
		if (nominal>=10000) {
			saldo += nominal;
			System.out.println("Setor tunai Rp"+nominal+" berhasil. Saldo saat ini: Rp"+saldo);
		}else {
			System.out.println("Gagal: Nominal setor minimal harus 10.000");
		}
	}
	public void tarikTunai (double tarik) {
		if (tarik>=10000) {
			if (tarik>saldo) {
			System.out.println("error tarik tunai sebesar "+tarik+" gagal. Saldo saat ini hanya Rp"+saldo);
			}else {
			saldo-= tarik;
			System.out.println("tarik tunai sebesar "+tarik+" berhasil. Saldo saat ini tinggal Rp"+saldo);
			}
		}else {
			System.out.println("minimal tarik tunai 10.000");
		}
	}
	public void cekInformasi() {
		System.out.println("---INFO REKENING---");
		System.out.println("No.Rekening : "+nomorRekening);
		System.out.println("Nama Pemilik : "+namaPemilik);
		System.out.println("Saldo Akhir : Rp"+saldo);
	}
}
