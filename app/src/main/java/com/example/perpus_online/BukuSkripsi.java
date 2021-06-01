package com.example.perpus_online;

public class BukuSkripsi {
    private String kode;
    private String judul;
    private String pengarang;
    private String universitas;
    private String tahunterbit;
    private String halaman;

    private String abstrak;
    private String status;
    private String imageKey;

    public BukuSkripsi(){}

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public void setUniversitas(java.lang.String universitas) {
        this.universitas = universitas;
    }

    public java.lang.String getUniversitas() {
        return universitas;
    }

    public String getTahunterbit() {
        return tahunterbit;
    }

    public void setTahunterbit(String tahunterbit) {
        this.tahunterbit = tahunterbit;
    }

    public String getAbstrak() {
        return abstrak;
    }

    public void setAbstrak(String abstrak) {
        this.abstrak = abstrak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public void setHalaman(String halaman) {
        this.halaman = halaman;
    }

    public String getHalaman() {
        return halaman;
    }

    public BukuSkripsi(String kode, String judul, String pengarang, String universitas,  String halaman, String tahunterbit, String abstrak, String status, String imageKey){
        this.kode = kode;
        this.judul = judul;
        this.pengarang = pengarang;
        this.universitas = universitas;
        this.halaman = halaman;
        this.tahunterbit = tahunterbit;
        this.abstrak = abstrak;
        this.status = status;
        this.imageKey = imageKey;
    }
}

