package mjava.io;

import java.io.File;

/**
 * java.io.File类：文件和文件目录路径的抽象表示形式，与平台无关
 * File 能新建、删除、重命名文件和目录，但 File 不能访问文件内容本身。 如果需要访问文件内容本身，则需要使用输入/输出流。
 * 想要在Java程序中表示一个真实存在的文件或目录，那么必须有一个File对 象，但是Java程序中的一个File对象，可能没有一个真实存在的文件或目录。
 * File对象可以作为参数传递给流的构造器
 * <p>
 * <p>
 *  File类的获取功能
 *  public String getAbsolutePath()：获取绝对路径
 *  public String getPath() ：获取路径
 *  public String getName() ：获取名称
 *  public String getParent()：获取上层文件目录路径。若无，返回null
 *  public long length() ：获取文件长度（即：字节数）。不能获取目录的长度。
 *  public long lastModified() ：获取最后一次的修改时间，毫秒值
 *  public String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组
 *  public File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组
 *  File类的重命名功能
 *  public boolean renameTo(File dest):把文件重命名为指定的文件路径
 *  File类的判断功能
 *  public boolean isDirectory()：判断是否是文件目录
 *  public boolean isFile() ：判断是否是文件
 *  public boolean exists() ：判断是否存在
 *  public boolean canRead() ：判断是否可读
 *  public boolean canWrite() ：判断是否可写
 *  public boolean isHidden() ：判断是否隐藏
 *  File类的创建功能
 *  public boolean createNewFile() ：创建文件。若文件存在，则不创建，返回false
 *  public boolean mkdir() ：创建文件目录。如果此文件目录存在，就不创建了。 如果此文件目录的上层目录不存在，也不创建。
 *  public boolean mkdirs() ：创建文件目录。如果上层文件目录不存在，一并创建
 * 注意事项：如果你创建文件或者文件目录没有写盘符路径，那么，默认在项目 路径下。
 *  File类的删除功能
 *  public boolean delete()：删除文件或者文件夹 删除
 * 注意事项： Java中的删除不走回收站。 要删除一个文件目录，请注意该文件目录内不能包含文件或者文件目录
 */
public class FileTest {
    public static void main(String[] args) {
        File file = new File("D:\\ssm");
        if (!file.exists()) {
            System.out.println("create");
            file.mkdir();
        }

    }
}
