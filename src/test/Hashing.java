package test;

/**
 * Created by abhishek on 12/3/16.
 */
public class Hashing {
    public static void main(String ar[]) {
        System.out.println(com.google.common.hash.Hashing.consistentHash(com.google.common.hash.Hashing.md5().hashUnencodedChars("aas"), 100));
    }
}
