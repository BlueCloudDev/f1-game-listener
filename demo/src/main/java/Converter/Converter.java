package Converter;

public class Converter {
  public short Uint8(byte b){
    return b;
  }

  public short Uint16(short s){
    return (short) (s & 0xffff);
  }

  public Long Uint64(long l){
    return l;
  }

  public float NormalizedVector(short f){
      return f / 32767.0f;
  }

  public long Uint32(int i){
      return i;
  }
}
