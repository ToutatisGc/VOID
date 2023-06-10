package cn.toutatis.xvoid.toolkit.file.image;

/**
 * 压缩图片时,使用的质量压缩数值策略
 * @author Toutatis_Gc
 */
public enum PictureQualityDistributionStrategy {

    /**
     * 平均分布
     * 例如:压缩5次,起始压缩质量为0.8
     * 1-5压缩质量分别为 0.16,0.32,0.48,...0.8
     */
    AVERAGE,

    /**
     *极端分布
     * 倾向于低的压缩边界
     */
    EXTREME,

}
