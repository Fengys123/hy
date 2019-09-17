import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MyXmlPraser {
    private final int size_movie = 28;
    private final int size_people = 28;

    Random random = new Random(System.currentTimeMillis());

    public Map<String,Integer> getPosition(){

        Map<String,Integer> map = new HashMap<>();
        map.put("x",new Integer(random.nextInt(600)));
        map.put("y",new Integer(random.nextInt(600)));
        return map;
    }

    public int x0 = 100;
    public int x1 = 1100;
    public int x2 = 2100;
    public int x3 = 3100;


    boolean flag1 = true;
    public int y_hero1 = 0;
    public int y_hero2 = 0;
    public Map<String,Integer> getPositionOfHero(){

        Map<String,Integer> map = new HashMap<>();
        if(flag) {
            map.put("x",new Integer(x1));
            map.put("y",new Integer(y_hero1));
            y_hero1 = y_hero1 + 250;
            flag = false;
        } else {
            map.put("x",new Integer(x2));
            map.put("y",new Integer(y_hero2));
            y_hero2 = y_hero2 + 250;
            flag = true;
        }
        return map;
    }

    boolean flag = true;
    public int y_movie1 = 500;
    public int y_movie2 = 500;
    public Map<String,Integer> getPositionOfMovie(){

        Map<String,Integer> map = new HashMap<>();
        if(flag) {
            map.put("x",new Integer(x0));
            map.put("y",new Integer(y_movie1));
            y_movie1 = y_movie1 + 200;
            flag = false;
        } else {
            map.put("x",new Integer(x3));
            map.put("y",new Integer(y_movie2));
            y_movie2 = y_movie2 + 200;
            flag = true;
        }
        return map;
    }

    @Test
    public void buildXML() throws DocumentException, UnsupportedEncodingException {
        Document doc = DocumentHelper.createDocument();
        doc.addComment("这里是注释");
        Element root = doc.addElement("graph");
        root.addNamespace("viz", "http://www.w3.org/2001/XMLSchema");
        Element nodes = createNodes(root);
        Element edges = createEdges(root);

        File f = new File("C:\\Users\\fys\\Desktop\\test1.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(f);
        Element rot = document.getRootElement();
        Element foo;
        for(Iterator i = rot.elementIterator("node"); i.hasNext();) {
            foo = (Element) i.next();
            String[] s = new String[2];
            int p = 0;
            for(Iterator j = foo.elementIterator("data"); j.hasNext();p++){
                Element joo = (Element)j.next();
                s[p] = (String)joo.getData();
            }
            if(s[0].equals("hero")){
                createNodeOfPeople(nodes,foo.attributeValue("id"),s[1]);
            }
            if (s[0].equals("movie")) {
                createNodeOfMovie(nodes,foo.attributeValue("id"),s[1]);
            }
        }
        Element e;
        for(Iterator i = rot.elementIterator("edge"); i.hasNext();) {
            e = (Element)i.next();
            String source = (String)e.attribute("source").getData();
            String target = (String)e.attribute("target").getData();
            createEdge(edges, source, target);
        }
        writeToFile(doc);
    }

    public Element createNodes(Element element) {
        Element nodes = element.addElement("nodes");
        return nodes;
    }

    public Element createEdges(Element element) {
        Element edges = element.addElement("edges");
        return edges;
    }

    public void createNodeOfPeople(Element element, String id , String label) {
        Element node = element.addElement("node");
        node.addAttribute("id", id);
        node.addAttribute("label", label);

        Element element1 = node.addElement("attvalues");
        Element attvalue = element1.addElement("attvalue");
        attvalue.addAttribute("for","modularity_class");
        attvalue.addAttribute("value","0");

        Element viz_size = node.addElement("viz:size");
        viz_size.addAttribute("value",String.valueOf(size_people));
        Element viz_position = node.addElement("viz:position");
        Map<String, Integer> position = getPositionOfHero();
        viz_position.addAttribute("x", String.valueOf(position.get("x")));
        viz_position.addAttribute("y", String.valueOf(position.get("y")));
        viz_position.addAttribute("z", String.valueOf(0));
        Element viz_color = node.addElement("viz:color");
        viz_color.addAttribute("r", String.valueOf(236));
        viz_color.addAttribute("g", String.valueOf(81));
        viz_color.addAttribute("b", String.valueOf(72));
    }

    public void createNodeOfMovie(Element element, String id, String label) {
        Element node = element.addElement("node");
        node.addAttribute("id", id);
        node.addAttribute("label",label);

        Element element1 = node.addElement("attvalues");
        Element attvalue = element1.addElement("attvalue");
        attvalue.addAttribute("for","modularity_class");
        attvalue.addAttribute("value","7");

        Element viz_size = node.addElement("viz:size");
        viz_size.addAttribute("value",String.valueOf(size_movie));
        Element viz_position = node.addElement("viz:position");
        Map<String, Integer> position = getPositionOfMovie();
        viz_position.addAttribute("x", String.valueOf(position.get("x")));
        viz_position.addAttribute("y", String.valueOf(position.get("y")));
        viz_position.addAttribute("z", String.valueOf(0));
        Element viz_color = node.addElement("viz:color");
        viz_color.addAttribute("r", String.valueOf(236));
        viz_color.addAttribute("g", String.valueOf(81));
        viz_color.addAttribute("b", String.valueOf(72));
    }

    private int index_edge = 0;

    private void createEdge(Element element, String source, String target) {
        Element node = element.addElement("edge");
        node.addAttribute("id", String.valueOf(index_edge++));
        node.addAttribute("source", source);
        node.addAttribute("target", target);
        node.addElement("attvalues");
    }

    private void writeToFile(Document doc){
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        try {
            //创建一个输出流对象
            out = new FileWriter("E://new.xml");
            //创建一个dom4j创建xml的对象
            XMLWriter writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(doc);
            writer.close();
            System.out.print("生成XML文件成功");
        } catch (IOException ee) {
            System.out.print("生成XML文件失败");
            ee.printStackTrace();
        }
    }
}
