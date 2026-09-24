package pekan3;

public class Transaksi {
	private String idTransaksi;
	private String jenis;
	private double nominal;
	
	//construktor
	public Transaksi (String id, String jenis, double nominal) {
		this.idTransaksi=id;
		this.jenis=jenis;
		this.nominal=nominal;
	}
	//2. hanya menyediakan Getter
	public String getidtransaksi() {
		return idTransaksi;
	}
	public String getjenis() {
		return jenis;
	}
	public double getnominal() {
		return nominal;
	}
	public void cetakDetail() {
		System.out.println("ID: "+ idTransaksi+"|Jenis: "+jenis+" Nominal: Rp"+nominal);
	}
}

