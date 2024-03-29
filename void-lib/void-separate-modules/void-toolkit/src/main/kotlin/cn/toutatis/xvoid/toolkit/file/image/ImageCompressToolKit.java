package cn.toutatis.xvoid.toolkit.file.image;

import cn.toutatis.xvoid.toolkit.Meta;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
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
        // 读取图片
        BufferedImage image = ImageIO.read(origin);
        String fileSuffix = FileToolkit.getFileSuffix(origin);
        //png图片采用哈夫曼编码和LZ777编码,所以基本无法在尺寸不变的情况下压缩文件大小,尽力了尽力了
        if ("png".equalsIgnoreCase(fileSuffix)) {
            Iterator<ImageWriter> imageWriterIterator = ImageIO.getImageWritersByFormatName("png");
            ImageWriter writer = imageWriterIterator.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType("Deflate");
            writeParam.setCompressionQuality(0.1f);
            FileOutputStream fos = new FileOutputStream(dist);
            ImageOutputStream output = ImageIO.createImageOutputStream(fos);
            writer.setOutput(output);
            writer.write(null, new IIOImage(image, null, null), writeParam);
            output.close();
            fos.close();
        }else {
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
            Graphics2D graphics2D = bufferedImage.createGraphics();
            //清除背景并绘制图像
            graphics2D.setColor(new Color(0,0,0, 0));
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
            Iterator<ImageWriter> iter = ImageIO.getImageWriters(type,"jpg");
            if (iter.hasNext()) { writer = iter.next(); }
            if (writer == null) {
                logger.error(String.format("[%s-IMAGE]未找到合适的输入器[%s]",Meta.MODULE_NAME, fileSuffix));
                return;
            }
            IIOImage iioImage = new IIOImage(bufferedImage, null, null);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(dist);
            writer.setOutput(outputStream);
            writer.write(null, iioImage, param);
            outputStream.close();
        }
    }

    public static void thumbnail(File origin, File dist, double scale) throws IOException {
        Thumbnails.of(origin).scale(scale).toFile(dist);
    }

    public static List<String> differentStandardThumbnail(List<File> fileList, CompressConfig.CompressContent config) throws IOException {
        List<File> distinctFileList =
                fileList.stream()
                        .distinct()
                        .filter(file -> file.exists() && file.isFile() && FileToolkit.isImg(FileToolkit.getFileSuffix(file.getName())))
                        .collect(Collectors.toList());
        ArrayList<String> filenameList = new ArrayList<>(distinctFileList.size());
        if (distinctFileList.size() > 0){
            logger.info(String.format("[%s]待生成 ",Meta.MODULE_NAME)+distinctFileList.size()+ " 个图像资源.");
            Integer zipTimes = config.getZipTimes();
            String lastSaveDir = config.getLastSaveDir();
            if (Validator.strNotBlank(lastSaveDir)){
                File file = new File(lastSaveDir);
                if (!file.exists()){
                    file.mkdir();
                }
                if (file.exists() && file.isDirectory()){
                    logger.info(String.format("[%s]目标地址校验成功.[√]",Meta.MODULE_NAME));
                }else {
                    String error = String.format("[%s]保存目录错误.[目标地址不是目录×]",Meta.MODULE_NAME);
                    logger.error(error);
                    throw new FileNotFoundException(error);
                }
            }else {
                logger.info(String.format("[%s]保存目录错误.[空文件夹×]",Meta.MODULE_NAME));
                throw new FileNotFoundException("");
            }
            AtomicInteger count = new AtomicInteger();
            distinctFileList.forEach(item ->{
                String suffix = FileToolkit.getFileSuffix(item);
                String nameType = config.getSaveFileRenameType();
                String fileName = "UNKNOWN-"+item.hashCode();
                int point = item.getName().lastIndexOf(".");
                if ("UUID".equals(nameType)){
                    fileName = UUID.randomUUID().toString().replace("-","");
                }else if ("SOURCE".equals(nameType)){
                    fileName = item.getName().substring(0,point);
                }
                if (config.getDifferentRegular()){
                    ArrayList<Double> qualityList = new ArrayList<>(zipTimes);
                    if (config.getPictureQualityDistributionStrategy() == PictureQualityDistributionStrategy.EXTREME){
                        // 质量因子,数值越高压缩质量越接近原图,建议区间[0.5-0.8]
                        double qualityFactor= 0.75;
                        // 质量顶点,数值最高值
                        double qualityPeak = 8;
                        // 修正值,数值在前两者的乘积上提高
                        double base = 0;
                        for (double x = zipTimes; x > 0; x -= 1) {
                            double currentZipQuality = (qualityFactor * Math.pow(x - qualityPeak, 2) + base)/100;
                            qualityList.add(currentZipQuality);
                        }
                    }else {
                        double avg = 0.8D / zipTimes;
                        for (int i = 1; i <= zipTimes; i++) {
                            double currentZipQuality = i * avg;
                            qualityList.add(currentZipQuality);
                        }
                    }
                    boolean isPng = "png".equalsIgnoreCase(suffix);
                    for (int i = 1; i <= qualityList.size(); i++) {
                        Double currentQuality = qualityList.get(i - 1);
//                        组合文件目录
                        switch (config.getZipType()){
                            case "1":
                                File quality = concatFileName(lastSaveDir, fileName, "Q", i, suffix);
                                logger.info("正在压缩"+item.getName()+"[Q"+i+"]");
                                try {
                                    if (isPng){
                                        ImageCompressToolKit.linearCompression(item,quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
//                                        logger.info("[%s]PNG图片,仅消除部分图片信息.已停止压缩文件[%s]".formatted(VoidModuleInfo.MODULE_NAME,item.getName()));
                                        break;
                                    }else {
                                        ImageCompressToolKit.linearCompression(item,quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                    }
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
                                    if (isPng){
                                        if (i == 1){
                                            ImageCompressToolKit.linearCompression(item,all_quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                        }else {
                                            continue;
                                        }
                                    }else {
                                        ImageCompressToolKit.linearCompression(item,all_quality,0,0,Float.parseFloat(String.valueOf(currentQuality)));
                                    }
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
        return filenameList;
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
