package com.example.healing;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ModelFormPromosi {
    String Alamat;
    String KTP;
    String Kabupaten;
    String Kecamatan;
    String Provinsi;
    String Telepon;
    String hTiket;
    String namaWisata;
    String Deskripsi;
    String id;
    String EmailP;
    public ModelFormPromosi(){}

    public ModelFormPromosi(String alamat, String KTP, String kabupaten, String kecamatan, String provinsi, String telepon, String hTiket, String namaWisata ,String id, String EmailP) {
        this.Alamat = alamat;
        this.KTP = KTP;
        this.Kabupaten = kabupaten;
        this.Kecamatan = kecamatan;
        this.Provinsi = provinsi;
        this.Telepon = telepon;
        this.hTiket = hTiket;
        this.namaWisata = namaWisata;
        this.id = id;
        this.EmailP = EmailP;
    }

    public String getEmailP() {
        return EmailP;
    }

    public void setEmailP(String emailP) {
        EmailP = emailP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTelepon() { return Telepon; }

    public void setTelepon(String telepon) { Telepon = telepon; }

    public String gethTiket() { return hTiket; }

    public void sethTiket(String hTiket) { this.hTiket = hTiket; }

    public String getNamaWisata() { return namaWisata; }

    public void setNamaWisata(String namaWisata) { this.namaWisata = namaWisata; }

    public String getDeskripsi() { return Deskripsi; }

    public void setDeskripsi(String deskripsi) { Deskripsi = deskripsi; }


}
