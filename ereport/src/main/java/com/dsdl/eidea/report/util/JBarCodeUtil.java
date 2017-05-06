package com.dsdl.eidea.report.util;

import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by 刘大磊 on 2016/11/14 17:36.
 */
public class JBarCodeUtil {
    private static String getBase64ImgStr(AbstractBarcodeBean bean128, String jbarcode) {
        String base64imgstr = "";
        //EAN128Bean bean128=new EAN128Bean();

        // 精细度
        final int dpi = 203;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(5f / dpi);

        // 配置对象
        bean128.setModuleWidth(moduleWidth);
//    	bean128.setFontSize(0);
//    	HumanReadablePlacement.HRP_NONE
        bean128.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        System.out.println("width:" + moduleWidth);
        bean128.setHeight(9.8800);
//        bean128.setWideFactor(3);
        bean128.doQuietZone(false);
//        bean128.set

        String format = "image/png";
        try {
            ByteArrayOutputStream ous = new ByteArrayOutputStream();
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean128.generateBarcode(canvas, jbarcode);
            ImageIO.write(canvas.getBufferedImage(), "png", ous);
            byte[] bytes = ous.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            base64imgstr = encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return base64imgstr;
        }
    }

    /**
     * 根据编码生成base64图片字符串
     *
     * @param jbarcode
     * @return
     * @author yuanfacheng
     * @date 2016年5月26日下午4:37:22
     * @version 1.0
     */
    @SuppressWarnings("finally")
    public static String getBase64ImgStr(String jbarcode) {

        if (StringUtils.isEmpty(jbarcode)) {
            return "";
        }
        Code128Bean bean128 = new Code128Bean();
        return getBase64ImgStr(bean128, jbarcode);

    }

    public static String getBase64ImgStrByEAN128(String jbarcode) {
        if (StringUtils.isEmpty(jbarcode)) {
            return "";
        }
        EAN128Bean bean128 = new EAN128Bean();
        return getBase64ImgStr(bean128, jbarcode);
    }
}
