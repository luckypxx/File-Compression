package top.bingoxin.huffmantree;


import java.util.*;

public class HuffmanTree {

    public HuffmanTreeNode _pRoot;
    public int _size;
    public HashMap<Character,String> map;

    /*
     * 根据权值将set升序排序
     * **/
    public List<HuffmanTreeNode> sortSet(List<HuffmanTreeNode> set){
        Collections.sort(set, new Comparator<HuffmanTreeNode>() {
            @Override
            public int compare(HuffmanTreeNode o1, HuffmanTreeNode o2) {
                int compare = o1._weight.count.compareTo(o2._weight.count);
                return compare;
            }
        });
        return set;
    }

    /**
     * 根据Huffman树进行Huffman编码,将Charater和其对应的编码存到map中
     * */
    public void getHuffmanCode(HuffmanTreeNode pRoot){

        if(pRoot == null){
            return;
        }

        getHuffmanCode(pRoot._pLeft);
        getHuffmanCode(pRoot._pRight);

        String str = pRoot._weight._strCode;
        HuffmanTreeNode pCur = pRoot;

        if(pCur._pLeft == null && pCur._pRight == null){
            HuffmanTreeNode pParent = pCur._pParent;
            while(pParent != null){

                if(pCur == pParent._pLeft){
                    str  = str.concat("0");

                }
                if(pCur == pParent._pRight){
                    str = str.concat("1");
                }

                pCur = pParent;
                pParent = pParent._pParent;
            }
            pRoot._weight._strCode = new StringBuilder(str).reverse().toString();
            this.map.put(pRoot._weight.obj,pRoot._weight._strCode);

        }

    }

    /*
     * 根据权值将map升序排序
     * **/
    public List<Map.Entry<Character,Integer>> sortList(Map<Character,Integer> map){
        List<Map.Entry<Character,Integer>> list = new ArrayList<Map.Entry<Character, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });
        return list;
    }

    /**
     * 用set中的结点，构建一棵Huffman树
     * */
    public List<Map.Entry<Character,Integer>> GetList(Map<Character,Integer> map){
        List<Map.Entry<Character,Integer>> list = sortList(map);
        return list;
    }

    /**
     * 构建一棵Huffman树
     * */
    public void BuildHuffmanTree( List<HuffmanTreeNode> set){
        //构建树
        while(set.size() > 1){

            HuffmanTreeNode pLeft = set.get(0);
            set.remove(0);
            set = sortSet(set);

            HuffmanTreeNode pRight = set.get(0);
            set.remove(0);
            set = sortSet(set);

            Weight weight = new Weight(pLeft._weight.obj,pLeft._weight.count+pRight._weight.count);
            HuffmanTreeNode pParent = new HuffmanTreeNode(weight);

            pParent._pLeft = pLeft;
            pParent._pRight = pRight;
            pLeft._pParent = pParent;
            pRight._pParent = pParent;
            set.add(pParent);
            set = sortSet(set);
        }

        _pRoot = set.get(0);

    }

    public HuffmanTree(Map<Character,Integer> mapInfo) {

        //对权值进行升序排序
        List<Map.Entry<Character,Integer>> list = GetList(mapInfo);

        int size = list.size();

        List<HuffmanTreeNode> set = new ArrayList<>();

        //构建Set
        for(int i = 0;i < size; ++i){
            HuffmanTreeNode huffmanTreeNode = new HuffmanTreeNode(list.get(i));
            set.add(huffmanTreeNode);
        }
        set = sortSet(set);
        _size = set.size();

        //将set中的结点构建一棵Huffman树
        BuildHuffmanTree(set);
       //对Huffman树进行编码,将Charater和其对应的编码存到map中
        this.map = new HashMap<>();
        getHuffmanCode(_pRoot);

    }


}
