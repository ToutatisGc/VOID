package cn.toutatis.xvoid.toolkit.file.image;

import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Toutatis_Gc
 * 图片压缩工具
 */
public class ImageCompressToolKit {

    private static final Logger logger = LoggerToolkit.getLogger(ImageCompressToolKit.class);

    /**
     * @param origin 源文件
     * @param dist 输出文件
     * @param maxWidth 最大宽度
     * @param maxHeight 最大高度
     * @param quality 输出质量
     * @throws IOException 图片错误
     */
    public static void linearCompression(File origin, File dist, int maxWidth, int maxHeight, float quality) throws IOException {

        ImageIcon imageIcon = new ImageIcon(origin.getCanonicalPath());
        Image image = imageIcon.getImage();
        Image resizedImage = null;

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int newWidth = Math.max(imageWidth, maxWidth);
        if (imageWidth >= imageHeight) {
            resizedImage = image.getScaledInstance(newWidth, (newWidth * imageHeight) / imageWidth, Image.SCALE_SMOOTH);
        }
        int newHeight = Math.max(imageHeight, maxHeight);
        if (resizedImage == null && imageHeight >= imageWidth) {
            resizedImage = image.getScaledInstance((newHeight * imageWidth) / imageHeight, newHeight, Image.SCALE_SMOOTH);
        }
        Image temp = new ImageIcon(resizedImage).getImage();
        //创建缓冲图像
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //将图像复制到缓冲图像
        Graphics graphics2D = bufferedImage.createGraphics();
        //清除背景并绘制图像
        graphics2D.setColor(Color.BLACK);

        graphics2D.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        graphics2D.drawImage(temp, 0, 0, null);
        graphics2D.dispose();

        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
//        模糊区块
//        描述指定的像素及其周围像素如何影响过滤操作的输出图像中的像素的位置的值的矩阵
        Kernel kernel = new Kernel(3, 3, softenArray);
//        构造给指定Kernel一个的ConvolveOp，边缘条件和RenderingHint对象（可以是0）
//        EDGE_NO_OP将位于源图像边缘的像素复制为目标中相应的像素，不加修改
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = convolveOp.filter(bufferedImage, null);
        ImageIO.write(bufferedImage, "", new File(""));
        ImageWriter writer = null;
        ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(bufferedImage);
        Iterator<ImageWriter> iter = ImageIO.getImageWriters(type, "jpg");
        if (iter.hasNext()) { writer = iter.next(); }
        if (writer == null) { return; }
        IIOImage iioImage = new IIOImage(bufferedImage, null, null);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(dist);
        writer.setOutput(outputStream);
        writer.write(null, iioImage, param);
    }

    public static void thumbnail(File origin, File dist, double scale) throws IOException {
        Thumbnails.of(origin).scale(scale).toFile(dist);
    }

    public static void differentStandardThumbnail(List<File> fileList, CompressConfig.CompressContent config) {
        List<File> distinctFileList =
                fileList.stream()
                        .distinct()
                        .filter(file -> file.exists() && file.isFile() && FileToolkit.isImg(FileToolkit.getFileSuffix(file.getName())))
                        .collect(Collectors.toList());
        if (distinctFileList.size() > 0){
            logger.info("待生成 "+distinctFileList.size()+ " 个图像资源.");
            Integer zipTimes = config.getZipTimes();
            String lastSaveDir = config.getLastSaveDir();
            if (!"".equals(lastSaveDir)){
                File file = new File(lastSaveDir);
                if (!file.exists()){
                    file.mkdir();
                }
                if (file.exists() && file.isDirectory()){
                    logger.info("目标地址校验成功.[√]");
                }else {
                    logger.info("保存目录错误.[目标地址不是目录×]");
                    return;
                }
            }else {
                logger.info("保存目录错误.[空文件夹×]");
                return;
            }
            AtomicInteger count = new AtomicInteger();
            distinctFileList.forEach(item ->{
                String suffix = item.getName().substring(item.getName().lastIndexOf(".") + 1);
                String nameType = config.getSaveFileRenameType();
                String fileName = "UNKNOWN-"+item.hashCode();
                int point = item.getName().lastIndexOf(".");
                if ("UUID".equals(nameType)){
                    fileName = UUID.randomUUID().toString().replace("-","");
                }else if ("SOURCE".equals(nameType)){
                    fileName = item.getName().substring(0,point);
                }
                if (config.getDifferentRegular()){
                    double avg = 0.8D / zipTimes;
                    for (Integer i = 1; i <= zipTimes; i++) {
                        double currentQuality = i * avg;
//                        组合文件目录
                        switch (config.getZipType()){
                            case "1":
                                File quality = concatFileName(lastSaveDir, fileName, "Q", i, suffix);
                                logger.info("正在压缩"+item.getName()+"[Q"+i+"]");
                                try {
                                    ImageCompressToolKit.linearCompression(item,quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "2":
                                File ratio = concatFileName(lastSaveDir, fileName, "R", i, suffix);
                                logger.info("正在压缩"+item.getName()+"[R"+i+"]");
                                try {
                                    ImageCompressToolKit.thumbnail(item,ratio,currentQuality);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "0":
                            default:
                                try {
                                    File all_ratio = concatFileName(lastSaveDir, fileName, "R", i, suffix);
                                    ImageCompressToolKit.thumbnail(item,all_ratio,currentQuality);
                                    logger.info("正在压缩"+item.getName()+"[R"+i+"]");
                                    File all_quality = concatFileName(lastSaveDir, fileName, "Q", i, suffix);
                                    ImageCompressToolKit.linearCompression(item,all_quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                    logger.info("正在压缩"+item.getName()+"[Q"+i+"]");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        count.getAndIncrement();
                    }
                }else {
                    try {
                        thumbnail(item,concatFileName(lastSaveDir,fileName,"0",0,suffix),0.5);
                        linearCompression(item,concatFileName(lastSaveDir,fileName,"0",0,suffix),0,0,0.5F);
                        logger.info("正在压缩"+item.getName()+"[0]");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!config.getRetainSourceFile()){
                    item.delete();
                }
            });
            logger.info("压缩已完成. ["+count.get()+"]");
        }else {
            logger.info("可执行图片数量为0.[×]");
        }
    }

    private static File concatFileName(String dir,String fileName,String middle,int currentTimes,String suffix){
        StringBuilder builder = new StringBuilder(dir);
        builder.append("\\");
        builder.append(fileName);
        builder.append("_").append(middle).append("_");
        builder.append(currentTimes);
        builder.append(".");
        builder.append(suffix);
        return new File(builder.toString());
    }
}
