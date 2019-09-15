import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class MyXmlPraser {
    public static void main(String[] args) {
        long lasting = System.currentTimeMillis();
        try {
            File f = new File("C:\\Users\\fys\\Desktop\\txt.xml");
            SAXReader reader = new SAXReader();
            Document document = reader.read(f);
            Element root = document.getRootElement();
            Element foo;
            for(Iterator i = root.elementIterator("VALUE"); i.hasNext();) {
                foo = (Element) i.next();

                System.out.print("车牌号码:" + new String(foo.elementText("NO").getBytes(), "UTF-8"));
                System.out.println("车主地址:" + new String(foo.elementText("NO").getBytes(), "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
