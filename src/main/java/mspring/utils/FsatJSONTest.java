package mspring.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

/**
 * fastjson在pojo与json中引入了JSONOBJECT的概念
 * JSON.parseObject: json->pojo
 * Json.parseArray  json->list<pojo>
 *
 * 如果我们没有对应的pojo类可以转化成jsonobject获取
 */
public class FsatJSONTest {
    public static void main(String[] args) {


        student student = new student();
        student.setAge(12);
        student.setDate(new Date());
        student.setName("lqx");
        pk pk = new pk();
        pk.setP(5);
        pk.setK(8);
        student.setPk(pk);

        String s = JSONObject.toJSONString(student);
        System.out.println(s);
        //处理日期方式1: JSONObject.toJSONStringWithDateFormat
        //System.out.println(JSONObject.toJSONStringWithDateFormat(student, "yyyy-MM-dd HH:mm:ss"));
        //String s= JSONObject.toJSONStringWithDateFormat(student, "yyyy-MM-dd HH:mm:ss");

        student student1 = JSONObject.parseObject(s, student.class);
        System.out.println(student1);

        Map<String, Object> map = JSONObject.parseObject(s, Map.class);
        System.out.println(map);

        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(jsonObject);

    }


}

class student {
    int age;
    String name;

    //处理日期方式2:
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    Date date;
    pk pk;

    public pk getPk() {
        return pk;
    }

    public void setPk(pk pk) {
        this.pk = pk;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", pk=" + pk +
                '}';
    }

    public static void main(String[] args) {
        try {
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);

            // 创建客户端socket
            Socket socket = new Socket();

            //循环监听等待客户端的连接
            while(true){
                // 监听客户端
                socket = serverSocket.accept();

                InetAddress address=socket.getInetAddress();
                System.out.println("当前客户端的IP："+address.getHostAddress());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}

class pk {
    int p;
    int k;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "pk{" +
                "p=" + p +
                ", k=" + k +
                '}';
    }

    public static void main(String[] args) throws IOException {
        try {
            // 和服务器创建连接
            Socket socket = new Socket("127.0.0.1", 8088);

            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("客户端发送信息");
            pw.flush();

            socket.shutdownOutput();

            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器返回信息：" + info);
            }

            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}