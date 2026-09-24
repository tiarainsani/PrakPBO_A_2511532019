package pekan3;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
	public static void main (String[] args) {
		Scanner input = new Scanner (System.in);
		Rekening akunAktif=null;
		boolean isRunning=true;
		ArrayList<Rekening> daftarRekening=new ArrayList<>();
		
		System.out.println("===SISTEM PERBANKAN MINI===");
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat)");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu :");
			int pilihan = input.nextInt();
			input.nextLine();
			switch (pilihan) {
			case 1:
				System.out.print("Masukkan No Rekening:");
				String no= input.nextLine();
				System.out.print("Masukkan Nama Pemilik:");
				String nama=input.nextLine();
				System.out.print("Masukkan Saldo Awal:");
				double saldo = input.nextDouble();
				input.nextLine();
				System.out.print("Buat PIN (6 digit): ");
				String pin = input.nextLine();
				
				Rekening rekeningBaru=new Rekening(no, nama, saldo, pin);
				daftarRekening.add(rekeningBaru);
				
				//instansiasi object / Menjalankan Construktor
				akunAktif=rekeningBaru;
				System.out.println("Rekening berhasil ditambahkan dan otomatis menjadi akun Aktif.");
				break;
			case 2:
				if (akunAktif ==null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
				}else {
					System.out.print("Masukkan nominal setor:");
					double setor =input.nextDouble();
					akunAktif.setorTunai(setor);//Memanggil Behavior/method
				}
				break;
			case 3:
				if (akunAktif ==null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
				}else {
					System.out.print("Masukkan PIN anda : ");
					String inputPin=input.nextLine();
					if (akunAktif.otentikasi(inputPin)) {
						System.out.print("Masukkan nominal tarik:");
						double tarik =input.nextDouble();
						input.nextLine();
						akunAktif.tarikTunai(tarik);//Memanggil Behavior/method
					}else {
						System.out.println("Akses ditolak: PIN yang anda masukkan salah");
					}
				}
				break;
			case 4:
				if (akunAktif==null) {
					System.out.println("Error: Anda belum membuka rekening~");
				}else {
					akunAktif.cekInformasi();
				}
				break;
			case 5:
				if (daftarRekening.isEmpty()) {
					System.out.println("Error: Belum ada rekening terdaftar di sistem!");
				}else {
					System.out.print("Masukkan no rekening yang dicari: ");
					String cariNo=input.nextLine();
					boolean ditemukan=false;
					
					for (Rekening rek : daftarRekening) {
						if (rek.getnomorRekening().equalsIgnoreCase(cariNo)) {
							ditemukan = true;
							if (akunAktif != null && akunAktif.getnomorRekening().equalsIgnoreCase(cariNo)) {
								System.out.println("Informasi: Akun dengan No Rekening " + cariNo + " (" + akunAktif.getnamaPemilik() + ") sedang aktif!");
							}else {
								akunAktif = rek;
								System.out.println("Berhasil berganti ke akun atas nama: " + akunAktif.getnamaPemilik());
							}
							break;
						}
					}
					if (!ditemukan) {
						System.out.println("Error: Nomor rekening tidak ditemukan!");
					}
				}
				break;
			case 6:
				if (akunAktif != null) {
				System.out.print("Masukkan PIN Anda: ");
				String inputPin = input.nextLine();
					if (akunAktif.otentikasi(inputPin)) {
						akunAktif.cetakMutasi();
					}else {
						System.out.println("Akses ditolak: PIN yang anda masukkan salah");
					}
				} else {
					System.out.println("Belum ada nomor rekening terdaftar di sistem");	
				}
				break;
			case 0:
				isRunning=false;
				System.out.println("Sistem ditutup.Terima Kasih!");
				break;
			default:
				System.out.println("Pilihan tidak valid!");
			}	
		}
		input.close();
	}	
}
