package com.cus.metime.filehandler.web.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by C-US on 10/3/2017.
 */
@RestController
@RequestMapping("/assets")
public class AssetsResource {

    @RequestMapping(value = "/image/{uuid}/{fileName}",method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] images(@PathVariable("uuid") String uuid, @PathVariable("fileName") String fileName) {



        try {
            BufferedImage bufferedImage = ImageIO.read(new File("D:/opt2/"+uuid+"/"+fileName));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png",byteArrayOutputStream);

            byte[] imageIntByte = byteArrayOutputStream.toByteArray();

            return imageIntByte;
        } catch (IOException e) {
            return null;
        }
    }

}
