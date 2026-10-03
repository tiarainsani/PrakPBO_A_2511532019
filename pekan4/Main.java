package pekan4;
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
			System.out.println("7. Simulasi Akhir Bulan (Khusu Tabungan)");
			System.out.println("8. Tarik Tunai Via Kartu Debit");
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
				
				System.out.println("Pilih produk");
				System.out.println("1. Tabungan Umum");
				System.out.println("2. Giro Bisnis");
				System.out.println("3. Rekening VIP");
				System.out.print("Pilih produk :");
				int jenis=input.nextInt();
				input.nextLine();
				
				Rekening rekeningBaru = null;
				if (jenis==1) {
					System.out.println("Masukkan suku bunga (%): ");
					double sukuBunga = input.nextDouble();
					input.nextLine();
					rekeningBaru=new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
					System.out.println("Rekening Tabungan Berhasil dibuat!");
				} else if (jenis==2) {
					System.out.println("Masukkan Batas Overdraft (Limit Pinjaman) : ");
					double batasOverdraft = input.nextDouble();
					input.nextLine();
					rekeningBaru=new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
					System.out.println("Rekening Giro berhasil dibuat!");
				}else if (jenis==3) {
				}else {
					System.out.println("Pilihan produk tidak valid!");
					break;		
				}
				daftarRekening.add(rekeningBaru);
				akunAktif=rekeningBaru;
				System.out.println("Rekening berhasil ditambahkan dan otomatis menjadi akun aktif.");
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
			case 7:
				if (akunAktif==null) {
					System.out.println("Error: belum ada akun aktif yang dipilih");
				}else {
					if (akunAktif instanceof RekeningTabungan) {
						RekeningTabungan tabungan=(RekeningTabungan)akunAktif;
						tabungan.tambahBungaAkhirBulan();
					}else {
						System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk rekening tabungan.");
					}
				}
				break;
			case 8:
			    if (akunAktif == null) {
			        System.out.println("Error: Belum ada akun aktif!");
			    } else if (akunAktif instanceof RekeningTabungan) {
			        System.out.print("Masukkan Nominal Tarik Kartu: ");
			        double nominal = input.nextDouble();
			        input.nextLine();
			        
			        System.out.print("Masukkan PIN Kartu: ");
			        pin = input.nextLine();

			        KartuDebit kartu = new KartuDebit((RekeningTabungan) akunAktif);
			        kartu.tarikTunaiKartu(nominal, pin);
			    } else {
			        System.out.println("Gagal: Kartu debit hanya tersedia untuk Rekening Tabungan.");
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
