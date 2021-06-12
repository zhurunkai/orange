package org.zust.interfaceapi.utils;

import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/8 12:01
 * @function:
 **/
public class BookUtils {

    // 将mobi等格式文件转化为epub
    // origin代表源文件路径，target代表目标文件路径
    public static boolean convert(String origin,String target){
        // 若该文件夹不存在，则创建一个文件夹
        File filePath = new File("D:/book/convert/");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        boolean flag = false;
        try{
            Runtime.getRuntime().exec("cmd.exe /C start ebook-convert "+origin+" "+target);
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean getEpub(File file,String fname){

        boolean flag = false;

//        File file = new File("D:/book/convert/cnvs_vpl_bible.epub");
        InputStream in = null;
        try {
            //从输入流当中读取epub格式文件
            EpubReader reader = new EpubReader();
            in = new FileInputStream(file);
            Book book = reader.readEpub(in);
            //获取到书本的头部信息
            Metadata metadata = book.getMetadata();
            String newCoverId = metadata.getMetaAttribute("cover");
            System.out.println("FirstTitle为：" + metadata.getFirstTitle());
            //获取到书本的全部资源
            Resources resources = book.getResources();

            Map jk = resources.getResourceMap();
            Resource r = (Resource) jk.get("images/calibre_cover.jpg");
//            Resource r = (Resource) jk.get("images/calibre_cover.jpg");
            InputStream inn = r.getInputStream();

            FileUtil.copyInputStreamToFile(inn,new File("D:/book/convert"+fname+"_cover.jpg"));

//            System.out.println("所有资源数量为：" + resources.size());
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
//            System.out.println("内容资源数量为：" + contents.size());
            //获取到书本的spine资源 线性排序
            Spine spine = book.getSpine();
//            System.out.println("spine资源数量为：" + spine.size());
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
//            System.out.println("目录资源数量为：" + tableOfContents.size());
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
        return flag;

    }

    public static void listener() {
        try {
            Process pro = Runtime.getRuntime().exec("cmd /c ebook-convert D:/DownloadDocument/15.mobi D:/DownloadDocument/15.epub");

            pro.waitFor();
            BufferedInputStream br = new BufferedInputStream(pro.getInputStream());
            BufferedInputStream br2 = new BufferedInputStream(pro.getErrorStream());

            int ch;

            System.out.println("Input Stream:");

            while ((ch = br.read()) != -1) {
                System.out.print((char) ch);
            }

            System.out.println("Error Stream:");

            while ((ch = br2.read()) != -1) {
                System.out.print((char) ch);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

}
