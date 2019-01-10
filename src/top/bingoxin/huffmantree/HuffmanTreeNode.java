package top.bingoxin.huffmantree;

import java.util.*;

public class HuffmanTreeNode {

    public HuffmanTreeNode _pLeft;
    public HuffmanTreeNode _pRight;
    public HuffmanTreeNode _pParent;
    public Weight _weight;



    public HuffmanTreeNode(Map.Entry<Character,Integer> map) {

        _weight = new Weight(map.getKey(),map.getValue());
        _weight.count = map.getValue();
        _weight.obj = map.getKey();

    }

    public HuffmanTreeNode(Weight weight){

        this._weight = new Weight();
        this._weight.obj = weight.obj;
        this._weight.count = weight.count;
        this._weight._strCode = weight._strCode;
    }
}
