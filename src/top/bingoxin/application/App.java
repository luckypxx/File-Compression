package top.bingoxin.application;

import top.bingoxin.compression.Compression;
import top.bingoxin.uncompression.Uncompression;


public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\\\Users\\\\pxx\\\\Desktop\\\\";
        String filename = "1.txt";
        Compression compression = new Compression();
        if(compression.FileCompression(filePath , filename)){
            System.out.println("文件"+filePath+ filename +"压缩成功");
        }
        Uncompression uncompression = new Uncompression(filePath);
    }
}
