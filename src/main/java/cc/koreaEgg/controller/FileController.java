package cc.koreaEgg.controller;

import cc.koreaEgg.entity.UploadFile;
import cc.koreaEgg.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
@Slf4j
public class FileController {

    @Autowired
    private AppService appService;

    @ResponseBody
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public List<String> uploadImg(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

        List<String> retVal = new ArrayList<String>();
        String uploadFileDir = "/srv/data/temp/";

                /*tempfile에 임시저장*/
        Iterator<String> itr = request.getFileNames();
        if (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());

            /* 유니크 파일 패스 생성*/
            String originalFileName = mpf.getOriginalFilename();
            String originalFileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length());
            String uniqueFileName = UUID.randomUUID().toString();
            String storagePathNameExt = uniqueFileName + "." + originalFileExt;

            /* 파일 저장 */

            File dir = new File(uploadFileDir);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }


            File lOutFile = new File (uploadFileDir + storagePathNameExt);
            FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
            lFileOutputStream.write(mpf.getBytes());
            lFileOutputStream.close();

            /* 리스트로 리턴 */
            retVal.add(storagePathNameExt);

            // DB Insert
            /*UploadFile uploadFile = new UploadFile();
            uploadFile.setPath(storagePathNameExt);
            uploadFile.setName(originalFileName);
            uploadFile.setFileExtension(originalFileExt.toUpperCase());*/
            //appService.createUploadFile(uploadFile);

        }
        return retVal;
    }

    /*@RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> postImage(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        List<UploadFile> uploadedFiles = new ArrayList<UploadFile>();

        String uploadFileDir = "/srv/repository/";
        log.debug(uploadFileDir);
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        while (itr.hasNext()) {

            mpf = request.getFile(itr.next());

            String uniqueFileName = this.upperCase(UUID.randomUUID().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            String year = simpleDateFormat.format(date).substring(0, 4);
            String month = simpleDateFormat.format(date).substring(4, 6);

            String originalFileName = mpf.getOriginalFilename();
            String originalFileExt = this.upperCase(originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length()));
            String storagePathNameExt = year + "/" + month + "/" + uniqueFileName + "." + this.upperCase(originalFileExt);

            if (originalFileExt.equalsIgnoreCase("JPG") || originalFileExt.equalsIgnoreCase("JPEG") || originalFileExt.equalsIgnoreCase("GIF")
                    || originalFileExt.equalsIgnoreCase("PNG")) {
                try {

                    BufferedImage originalImage = ImageIO.read(mpf.getInputStream());
                    if(originalImage.getWidth() > 6144 || originalImage.getHeight() > 6144 ) {
                        map.put("result", "error");
//                        map.put("description", "File Resolution [" + originalImage.getWidth() + ":" + originalImage.getHeight()+ "] Not Allowed");
                        map.put("description", "이미지의 사이즈가 너무 큽니다. 6000x6000이하의 이미지만 등록이 가능합니다.");

                        return map;
                    }

                    // Origin File Save
                    byte[] bytes = mpf.getBytes();

                    File dir = new File(uploadFileDir + year + "/" + month + "/");
                    if (!dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    File lOutFile = new File(uploadFileDir + storagePathNameExt);
                    FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
                    lFileOutputStream.write(bytes);
                    lFileOutputStream.close();

                    // Make Thumbnail
                    String destinationFileName = storagePathNameExt.substring(0, storagePathNameExt.lastIndexOf(".")) + "_THUMB"
                            + storagePathNameExt.substring(storagePathNameExt.lastIndexOf(".", storagePathNameExt.length()));

                    double scale = 0;
                    if(originalImage.getWidth()>200) {
                        scale = determineImageScale(originalImage.getWidth(), originalImage.getHeight(), 200, originalImage.getHeight());
                    } else {
                        scale = determineImageScale(originalImage.getWidth(), originalImage.getHeight(), originalImage.getWidth(), originalImage.getHeight());
                    }
                    int scaleWidth = (int) (scale * originalImage.getWidth());
                    int scaleHeight = (int) (scale * originalImage.getHeight());

                    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                    BufferedImage resizeImageJpg = resizeImageWithHint(originalImage, type, scaleWidth, scaleHeight);
                    ImageIO.write(resizeImageJpg, originalFileExt, new File(uploadFileDir + destinationFileName));

                    // DB Insert
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setPath(storagePathNameExt);
                    uploadFile.setName(originalFileName);
                    uploadFile.setFileExtension(originalFileExt.toUpperCase());
                    appService.createUploadFile(uploadFile);

                    uploadedFiles.add(uploadFile);

                } catch (IOException ie) {
                    ie.printStackTrace();
                    throw ie;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }

            } else {
                throw new Exception("[" + originalFileExt + "]File Type Not Allowed");
            }
        }

        map.put("result", "success");
        map.put("description", "Image Upload Success.");
        map.put("uploadedFiles", uploadedFiles);

        return map;
    }

    private double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {

        double scalex = (double) targetWidth / sourceWidth;
        double scaley = (double) targetHeight / sourceHeight;
        return Math.min(scalex, scaley);
    }

    private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int width, int height) {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    */

}
