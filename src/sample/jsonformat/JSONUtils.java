package sample.jsonformat;

import javafx.scene.control.Label;

import java.io.*;

/**
 * 格式化通用json  字符串 工具类
 */
public class JSONUtils {
    private final String newContent;

    public JSONUtils(String newContent) {
        // newContent:需要新写入的内容。
        this.newContent = newContent;
    }

    public String start() {
        System.out.println(newContent);
        return format(newContent);
    }

    /**
     * 格式化json 字符串
     */
    private String format(String str) {
        str = str.replace("/n", "");
        str = str.replace(" ", "");
        StringBuilder out = new StringBuilder();
        System.out.println(str);
        int level = 0;
        int i, j;
        boolean start = false, newLine = true;
        for (i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            switch (ch) {
                case '[':
                case '{':
                    newLine = true;
                    level++;
                    out.append("\n");// 换行
                    for (j = 0; j < level - 1; ++j) {// 缩进
                        out.append("\t");
                    }
                    break;
                case ']':
                case '}':
                    out.append("\n");// 换行
                    for (j = 0; j < level - 1; ++j) {// 缩进
                        out.append("\t");
                    }
                    level--;
                    break;
                case ',':
                    newLine = true;
                    break;
                case ':':
                    newLine = false;
                    break;
                case '\"':
                case '\'':
                    start = !start;
                    if (start && newLine) {
                        out.append("\n");// 换行
                        for (j = 0; j < level; ++j) {// 缩进
                            out.append("\t");
                        }
                    }
                    break;
            }
            out.append(ch);
        }
        return out.toString();
    }
}