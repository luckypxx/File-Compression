package top.bingoxin.huffmantree;

public class Weight {

    //Hash码
    public String _strCode;
    //权值
    public Integer count;
    //存储对象
    public Character obj;

    public Weight( Character obj,Integer count) {
        this.count = count;
        this.obj = obj;
        this._strCode = "";
    }

    public Weight(){
        _strCode = "";
    }
}
