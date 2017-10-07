package com.cus.metime.filehandler.config.messaging;

import com.cus.metime.filehandler.domain.MediaFile;
import com.cus.metime.filehandler.domain.builder.MediaFileBuilder;
import com.cus.metime.filehandler.domain.embedable.builder.CreationalDateBuilder;
import com.cus.metime.filehandler.domain.embedable.builder.FileSpecificationBuilder;
import com.cus.metime.filehandler.dto.messaging.EventWrapperDTO;
import com.cus.metime.filehandler.dto.messaging.FileTransferDTO;
import com.cus.metime.filehandler.dto.messaging.MessageEvent;
import com.cus.metime.filehandler.service.MediaFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jmimemagic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import sun.plugin2.message.EventMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Created by C-US on 9/29/2017.
 */

@Configuration
@MessageEndpoint
public class FileHandlerMessageProcessor {


    private static final int IMG_WIDTH = 100;
    private static final int IMG_HEIGHT = 100;


    private MediaFileService mediaFileService;

    @Autowired
    public FileHandlerMessageProcessor(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }

    public static void copyObject(Object src, Object dest)
        throws IllegalArgumentException, IllegalAccessException,
        NoSuchFieldException, SecurityException {
        for (Field field : src.getClass().getFields()) {
            dest.getClass().getField(field.getName()).set(dest, field.get(src));
        }
    }

    @ServiceActivator(inputChannel = "fileInput")
    public void fileInputProcessor(Message<byte[]> r) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String ,Object> eventWrapperDTOMap = objectMapper.convertValue(deserialize(r.getPayload()),Map.class);

        EventWrapperDTO eventWrapperDTO = new EventWrapperDTO();
        if(eventWrapperDTOMap.get("event").toString() == "DELETE"){
            eventWrapperDTO.setEvent(MessageEvent.DELETE);
        } else if(eventWrapperDTOMap.get("event").toString() == "UPDATE"){
            eventWrapperDTO.setEvent(MessageEvent.UPDATE);
        } else if(eventWrapperDTOMap.get("event").toString() == "CREATE"){
            eventWrapperDTO.setEvent(MessageEvent.CREATE);
        }

        eventWrapperDTO.setMessage(eventWrapperDTOMap.get("message"));


        Map<String,Object> map = objectMapper.convertValue(eventWrapperDTO.getMessage(),Map.class);


        MediaFile mediaFile = new MediaFileBuilder().createMediaFile();
        if(eventWrapperDTOMap.get("event").toString() == "DELETE"){
            System.out.println("DELETE FILE AND DATA");
        } else {
            FileTransferDTO fileTransferDTO = new FileTransferDTO();
            fileTransferDTO.setFileExtension((String) map.get("fileExtension"));
            fileTransferDTO.setFileName((String) map.get("fileName"));
            fileTransferDTO.setFileStream((byte[]) map.get("fileStream"));
            byte[] file = fileTransferDTO.getFileStream();
            String fileName =  fileTransferDTO.getFileName();
            String fileExtension = fileTransferDTO.getFileExtension();
            MagicMatch magicMatch = null;
            try {
                 magicMatch = Magic.getMagicMatch(file);
            } catch (MagicParseException e) {
                e.printStackTrace();
            } catch (MagicMatchNotFoundException e) {
                e.printStackTrace();
            } catch (MagicException e) {
                e.printStackTrace();
            }

            mediaFileService.save(new MediaFileBuilder()
                .setSegment((String)map.get("uuid"))
                .setUuid(UUID.randomUUID().toString())
                .setCreationalDate(new CreationalDateBuilder()
                    .setModifiedBy("System")
                    .setModifiedAt(LocalDateTime.now())
                    .setCreatedBy("System")
                    .setCreatedAt(LocalDateTime.now())
                    .createCreationalDate())
                .setFileSpecification(new FileSpecificationBuilder()
                    .setExtension((String)map.get("fileExtension"))
                    .setFileName((String) map.get("fileName"))
                    .setMimeType(magicMatch != null ? magicMatch.getMimeType() : "")
                    .setOriginalSize( Float.parseFloat(String.valueOf(file.length)))
                    .createFileSpecification())
                .createMediaFile());


            String name = "test";
            if(file.length > 0){
                byte[] bytes = file;


                new File("D:/opt2/"+ map.get("uuid")).mkdirs();
                BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File("D:/opt2/"+ map.get("uuid").toString() +"/" + fileName+"."+fileExtension)));
                stream.write(bytes);
                stream.close();

                BufferedImage originalImage = ImageIO.read(new File("D:/opt2/"+ map.get("uuid").toString() +"/" + fileName+"."+fileExtension));
                int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizedImage = resizeImage(originalImage,type);
                ImageIO.write(resizedImage, magicMatch != null ? magicMatch.getExtension() : "",new File("D:/opt2/"+ map.get("uuid").toString() +"/" + fileName+"_thumb"+"."+fileExtension));
                System.out.println("You successfully uploaded " + name + " into " + name + "-uploaded !");
            }
        }
        System.out.println(r);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

}
