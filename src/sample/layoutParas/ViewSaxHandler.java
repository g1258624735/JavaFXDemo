package sample.layoutParas;

import com.intellij.icons.AllIcons;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 布局文件解析
 * 自动找到布局文件的id
 */
public class ViewSaxHandler extends DefaultHandler {
    private List<ViewPart> viewPartList;
    private String layoutPath = "";
    private AllIcons.Welcome.Project project;

//    public static void main(String[] args) {
//        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
//                "              android:orientation=\"vertical\"\n" +
//                "              android:layout_width=\"fill_parent\"\n" +
//                "              android:layout_height=\"fill_parent\">\n" +
//                "    <TextView\n" +
//                "            android:id=\"@id/hello_world\"\n" +
//                "            android:layout_width=\"fill_parent\"\n" +
//                "            android:layout_height=\"wrap_content\"\n" +
//                "            android:text=\"Hello World, MyActivity\"/>\n" +
//                "    <TextView\n" +
//                "            android:id=\"@+id/hello_world_plus\"\n" +
//                "            android:layout_width=\"fill_parent\"\n" +
//                "            android:layout_height=\"wrap_content\"\n" +
//                "            android:text=\"Hello World, MyActivity\"/>\n" +
//                "</LinearLayout>\n" +
//                "\n";
//
//
//    }

    /**
     * @param str 传入布局文件
     */
    public static String start(String str) {
        StringBuilder text_out = new StringBuilder();
        ViewSaxHandler handler = new ViewSaxHandler();
        try {
            handler.createViewList(str);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        List<ViewPart> viewParts = handler.getViewPartList();
        //@BindView(R2.id.recyclerView)
//        RecyclerView recyclerView;
        for (ViewPart viewPart : viewParts) {
            text_out.append("@BindView(R2.id.").append(viewPart.getId()).append(")\n");
            text_out.append(viewPart.getType()).append(" ").append(viewPart.getName()).append(";").append("\n");
            System.out.println(viewPart.toString());
        }
        return text_out.toString();
    }

    public void createViewList(String string) throws ParserConfigurationException, SAXException, IOException {
        InputStream xmlStream = new ByteArrayInputStream(string.getBytes("UTF-8"));
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlStream, this);
    }


    @Override
    public void startDocument() throws SAXException {
        if (viewPartList == null) {
            viewPartList = new ArrayList<ViewPart>();
        }
    }

    //
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("include")) {
//            String includeLayout = attributes.getValue("layout");
//            if (includeLayout != null) {
//                File file = new File(getLayoutPath(), includeLayout.replace("@layout", "") + ".xml");
//                if (file.exists()) {
//                    VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
//                    if (virtualFile == null) {
//                        return;
//                    }
//                    PsiFile psiFile = PsiManager.getInstance(getProject()).findFile(virtualFile);
//                    try {
//                        if (psiFile != null) {
//                            this.createViewList(psiFile.getText());
//                        }
//                    } catch (ParserConfigurationException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        } else {
            String id = attributes.getValue("android:id");
            if (id != null) {
                ViewPart viewPart = new ViewPart();
                viewPart.setType(qName);
                viewPart.setId(id.replace("@+id/", "").replace("@id/", "").replace("@android:id/", ""));
                viewPartList.add(viewPart);
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    public List<ViewPart> getViewPartList() {
        return viewPartList;
    }

    public String getLayoutPath() {
        return layoutPath;
    }

    public void setLayoutPath(String layoutPath) {
        this.layoutPath = layoutPath;
    }

    public AllIcons.Welcome.Project getProject() {
        return project;
    }

    //
    public void setProject(AllIcons.Welcome.Project project) {
        this.project = project;
    }
}
