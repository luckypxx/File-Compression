package top.bingoxin.uncompression;

import com.sun.javaws.exceptions.UnsignedAccessViolationException;
import top.bingoxin.huffmantree.HuffmanTree;
import top.bingoxin.huffmantree.HuffmanTreeNode;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class Uncompression {

    public String _filePath = null;
    public HuffmanTree _huffmanTree;

    public Map<Character,Integer> GetMap(StringBuilder str){
        String[] fileInfo = str.toString().split(",");
        int size = fileInfo.length;

        Map<Character,Integer> map = new HashMap<Character,Integer>();

        for(int i = 0 ;i < size - 1;i += 2){
            map.put(fileInfo[i].charAt(0),new Integer(fileInfo[i + 1]));
        }
        return map;
    }

    public int getNum(Map<Character,Integer> map){
        int num = 0;
        int size = map.size();

        Set<Map.Entry<Character,Integer>> set = map.entrySet();
        Iterator<Map.Entry<Character,Integer>> iterator = set.iterator();
        while(iterator.hasNext()){
            num += iterator.next().getValue();
        }
        return num;
    }

    /**
     * 还原原文件
     * */
    public void writeUncompressionFile(byte[] data,int num) throws IOException {

        int size = data.length;
        ArrayList<Byte> writeData = new ArrayList<>();//存放处理后的数据
        File fileIn = new File(_filePath + "解压后的文件.txt");
        FileOutputStream fileOut = new FileOutputStream(fileIn);
        HuffmanTreeNode pRoot = _huffmanTree._pRoot;

        //将压缩后文件的每一位变为数组
        Queue<Queue> queue1 = new LinkedBlockingDeque<>();

        for(int j =0;j < size;++j) {

            Queue queue = new LinkedBlockingDeque();
            byte b = data[j];

            for (int a = 0; a < 8; ++a) {
                if ((b & (byte) 1) > (byte) 0) {
                    ((LinkedBlockingDeque) queue).addFirst("1");
                } else {
                    ((LinkedBlockingDeque) queue).addFirst("0");
                }

                b >>= 1;
            }
            queue1.add(queue);
        }

        for(int j =0;j < size;++j) {
            byte b = data[j];
            Queue queue = ((LinkedBlockingDeque<Queue>) queue1).getFirst();

            for (int a = 0; a < 8; ++a) {
                if (queue.peek().equals("1")) {
                    pRoot = pRoot._pRight;
                } else {
                    pRoot = pRoot._pLeft;
                }

                queue.remove();
                b >>= 1;

                if(pRoot._pLeft == null && pRoot._pRight == null){

                    writeData.add(pRoot._weight.obj.toString().getBytes()[0]);
                    if(writeData.size() == num){
                        break;
                    }
                    pRoot = _huffmanTree._pRoot;
                }
            }

            queue1.remove();
        }

        byte[] wd = new byte[writeData.size()];
        int wdSize = wd.length;

        for(int i = 0;i < wdSize;++i){

            wd[i] = writeData.remove(0);
        }
        fileOut.write(wd);
        fileOut.close();
    }

    public void GetInfo(File fileOut) throws IOException {

        //记录压缩文件信息的行数
        Integer lineNo = null;

        //用于存储压缩文件的信息
        StringBuilder fileInfo = new StringBuilder("");
        StringBuilder fileCom = new StringBuilder();

        //获取压缩后文件的输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileOut),"GBK"));

        //获取压缩信息统计的行数
        lineNo = new Integer(br.readLine());

        //读取原文件内容统计
        for(int i = 0;i < lineNo;++i){

            fileInfo.append(br.readLine()).append(",");
        }

        //用于存储每次读取的一行内容
        String string = null;
        while((string = br.readLine()) != null){
            fileCom.append(string);
        }

        br.close();

        Map<Character,Integer> map = GetMap(fileInfo);

        int chNum = getNum(map);
        System.out.println(chNum);

        //重新构造Huffman树
        _huffmanTree = new HuffmanTree(map);

        //对压缩后的内容进行解析
        byte[] data = fileCom.toString().getBytes("GBK");

        writeUncompressionFile(data,chNum);

    }

    public Uncompression(String filePath) throws IOException {
        //读取压缩文件中的内容说明
        System.out.println("开始解压");

        this._filePath = filePath;

        File fileOut = new File(filePath +  "压缩后文件.txt");

        GetInfo(fileOut);

        System.out.println("结束");

    }
}
