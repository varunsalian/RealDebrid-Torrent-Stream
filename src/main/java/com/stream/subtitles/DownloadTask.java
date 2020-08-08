package com.stream.subtitles;

import com.stream.common.CommonConstants;
import com.stream.common.CommonUtils;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadTask implements Runnable {

    private String link;
    private String fileName;
    private String imdbID;
    private static Logger logger = Logger.getLogger(DownloadTask.class.getName());


    @Override
    public void run() {
        String url;
        try {
            url = getDownloadLinkFromYS(link);
            downloadSubtitle(url, fileName);
            String filePath = CommonConstants.SUBS_COMPRESSED_FOLDER + imdbID + CommonConstants.FORWARD_SLASH +fileName + CommonConstants.ZIP_EXTENSION;
            unzip(filePath, CommonConstants.SUBS_UNCOMPRESSED_FOLDER+imdbID);
            deleteFile(CommonConstants.SUBS_COMPRESSED_FOLDER + imdbID + CommonConstants.FORWARD_SLASH + fileName + CommonConstants.ZIP_EXTENSION);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if(file.exists() && !file.delete()) {
            throw new RuntimeException("Unable to delete subtitle file");
        }
    }

    private String getDownloadLinkFromYS(String url) throws IOException {
        Document document1 = YtsSubtitleUtils.getScrapDataFromUrl(url);
        return document1.select(CommonConstants.SUBS_DOWNLOAD_SELECTOR).first().absUrl(CommonConstants.SELECTOR_HREF);
    }

    private void downloadSubtitle(String url, String fileName) throws IOException {
        String filePath = CommonConstants.SUBS_COMPRESSED_FOLDER + imdbID + CommonConstants.FORWARD_SLASH +fileName + CommonConstants.ZIP_EXTENSION;
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(CommonUtils.getHttpUrlConnection(website, null).getInputStream());
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    private  void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry zipEntry = zis.getNextEntry();
            while(zipEntry != null){
                String filename = this.fileName + CommonConstants.SRT_EXTENSION;
                File newFile = new File(destDir + File.separator + filename);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
           logger.warning(e.getMessage());
        }

    }

    DownloadTask(String link, String fileName, String imdbID){
        this.link = link;
        this.fileName = fileName;
        this.imdbID = imdbID;
    }
}
