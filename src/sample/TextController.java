package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本处理工具类
 * 主要实现json 文本的批量 格式化成 java 实体对象
 */
public class TextController {
    public static String format(String text) {
        if (text.isEmpty()) {
            return "";
        }
        String[] arr = text.replace(" ", "").split("}");//replaceAll("\r|\n", "").
//        System.out.println(text.replaceAll("\r|\n", "").replace(" ", ""));
        List<StringBean> beanList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].isEmpty()) {
                StringBean textBean = new StringBean();
                List<InfoBean> stringBean_list = new ArrayList<>();
                String[] arr_string = arr[i].split("\\{");
                textBean.setName(arr_string[0]);
//
                String textRight = arr_string[1];
                String[] arr_string_1 = textRight.split("\n");

                for (int j = 0; j < arr_string_1.length; j++) {
                    if (!arr_string_1[j].isEmpty()) {
                        char[] arr_ = arr_string_1[j].toCharArray();
                        int count = 0;
                        for (int k = 0; k < arr_.length; k++) {
                            if (arr_[k] == ':') {
                                if (count != 0) {
                                    arr_[k] = ';';
                                }
                                count++;
                            }
                        }
                        String textLeft = new String(arr_);
                        InfoBean bean = new InfoBean();
                        String[] arr_list_2 = textLeft.split(":");
                        if (arr_list_2.length > 1) {
                            bean.setDes(arr_list_2[1]);
                        }
                        String right = arr_list_2[0].replace("(", ",").replace(")", "");
                        //data=Array[AppInfoView]=optional
                        String[] right_1 = right.split(",");
                        bean.setName(right_1[0]);
                        if (right_1.length > 1) {
                            if (!right_1[1].equalsIgnoreCase("string") && right_1[1].contains("Array")) {
                                String[] right_1_1 = right_1[1].replace("]", "").split("\\[");
                                bean.setType(InfoBean.TYPE_LIST.LIST.type);
                                bean.setTypeName(right_1_1[1]);
                            } else if (!right_1[1].equalsIgnoreCase("string") && !right_1[1].contains("Array") && !right_1[1].contains("integer") && !right_1[1].contains("boolean") && !right_1[1].contains("number")) {
                                bean.setType(InfoBean.TYPE_LIST.BEAN.type);
                                bean.setTypeName(right_1[1]);
                            }
                        }
                        stringBean_list.add(bean);
                    }
                }
                textBean.setList(stringBean_list);
                beanList.add(textBean);
            }
        }
//        System.out.println(beanList.toString());
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < beanList.size(); i++) {
            if (i == 0) {
                content.append("public class " + beanList.get(i).getName() + " extends BaseModel{\n");
                if (beanList.get(i).getList() != null) {
                    for (InfoBean bean : beanList.get(i).getList()) {
                        content.append("/**" + bean.getDes() + "*/\n");
                        content.append("private " + (!bean.getType().equalsIgnoreCase("LIST") ? bean.getTypeName() : "List<" + bean.getTypeName() + "> ") + " " + bean.getName() + ";");

                    }
                }
            } else {
                content.append("public static class " + beanList.get(i).getName().replaceAll("\r|\n", "") + " extends BaseModel{\n");
                if (beanList.get(i).getList() != null) {
                    for (InfoBean bean : beanList.get(i).getList()) {
                        content.append("/**" + bean.getDes() + "*/\n");
                        content.append("private ").append(!bean.getType().equalsIgnoreCase("LIST") ? bean.getTypeName() : "List<" + bean.getTypeName() + ">").append(" ").append(bean.getName()).append(";");
                    }
                }

            }
            if (beanList.get(i).getList() != null) {
                for (InfoBean bean : beanList.get(i).getList()) {
                    content.append("public ").append(!bean.getType().equalsIgnoreCase("LIST") ? bean.getTypeName() : "List<" + bean.getTypeName() + "> ").append(" get").append(captureName(bean.getName())).append("() {");
                    content.append("return ").append(bean.getName()).append("; }");
                    content.append("public " + "void set").append(captureName(bean.getName()) + "(").append(!bean.getType().equalsIgnoreCase("LIST") ? bean.getTypeName() : "List<" + bean.getTypeName() + "> ").append(" " + bean.getName() + ") {");
                    content.append("this.").append(bean.getName()).append("=").append(bean.getName()).append("; }");
                }
            }
            if (i != 0) {
                content.append("}");
            }
        }
        content.append("}");
//        System.out.println(content.toString());
        return content.toString();
    }

    public static String captureName(String name) {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }

    public static class StringBean {
        private String name;
        List<InfoBean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<InfoBean> getList() {
            return list;
        }

        public void setList(List<InfoBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "StringBean{" +
                    "name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    public static class InfoBean {
        enum TYPE_LIST {
            STRING("String"),
            BEAN("BEAN"),
            LIST("LIST");
            private String type;

            TYPE_LIST(String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        private String name;
        private String des;
        private String type = TYPE_LIST.STRING.type;
        private String typeName = "String";

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "name='" + name + '\'' +
                    ", des='" + des + '\'' +
                    ", type='" + type + '\'' +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

}
