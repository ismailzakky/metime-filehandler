package com.cus.metime.filehandler.dto.messaging;


import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by C-US on 9/26/2017.
 */
public class FileTransferDTO implements Serializable {


    private String fileName;
    private byte[] fileStream;
    private String fileExtension;

    public FileTransferDTO(String fileName, byte[] fileStream, String fileExtension) {
        this.fileName = fileName;
        this.fileStream = fileStream;
        this.fileExtension = fileExtension;
    }

    public FileTransferDTO() {

    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileStream() {
        return fileStream;
    }

    public void setFileStream(byte[] fileStream) {
        this.fileStream = fileStream;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return "FileTransferDTO{" +
            "fileName='" + fileName + '\'' +
            ", fileStream=" + Arrays.toString(fileStream) +
            ", fileExtension='" + fileExtension + '\'' +
            '}';
    }
}
