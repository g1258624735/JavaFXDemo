package sample;

import javafx.scene.control.Label;

import java.io.*;

/**
 * 替换文件（如果该文件含有子目录，则包括子目录所有文件）中某个字符串并写入新内容（Java代码实现）.
 * <p>
 * 原理：逐行读取源文件的内容，一边读取一边同时写一个*.tmp的文件。
 * 当读取的行中发现有需要被替换和改写的目标内容‘行’时候，用新的内容‘行’替换之。
 * 最终，删掉源文件，把*.tmp的文件重命名为源文件名字。
 * <p>
 * 注意！代码功能是逐行读取一个字符串，然后检测该字符串‘行’中是否含有替换的内容，有则用新的字符串‘行’替换源文件中该处整个字符串‘行’。没有则继续读。
 * 注意！替换是基于‘行’，逐行逐行的替换！
 */
public class Modify {
    /**
     * 输出文件路径
     */
    private String pathOut = System.getProperty("user.dir");
    // 操作目录。从该目录开始。该文件目录下及其所有子目录的文件都将被替换。
    private String pathIn = System.getProperty("user.dir");
    private final String target = "Emptry";  // target:需要被替换、改写的内容。
    private Label label_loading;
    private final String newContent;
    private final String[] files_arr = {"EmptryComponent.java", "EmptryModule.java", "EmptryPresenter.java", "EmptryView.java", "EmptryViewState.java"};

    public Modify(String newContent, Label label_loading) {
        // newContent:需要新写入的内容。
        this.newContent = newContent;
        this.label_loading = label_loading;
    }

    public void start() {
        System.out.println(pathOut);
        opeationDirectory();
    }

    public void opeationDirectory() {
        for (int i = 0; i < files_arr.length; i++) {
            File f = new File(pathOut + "/lib/" + files_arr[i]);
            if (f.isFile())
                operationFile(f);
        }
        label_loading.setText("文件生成成功！");
    }

    public void operationFile(File file) {
        try {
            label_loading.setText("开始读写...");
            System.out.println("开始读写。。。");
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));//数据流读取文件

            StringBuffer strBuffer = new StringBuffer();
            for (String temp; (temp = bufReader.readLine()) != null; temp = null) {
                if (temp.contains(target)) { //判断当前行是否存在想要替换掉的字符 -1表示存在
                    temp = temp.replaceAll(target, newContent);//替换为你想要的东东
                }
                strBuffer.append(temp);
                strBuffer.append(System.getProperty("line.separator"));//行与行之间的分割
            }
            bufReader.close();
            PrintWriter printWriter = new PrintWriter(pathOut + "\\" + file.getName().replace(target, newContent));//替换后输出的文件位置
            printWriter.write(strBuffer.toString().toCharArray());
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}