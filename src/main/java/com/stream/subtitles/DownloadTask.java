package com.stream.subtitles;

import com.stream.common.CommonUtils;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadTask implements Runnable {

    private String link;
    private String fileName;
    private String imdbID;

    @Override
    public void run() {
        String url;
        try {
            url = getDownloadLinkFromYS(link);
            downloadSubtitle(url, fileName);
            String filePath = "subtitle/compressed/" + imdbID + "/" +fileName + ".zip";
            unzip(filePath, "subtitle/uncompressed/"+imdbID);
            deleteFile("subtitle/compressed/" + imdbID + "/" + fileName + ".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if(file.exists()){
           System.out.println(file.delete());
        }
    }

    public String getDownloadLinkFromYS(String url) throws IOException {
        Document document1 = YtsSubtitleUtils.getScrapDataFromUrl(url);
        return document1.select("a[href].btn-icon").first().absUrl("href");
    }

    public  void downloadSubtitle(String url, String fileName) throws IOException {
        String filePath = "subtitle/compressed/" + imdbID + "/" +fileName + ".zip";
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(CommonUtils.getHttpUrlConnection(website, null).getInputStream());
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

//        String filePath = "subtitle/compressed/"+fileName + ".zip";
//        try (InputStream fileInputStream = CommonUtils.getHttpUrlConnection(new URL(url), null).getInputStream();
//             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//             FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
//            int data;
//            while ((data=bufferedInputStream.read())!=-1){
//                outputStream.write(data);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private  void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = this.fileName + ".srt";
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DownloadTask(String link, String fileName, String imdbID){
        this.link = link;
        this.fileName = fileName;
        this.imdbID = imdbID;
    }
}
