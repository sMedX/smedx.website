package app.core.Utils;

import app.core.DB.Services.ArticleService;
import app.core.DB.Services.NewsService;
import app.core.DB.DataModel.News;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.jsoup.select.Elements;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton_arakcheyev on 11/12/2018.
 */
@Service("migrationUtil")
public class MigrationUtil {

    private static MigrationUtil instance;

    protected static Logger logger = Logger.getLogger(MigrationUtil.class.getName());

    @Resource
    private WebApplicationContext servletAppContext;

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "newsService")
    private NewsService newsService;

    public MigrationUtil() {
        instance = this;
    }

    public static MigrationUtil getInstance() {
        if (instance == null) {
            instance = new MigrationUtil();
        }
        return instance;
    }

    public void migrateNews() {
        logger.info("***************** Starting migration news ******************");
        System.out.println("***************** Starting migration news ******************");
        try {
            List<News> newsList = new ArrayList<>();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ServletContext context = servletAppContext.getServletContext();
            String absolutePath = context.getRealPath("/static/files");
            Document document = documentBuilder.parse(absolutePath + "/Squarespace-Wordpress-Export-12-11-2018.xml");
            NodeList items = document.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeType() != Node.TEXT_NODE) {
                    NodeList itemProps = item.getChildNodes();
                    /*for (int j = 0; j < itemProps.getLength(); j++) {
                        //System.out.println(itemProps.item(j));
                        Node itemProp = itemProps.item(j);
                        if (itemProp.getNodeType() != Node.TEXT_NODE) {
                            System.out.println(i + ":" + j);
                            System.out.println("<" + itemProp.getNodeName() + ">:" + itemProp.getChildNodes().item(0).getTextContent());
                        }
                    }*/
                    if (itemProps.getLength() > 15) {
                        Node linkProp = itemProps.item(3);
                        if (linkProp.getNodeType() != Node.TEXT_NODE
                                && linkProp.getChildNodes().item(0).getTextContent().startsWith("/newsblog/")
                                && itemProps.item(15).getNodeType() != Node.TEXT_NODE
                                && itemProps.item(15).getNodeName().equalsIgnoreCase("wp:status")
                                && !itemProps.item(15).getChildNodes().item(0).getTextContent().equalsIgnoreCase("draft")) {
                            //System.out.println("Parsing entry #" + i);
                            News news = new News();
                            try {
                                news.setLink(linkProp.getChildNodes().item(0).getTextContent().replaceAll("/newsblog/", "").replaceAll("/", "-"));
                                news.setTitle(itemProps.item(1).getChildNodes().item(0).getTextContent());
                                news.setBody(itemProps.item(5).getChildNodes().item(0).getTextContent());
                                if (news.getBody().indexOf("<img src=") > -1 && news.getBody().indexOf("<img src=") < 150) {
                                    /*System.out.println("************ full ************");
                                    System.out.println(news.getBody().substring(
                                            news.getBody().indexOf("<img src=") + 9, 250)
                                    );*/
                                    //System.out.println("************ cropped ************");
                                    String coverImage = news.getBody().substring(
                                            news.getBody().indexOf("<img src=") + 10,
                                            news.getBody().indexOf("alt=\"\"") - 2
                                    );
                                    //System.out.println(coverImage);
                                    //System.out.println("************ end ************");
                                    String imageTag = news.getBody().substring(
                                            news.getBody().indexOf("<img src="),
                                            news.getBody().indexOf(">") + 1
                                    );
                                    news.setCoverImage(coverImage);

                                    //TODO: replace cover image from body
                                    //System.out.println("****** old body: *****");
                                    //System.out.println(news.getBody().substring(0, 450));
                                    //System.out.println("****** old body *****");
                                    String newsBody = news.getBody().replaceAll(imageTag, "");
                                    //System.out.println(newsBody);
                                    //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                                    org.jsoup.nodes.Document doc = Jsoup.parse(newsBody);
                                    org.jsoup.nodes.Element body = doc.body();
                                    StringBuilder resultBody = new StringBuilder();
                                    Elements els = body.select("td");
                                    for (org.jsoup.nodes.Element e : els) {
                                        //resultBody.append(e.html()).append("<br>");
                                        Elements subels = e.children();
                                        for (org.jsoup.nodes.Element sube : subels) {
                                            if (!sube.tag().getName().equalsIgnoreCase("table")) {
                                                Elements csses = sube.select("style");
                                                for (org.jsoup.nodes.Element css : csses) {
                                                    css.remove();
                                                }
                                                resultBody.append(sube.html()).append("<br>");
                                            }
                                        }
                                    }
                                    newsBody = resultBody.toString();

                                    //System.out.println(newsBody);
                                    //System.out.println("*********************************************");
                                    news.setBody(newsBody);
                                    //System.out.println("****** New body: *****");
                                    //System.out.println(news.getBody().substring(0, 450));
                                    //System.out.println("****** New body *****");
                                }

                                news.setDescription(itemProps.item(7).getChildNodes().item(0).getTextContent());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                /*String timeValue = itemProps.item(19).getChildNodes().item(0).getTextContent();
                                System.out.println("Time Value: " + timeValue);

                                System.out.println("Time parse result: " + simpleDateFormat.parse(timeValue).toString());
                                Long timeLong = simpleDateFormat.parse(itemProps.item(19).getChildNodes().item(0).getTextContent()).getTime();
                                System.out.println("Time long is: " + timeLong);
                                Timestamp time = new Timestamp(timeLong);
                                System.out.println(time.toString());*/
                                news.setAdded(
                                        new Timestamp(
                                                simpleDateFormat.parse(
                                                        itemProps.item(19).getChildNodes().item(0).getTextContent()
                                                ).getTime()
                                        )
                                );

                                StringBuilder tagsSB = new StringBuilder();
                                tagsSB.append("[");
                                for (int j = 25; j < itemProps.getLength(); j++) {
                                    //System.out.println(itemProps.item(j));
                                    Node itemProp = itemProps.item(j);
                                    if (itemProp.getNodeType() != Node.TEXT_NODE
                                            && itemProp.getNodeName().equalsIgnoreCase("category")) {
                                        //System.out.println(i + ":" + j);
                                        //System.out.println(j + ":<" + itemProp.getNodeName() + ">:" + itemProp.getChildNodes().item(0).getTextContent());
                                        tagsSB.append(itemProp.getChildNodes().item(0).getTextContent()).append(",");
                                    }
                                }
                                tagsSB.append("]");
                                news.setTags(tagsSB.toString());
                                newsService.addNews(news);
                                ///System.out.println("Adding entry #" + i);
                                newsList.add(news);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                                System.out.println("Error parsing news [" + linkProp.getChildNodes().item(0).getTextContent() + "]");
                            }
                        }
                    }


                }
                //System.out.println(item);
            }
            System.out.println(newsList.size());
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        } catch (SAXException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
        System.out.println("****************** News Migration Ended ********************");

        logger.info("****************** News Migration Ended ********************");

    }
}
