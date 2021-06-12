package org.zust.book.test;

import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import org.zust.interfaceapi.utils.FileUtil;

import javax.xml.bind.annotation.XmlElementDecl;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/8 14:30
 * @function:
 **/
public class jiexi {

    public static void main(String[] args) {

        File file = new File("C:/Users/12113/Desktop/13.epub");
        InputStream in = null;
        try {
            //从输入流当中读取epub格式文件
            EpubReader reader = new EpubReader();
            in = new FileInputStream(file);
            Book book = reader.readEpub(in);
            Resource coverImage = book.getCoverImage();
            //获取到书本的头部信息
            Metadata metadata = book.getMetadata();
//            System.out.println(metadata.getMetaAttribute("cover"));
            String coverId = metadata.getMetaAttribute("cover");
            System.out.println("FirstTitle为：" + metadata.getFirstTitle());
            //获取到书本的全部资源
            Resources resources = book.getResources();
            Resource newCoverR = null;
            Map jk = resources.getResourceMap();
            for (Object o : jk.keySet()) {
                if(coverId.equals(((Resource)jk.get(o)).getId())) {
                    newCoverR = (Resource)jk.get(o);
                    break;
                }
            }
            InputStream inn = coverImage.getInputStream();

            FileUtil.copyInputStreamToFile(inn,new File("D:/book/convert/111_cover.jpg"));

            System.out.println("所有资源数量为：" + resources.size());
            //获取所有的资源数据
            Collection<String> allHrefs = resources.getAllHrefs();
            for (String href : allHrefs) {
                Resource resource = resources.getByHref(href);
                //data就是资源的内容数据，可能是css,html,图片等等
                byte[] data = resource.getData();
                // 获取到内容的类型  css,html,还是图片
                MediaType mediaType = resource.getMediaType();
            }
            //获取到书本的内容资源
            List<Resource> contents = book.getContents();
            System.out.println("内容资源数量为：" + contents.size());
            //获取到书本的spine资源 线性排序
            Spine spine = book.getSpine();
            System.out.println("spine资源数量为：" + spine.size());
            //通过spine获取所有的数据
            List<SpineReference> spineReferences = spine.getSpineReferences();
            for (SpineReference spineReference : spineReferences) {
                Resource resource = spineReference.getResource();
                //data就是资源的内容数据，可能是css,html,图片等等
                byte[] data = resource.getData();
                // 获取到内容的类型  css,html,还是图片
                MediaType mediaType = resource.getMediaType();
            }
            //获取到书本的目录资源
            TableOfContents tableOfContents = book.getTableOfContents();
            System.out.println("目录资源数量为：" + tableOfContents.size());
            //获取到目录对应的资源数据
            List<TOCReference> tocReferences = tableOfContents.getTocReferences();
            for (TOCReference tocReference : tocReferences) {
                Resource resource = tocReference.getResource();
                //data就是资源的内容数据，可能是css,html,图片等等
                byte[] data = resource.getData();
                // 获取到内容的类型  css,html,还是图片
                MediaType mediaType = resource.getMediaType();
                if (tocReference.getChildren().size() > 0) {
                    //获取子目录的内容
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //一定要关闭资源
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
