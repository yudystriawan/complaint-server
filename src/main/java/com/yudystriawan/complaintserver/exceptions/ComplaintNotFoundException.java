package com.yudystriawan.complaintserver.exceptions;

public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(Integer id) {
        super("Pengaduan id:"+id+" tidak ditemukan");
    }
}
