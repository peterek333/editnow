package pl.pl.mgr.editnow.service.util;

public interface ImageConverter<T> {

    public byte[] encode(T data);
    public byte[] encode(byte[] dataBytes);
    public T decode(byte[] encodedData);

}
