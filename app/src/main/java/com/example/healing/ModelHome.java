package com.example.healing;

public class ModelHome {
    String Alamat;
    String KTP;
    String Kabupaten;
    String Kecamatan;
    String Provinsi;
    String Role;
    String Telepon;
    String hTiket;
    String namaWisata;
    String Deskripsi;

    public ModelHome(){}

    public ModelHome(String alamat, String KTP, String kabupaten, String kecamatan, String provinsi, String role, String telepon, String hTiket, String namaWisata,String Deskripsi) {
        this.Alamat = alamat;
        this.KTP = KTP;
        this.Kabupaten = kabupaten;
        this.Kecamatan = kecamatan;
        this.Provinsi = provinsi;
        this.Role = role;
        this.Telepon = telepon;
        this.hTiket = hTiket;
        this.namaWisata = namaWisata;
        this.Deskripsi = Deskripsi;
    }

    public String getAlamat() { return Alamat; }

    public void setAlamat(String alamat) { Alamat = alamat; }

    public String getKTP() { return KTP; }

    public void setKTP(String KTP) { this.KTP = KTP; }

    public String getKabupaten() { return Kabupaten; }

    public void setKabupaten(String kabupaten) { Kabupaten = kabupaten; }

    public String getKecamatan() { return Kecamatan; }

    public void setKecamatan(String kecamatan) { Kecamatan = kecamatan; }

    public String getProvinsi() { return Provinsi; }

    public void setProvinsi(String provinsi) { Provinsi = provinsi; }

    public String getRole() { return Role; }

    public void setRole(String role) { Role = role; }

    public String getTelepon() { return Telepon; }

    public void setTelepon(String telepon) { Telepon = telepon; }

    public String gethTiket() { return hTiket; }

    public void sethTiket(String hTiket) { this.hTiket = hTiket; }

    public String getNamaWisata() { return namaWisata; }

    public void setNamaWisata(String namaWisata) { this.namaWisata = namaWisata; }

    public String getDeskripsi() { return Deskripsi; }

    public void setDeskripsi(String deskripsi) { Deskripsi = deskripsi; }
}
