package com.jweb.common.file.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.jweb.common.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

/**
 * 图片处理工具类
 * @author 邓超
 *
 * 2022/12/15 上午11:25:06
 */
@Slf4j
public class ImageFileUtil {
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * 去噪
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream reductionNoise (InputStream is) throws CommonException {
		Mat sourceMat = transfToMat(is);
		
		Mat gray = new Mat();
	    Imgproc.cvtColor(sourceMat, gray, Imgproc.COLOR_BGR2GRAY);
	    
	    Mat bin = new Mat();
	    Imgproc.adaptiveThreshold(gray, bin, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);
	    
//	    Mat hline = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(sourceMat.cols() / 16,1), new Point(-1, - 1));
//	    Mat yline = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, sourceMat.rows()/16), new Point(-1, -1));
	    Mat sline = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3), new Point(-1, -1));
	    
	    Mat temp = new Mat();
	    Imgproc.erode(bin, temp, sline);
	    
	    Mat dst = new Mat();
	    Imgproc.dilate(temp, dst, sline);
	   
		return transfToInputStream(dst);
	}
	/**
	 * 马赛克处理
	 * @param is 图片流InputStream
	 * @param degree 模糊程度，值越大越模糊
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream mosaic(InputStream is, int degree) throws CommonException {
		Mat sourceMat = transfToMat(is);
		
		int wsrc = sourceMat.width();
		int hsrc = sourceMat.height();
		
		double newWidth = wsrc/degree;
		double newHeight = hsrc/degree;
		
		Mat dst = new Mat();
		Imgproc.resize(sourceMat, dst, new Size(newWidth, newHeight), 10.0,50.0,Imgproc.INTER_NEAREST);
		Imgproc.resize(dst, dst, new Size(wsrc, hsrc), 10.0,50.0,Imgproc.INTER_NEAREST);
		return transfToInputStream(dst);
	}
	/**
	 * 局部区域马赛克处理
	 * @param is 图片流InputStream
	 * @param startX X轴起始位置
	 * @param startY Y轴起始位置
	 * @param endX X轴截止位置
	 * @param endY Y轴截止位置
	 * @param degree 模糊程度，值越大越模糊
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream mosaicPart(InputStream is, int startX, int startY, int endX, int endY, int degree) throws CommonException {
		Mat sourceMat = transfToMat(is);
		
		int width=sourceMat.width();//宽
	    int height=sourceMat.height();//高
	    Mat desImage = new Mat();
	    Imgproc.cvtColor(sourceMat, desImage,Imgproc.COLOR_BGRA2BGR,3);//图片类型转换，将ARGB转RGB
	    Mat cloneImage= desImage.clone();//克隆目标图

	    endX = endX <= startX || endX <= 0 || endX > width ? width : endX;
	    endY = endY <= startY || endY <= 0 || endY > height ? height : endY;
	    
	    endX -= degree;
	    endY -= degree;
	    
	    for (int i=startX; i<endY; i+=degree) {//i<总高，每次加level
	        for (int j=startY; j<endX; j+=degree) {//j小总宽，每次加level
	            //创建马赛克矩形区域
	            Rect mosaicRect= new Rect(j,i,degree,degree);
	            //填充数据
	            Mat roi= desImage.submat(mosaicRect);
	            //保证矩形区域颜色一致，方框内颜色值都为i，j点位的值
	            Scalar scaler = new Scalar(cloneImage.get(i, j)[0],cloneImage.get(i, j)[1], cloneImage.get(i, j)[2]);
	            //将处理好的矩形区域拷贝到目标图上去
	            Mat roiCopy = new Mat(mosaicRect.size(),CvType.CV_8UC3,scaler);
	            roiCopy.copyTo(roi);
	        }
	    }
		return transfToInputStream(desImage);
	}
	/**
	 * 压缩
	 * @param is 图片流InputStream
	 * @param quality 质量值：0-100，值越大质量越好
	 * @return 
	 * @throws CommonException 
	 */
	public static InputStream compress(InputStream is, int quality) throws CommonException {
		Mat sourceMat = transfToMat(is);
		MatOfInt dstImage = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, quality);
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(".jpg", sourceMat, mob, dstImage);
		return new ByteArrayInputStream(mob.toArray());
	}
	
	/**
	 * 旋转
	 * @param is 图片流InputStream
	 * @param angle 旋转角度（度），正值表示逆时针旋转
	 * @param scale 缩放比例
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream rotate(InputStream is, double angle, double scale) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Point center = new Point(sourceMat.width()/2, sourceMat.height()/2);
		Mat affineTrans = Imgproc.getRotationMatrix2D(center, angle, scale);

		double thera = angle * Math.PI / 180;
		double a = Math.sin(thera);
		double b = Math.cos(thera);
		  
		int wsrc = sourceMat.width();
		int hsrc = sourceMat.height();
		  
		int wdst = (int) (hsrc * Math.abs(a) + wsrc * Math.abs(b));
		int hdst = (int) (wsrc * Math.abs(a) + hsrc * Math.abs(b));
		Mat rotate = new Mat(hdst, wdst, sourceMat.type());
		
		// 改变变换矩阵第三列的值
	    affineTrans.put(0, 2, affineTrans.get(0, 2)[0] + (wdst - wsrc) / 2);
	    affineTrans.put(1, 2, affineTrans.get(1, 2)[0] + (hdst - hsrc) / 2);
		
	    Imgproc.warpAffine(sourceMat, rotate, affineTrans, rotate.size(), Imgproc.WARP_FILL_OUTLIERS);

	    return transfToInputStream(rotate);
	}
	
	/**
	 * 等比缩放
	 * @param is 图片流InputStream
	 * @param scale 缩放比例
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream resize(InputStream is, double scale) throws CommonException {
		Mat sourceMat = transfToMat(is);
		double width = sourceMat.width()*scale;
		double height = sourceMat.height()*scale;
		
		Mat resize = new Mat();
		Imgproc.resize(sourceMat, resize, new Size(width, height));
	    return transfToInputStream(resize);
	}
	/**
	 * 指定宽高缩放
	 * @param is 图片流InputStream
	 * @param width 宽度
	 * @param height 高度
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream resize(InputStream is, int width, int height) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Mat resize = new Mat();
		Imgproc.resize(sourceMat, resize, new Size(width, height));
	    return transfToInputStream(resize);
	}
	/**
	 * 灰度化
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream gray(InputStream is) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Mat gray = new Mat();
	    Imgproc.cvtColor(sourceMat, gray, Imgproc.COLOR_BGR2GRAY);
	    return transfToInputStream(gray);
	}
	/**
	 * 二值化
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream binary(InputStream is) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Mat gray = new Mat();
	    Imgproc.cvtColor(sourceMat, gray, Imgproc.COLOR_BGR2GRAY);
	    
		Mat dst = new Mat();
		Imgproc.adaptiveThreshold(gray, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 8);
		
	    return transfToInputStream(dst);
	}
	
	/**
	 * 反色处理
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream inverse(InputStream is) throws CommonException {
		Mat sourceMat = transfToMat(is);

		int width = sourceMat.cols();
		int height = sourceMat.rows();
		int dims = sourceMat.channels();

		byte[] data = new byte[width*height*dims];
		sourceMat.get(0, 0, data);
		
		int index = 0;
		int r=0, g=0, b=0;
		for(int row=0; row<height;row++) {
			for(int col=0; col<width;col++) {
				index = row*width*dims + col;
				b = data[index]&0xff;
				g = data[index+1]&0xff;
				r = data[index+2]&0xff;

				r = 255 - r;
				g = 255 - g;
				b = 255 - b;
		
				data[index] = (byte)b;
				data[index+1] = (byte)g;
				data[index+2] = (byte)r;
			}

		}

		sourceMat.put(0, 0, data);

	    return transfToInputStream(sourceMat);
	}
	
	/**
	 * 调节亮度
	 * @param is 图片流InputStream
	 * @param bright 亮度值，值越大越亮
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream brightness(InputStream is, double bright) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Mat dst = new Mat();
		Mat black = Mat.zeros(sourceMat.size(), sourceMat.type());
		Core.addWeighted(sourceMat, bright, black, 0.5, 0, dst);
	    return transfToInputStream(dst);
	}
	
	/**
	 * 锐化
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream sharpen(InputStream is) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Mat dst = new Mat();
		float[] sharper = new float[]{0, -1, 0, -1, 5, -1, 0, -1, 0};
		Mat operator = new Mat(3, 3, CvType.CV_32FC1);
		operator.put(0, 0, sharper);
		Imgproc.filter2D(sourceMat, dst, -1, operator);
	    return transfToInputStream(dst);
	}
	/**
	 * 高斯模糊
	 * @param is 图片流InputStream
	 * @param sigmaX 值越大越模糊
	 * @param sigmaY 值越大越模糊
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream blur(InputStream is, double sigmaX, double sigmaY) throws CommonException {
		Mat sourceMat = transfToMat(is);
		Mat dst = new Mat();
		Imgproc.GaussianBlur(sourceMat, dst, new Size(15, 15), sigmaX, sigmaY);
		return transfToInputStream(dst);
	}
	/**
	 * 梯度
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException 
	 */
	public static InputStream gradient(InputStream is) throws CommonException {
		
		Mat sourceMat = transfToMat(is);

		Mat grad_x = new Mat();
		Mat grad_y = new Mat();
		Mat abs_grad_x = new Mat();
		Mat abs_grad_y = new Mat();

		Imgproc.Sobel(sourceMat, grad_x, CvType.CV_32F, 1, 0);
		Imgproc.Sobel(sourceMat, grad_y, CvType.CV_32F, 0, 1);

		Core.convertScaleAbs(grad_x, abs_grad_x);
		Core.convertScaleAbs(grad_y, abs_grad_y);

		grad_x.release();
		grad_y.release();

		Mat gradxy = new Mat();
		Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 10, gradxy);

		return transfToInputStream(gradxy);
	}

	/**
	 * 倾斜矫正
	 * @param is 图片流InputStream
	 * @throws CommonException 
	 */
	public static InputStream rectify(InputStream is) throws CommonException {
		Mat sourceMat = transfToMat(is);
		 // 二值化
        Mat binary = imgBinarization(sourceMat);
        // 膨胀与腐蚀
        Mat preprocess = preprocess(binary);
        // 查找和筛选文字区域
        List<RotatedRect> rects = findTextRegion(preprocess) ;
        //将获取到的矩形根据面积倒序  或 将被包含和重复的去掉
        Mat correction = correction(rects, sourceMat);
		return transfToInputStream(correction);
	}
	/**
	 * 图片识别
	 * @param is 图片流InputStream
	 * @param type 类别
	 * @throws CommonException 
	 */
	public static void recognition(InputStream is, ImageFileUtil.Recognition type) throws CommonException {
	    CascadeClassifier detector = null;
		try {
			String templatePath = ImageFileUtil.class.getClassLoader().getResource(type.getTemplate()).getPath();
			if(templatePath.startsWith("/")) {
				templatePath = templatePath.substring(1, templatePath.length());
			}

			detector = new CascadeClassifier(templatePath);
		} catch (Exception e) {
			log.error("图片识别加载模板文件出错", e);
			CommonException.readFileError();
		}
	    Mat image = transfToMat(is);
	    // Detect faces in the image.
	    // MatOfRect is a special container class for Rect.
	    MatOfRect faceDetections = new MatOfRect();
	    detector.detectMultiScale(image, faceDetections);
	    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	    // Draw a bounding box around each face.
	    for (Rect rect : faceDetections.toArray()) {
	        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	    }
	    // Save the visualized detection.
	    String filename = "C:\\Users\\Administrator\\Desktop\\img\\faceDetection.png";

	    Imgcodecs.imwrite(filename, image);
	}

    private static Mat imgBinarization(Mat srcImage){
        Mat gray_image = null;
        try {
            gray_image = new Mat(srcImage.height(), srcImage.width(), CvType.CV_8UC1);
            Imgproc.cvtColor(srcImage,gray_image,Imgproc.COLOR_RGB2GRAY);
        } catch (Exception e) {
            gray_image = srcImage.clone();
            gray_image.convertTo(gray_image, CvType.CV_8UC1);
            System.out.println("原文异常，已处理...");
        }
        Mat thresh_image = new Mat(srcImage.height(), srcImage.width(), CvType.CV_8UC1);
        Imgproc.threshold(gray_image, thresh_image,100, 255, Imgproc.THRESH_BINARY);
        return thresh_image;
    }
    /**
     * 根据二值化图片进行膨胀与腐蚀
     * @author MaChao
     * @time 2019-9-29
     */
    private static Mat preprocess(Mat binary){
        Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 4));
        Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
        Mat dilate1 = new Mat();
        Imgproc.dilate(binary, dilate1, element2);
       
        Mat erode1 = new Mat();
        Imgproc.erode(dilate1, erode1, element1);
        Mat dilate2 = new Mat();
        Imgproc.dilate(erode1, dilate2, element2);
        return dilate2;
    }
    /**
     * 文字区域
     * @author MaChao
     * @time 2019-12-3
     */
    private static List<RotatedRect> findTextRegion(Mat img)
    {
        List<RotatedRect> rects = new ArrayList<RotatedRect>();
        //1.查找轮廓
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        int img_width = img.width();
        int img_height = img.height();
        int size = contours.size();
        //2.筛选那些面积小的
        for (int i = 0; i < size; i++){
            double area = Imgproc.contourArea(contours.get(i));
            if (area < 1000)
                continue;
            //轮廓近似，作用较小，approxPolyDP函数有待研究
            double epsilon = 0.001*Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true);
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), approxCurve, epsilon, true);
     
            //找到最小矩形，该矩形可能有方向
            RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));
            //计算高和宽
            int m_width = rect.boundingRect().width;
            int m_height = rect.boundingRect().height;
     
            //筛选那些太细的矩形，留下扁的
            if (m_width < m_height)
                continue;
            if(img_width == rect.boundingRect().br().x)
                continue;
            if(img_height == rect.boundingRect().br().y)
                continue;
            //符合条件的rect添加到rects集合中
            rects.add(rect);
        }
        return rects;
    }
    /**
     * 倾斜矫正
     * @param rects
     */
    private static Mat correction(List<RotatedRect> rects,Mat srcImage) {
        double degree = 0;
        double degreeCount = 0;
        for(int i = 0; i < rects.size();i++){
            if(rects.get(i).angle >= -90 && rects.get(i).angle < -45){
                degree = rects.get(i).angle;
                if(rects.get(i).angle != 0){
                    degree += 90;
                }
            }
            if(rects.get(i).angle > -45 && rects.get(i).angle <= 0){
                degree = rects.get(i).angle;
            }
            if(rects.get(i).angle <= 90 && rects.get(i).angle > 45){
                degree = rects.get(i).angle;
                if(rects.get(i).angle != 0){
                    degree -= 90;
                }
            }
            if(rects.get(i).angle < 45 && rects.get(i).angle >= 0){
                degree = rects.get(i).angle;
            }
            if(degree > -5 && degree < 5){
                degreeCount += degree;
            }
           
        }
        if(degreeCount != 0){
            // 获取平均水平度数
            degree = degreeCount/rects.size();
        }
        Point center = new Point(srcImage.cols() / 2, srcImage.rows() / 2);
        Mat rotm = Imgproc.getRotationMatrix2D(center, degree, 1.0);    //获取仿射变换矩阵
        Mat dst = new Mat();
        Imgproc.warpAffine(srcImage, dst, rotm, srcImage.size(), Imgproc.INTER_LINEAR, 0, new Scalar(255, 255, 255));    // 进行图像旋转操作
        return dst;
    }
    /**
	 * Convert InputStream to Mat
	 * @param is 图片流InputStream
	 * @return
	 * @throws CommonException
	 */
	private static Mat transfToMat(InputStream is) throws CommonException {
		try {
			BufferedImage bi = ImageIO.read(is);
			
			BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = newBi.createGraphics();
			try {
				g.setComposite(AlphaComposite.Src);
				g.drawImage(bi, 0, 0, null);
			}finally {
				g.dispose();
			}
	
			Mat mat = new Mat(newBi.getHeight(), newBi.getWidth(), CvType.CV_8UC3);
			DataBuffer db = newBi.getRaster().getDataBuffer();
			byte[] b = ((DataBufferByte)db).getData();
			mat.put(0,  0, b);
			return mat;
		} catch (IOException e) {
			CommonException.readFileError();
		}
		return null;
	}
	
	/**
	 * Convert Mat to InputStream
	 * @param mat
	 * @return
	 */
	private static InputStream transfToInputStream(Mat mat) {
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(".png", mat, mob);
		return new ByteArrayInputStream(mob.toArray());
	}
	
	public static enum Recognition{
		
		/**
		 * 人脸
		 */
		FACE("opencv/classifier/lbpcascades/lbpcascade_frontalface.xml"),
		/**
		 * 眼睛
		 */
		EYE("opencv/classifier/haarcascades/haarcascade_eye_tree_eyeglasses.xml");
		
		private String template;
		Recognition(String template){
			this.template = template;
		}
		public String getTemplate() {
			return template;
		}
		
	}
}
