package sample.jsonformat;

/**
 * 格式化通用json  字符串 工具类
 */
class JSONUtilsReplace {
    private final String newContent;

    JSONUtilsReplace(String newContent) {
        // newContent:需要新写入的内容。
        this.newContent = newContent;
    }

    String start() {
        System.out.println(newContent);
        return format(newContent);
    }

    /**
     * 格式化json 字符串
     */
    private String format(String str) {
        str = str.replaceAll("\r|\n|\r", "");
        return str;
    }
}