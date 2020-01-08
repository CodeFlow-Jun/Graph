import java.io.File;
import java.io.IOException;
import java.util.IllformedLocaleException;
import java.util.Scanner;

public class AdjMatrix {

    //1.定义该类的成员变量，图中有V个顶点，E条边，形成了一个用来表示图的邻接矩阵adj，邻接矩阵用二维数组来表示
    private int V;
    private int E;
    private int[][] adj;

    //2.定义该类的构造函数，传来的参数是文件的名称filename
    public AdjMatrix(String filename) {

        //4.具体写构造函数中的逻辑，首先要将filename转换成file这个对象
        File file = new File(filename);

        //5.然后读取file对象文件中的内容，生成内容对象scanner，try(){}catch{}处理异常
        try(Scanner scanner = new Scanner(file)){

            //6.读取文本中的第一行
            //  第一个数，图中顶点的个数
            //  第二个数，图中边的个数
            //  根据顶点的个数就可以定义表示图的二维数组
            V=scanner.nextInt();
            if (V<0) throw new IllegalArgumentException("V must be non-negative");
            adj=new int[V][V];

            E=scanner.nextInt();
            if (E<0) throw new IllegalArgumentException("E must be non-negative");
            //7.读取接下来的E行，也就是边的信息
            //  每一行都是邻接矩阵中的一个坐标，表示该两点之间存在边，要置1。
            for (int i=0; i<E ;i++){
                //a,b属于[0,V-1]
                int a = scanner.nextInt();
                validationVertix(a);
                int b = scanner.nextInt();
                validationVertix(b);

                //简单图没有自环边(主对角线为0)
                if (a == b) throw new IllegalArgumentException("Self Loop is detected!");
                //简单图没有平行边(矩阵中没置1前就存在的边)
                if (adj[a][b]==1) throw new IllformedLocaleException("Parellel Edges are detected!!");

                adj[a][b] = 1;
                adj[b][a] = 1;//关于主对角线对称
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //专门用来判断传来的参数是否合法
    private void validationVertix(int v){
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " +  v + " is invalid." );
    }

    //补充一些对用户有用的的接口
    //   因为类中的成员变量V，E都是私有的，防止用户修改
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }

    //8.为了方便我们测试，把图的类对象打印出来，所以要覆盖该类的toString方法
    //  返回的是一个拼接的String，方法名是toString()，用StringBuilder类组建该类的字符串
    //  最后返回的拼接的字符串一共两部分：
    //  第一部分是图的基本信息（文件内容的第一行）
    //  第二部分是图的表示信息，邻接矩阵（二维数组）
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("V = %d, E = %d\n", V, E));

        for (int i =0; i<V ; i++) {
            for (int j = 0 ; j<V ; j++) {
                sb.append(String.format("%d\t",adj[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString(); //返回的是StringBuilder类的对象
    }

    //3.在main函数中构造该类的对象adjMatrix，要提供一个参数，就是提供图基本信息的具体文件名称
    public static void main(String[] args) {
        AdjMatrix adjMatrix = new AdjMatrix("g.txt");
        //9.打印该类的对象，测试结果
        System.out.println(adjMatrix);
    }

}
