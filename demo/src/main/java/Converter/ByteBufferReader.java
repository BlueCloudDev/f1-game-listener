package Converter;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class ByteBufferReader {
  public int Uint8(ByteBuffer bb){
    return bb.get() & 0xff;
  }

  public int Int8(ByteBuffer bb){
    return bb.get() & 0xff;
  }

  public int Uint16(ByteBuffer bb){
    return bb.getShort() & 0xFFFF;
  }

  public int Int16(ByteBuffer bb){
    return bb.getShort() & 0xffff;
  }

  public long Uint32(ByteBuffer bb) {
    byte[] buf4 = { 0x00, 0x00, 0x00, 0x00 };
    bb.get(buf4);
    ByteBuffer buffer = ByteBuffer.allocate(8).put(buf4).put(new byte[]{0, 0, 0, 0});
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    buffer.position(0);

    return buffer.getLong();
  }

  public int Int32(ByteBuffer bb) {
    return bb.getInt();
  }

  public String Uint64(ByteBuffer bb) {
    byte[] buf8 = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    bb.get(buf8);
    //ByteBuffer longBuf = ByteBuffer.wrap(buf8);
    BigInteger bigIndex = new BigInteger(buf8);
    return bigIndex.toString();
    /*Charset charset = Charset.defaultCharset();
    String val = charset.decode(longBuf).toString();
    return val;*/
  }

  public float NormalizedVector(ByteBuffer bb){
    int f = Int16(bb);
    return f / 32767.0f;
  }

  public float Float(ByteBuffer bb) {
    return bb.getFloat();
  }

  public double Double(ByteBuffer bb) {
    return bb.getDouble();
  }
}