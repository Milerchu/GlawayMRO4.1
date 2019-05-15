package com.glaway.sddq.tools;

/**
 * 
 * 移动端图片工具类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class ImageHelper {
	/**
	 * 
	 * @Title: checkJpegImage
	 * @Description: 检测图片是否异常
	 * @param imageData
	 *            图片流
	 * @param imageLen
	 *            图片长度
	 * @return 是否
	 */
	public static boolean check(byte[] imageData) {
		int imageLen = imageData.length;
		if (imageData == null || 0 >= imageLen) {
			return false;
		}
		if (!(-1 == imageData[0] && -40 == imageData[1])) {
			return false;
		}

		if (!(-1 == imageData[imageLen - 2] && -39 == imageData[imageLen - 1])) {
			return false;
		}
		return true;
	}

	public static boolean checkJpegImage(byte[] imageData, int imageLen) {
		if (imageData == null || 0 >= imageLen) {
			return false;
		}
		if (!(-1 == imageData[0] && -40 == imageData[1])) {
			return false;
		}

		if (!(-1 == imageData[imageLen - 2] && -39 == imageData[imageLen - 1])) {
			return false;
		}
		return true;
	}

	// private static boolean IsCompletedImage(byte[] b)
	// {
	//
	// //jpg png图是根据最前面和最后面特殊字节确定. bmp根据文件长度确定
	// //png检查
	// if (szBuffer[0] == 137 && szBuffer[1] == 80 && szBuffer[2] == 78 &&
	// szBuffer[3] == 71 && szBuffer[4] == 13
	// && szBuffer[5] == 10 && szBuffer[6] == 26 && szBuffer[7] == 10)
	// {
	// //&& szBuffer[szBuffer.Length - 8] == 73 && szBuffer[szBuffer.Length - 7]
	// == 69 && szBuffer[szBuffer.Length - 6] == 78
	// if (szBuffer[szBuffer.Length - 5] == 68 && szBuffer[szBuffer.Length - 4]
	// == 174 && szBuffer[szBuffer.Length - 3] == 66
	// && szBuffer[szBuffer.Length - 2] == 96 && szBuffer[szBuffer.Length - 1]
	// == 130)
	// return true;
	// //有些情况最后多了些没用的字节
	// for (int i = szBuffer.Length - 1; i > szBuffer.Length / 2; --i)
	// {
	// if (szBuffer[i - 5] == 68 && szBuffer[i - 4] == 174 && szBuffer[i - 3] ==
	// 66
	// && szBuffer[i - 2] == 96 && szBuffer[i - 1] == 130)
	// return true;
	// }
	//
	//
	// }
	// else if (szBuffer[0] == 66 && szBuffer[1] == 77)//bmp
	// {
	// //bmp长度
	// //整数转成字符串拼接
	// string str = Convert.ToString(szBuffer[5], 16) +
	// Convert.ToString(szBuffer[4], 16)
	// + Convert.ToString(szBuffer[3], 16) + Convert.ToString(szBuffer[2], 16);
	// int iLength = Convert.ToInt32("0x" + str, 16); //16进制数转成整数
	// if (iLength <= szBuffer.Length) //有些图比实际要长
	// return true;
	// }
	// else if (szBuffer[0] == 71 && szBuffer[1] == 73 && szBuffer[2] == 70 &&
	// szBuffer[3] == 56)//gif
	// {
	// //标准gif 检查00 3B
	// if (szBuffer[szBuffer.Length - 2] == 0 && szBuffer[szBuffer.Length - 1]
	// == 59)
	// return true;
	// //检查含00 3B
	// for (int i = szBuffer.Length - 1; i > szBuffer.Length / 2; --i)
	// {
	// if (szBuffer[i] != 0)
	// {
	// if (szBuffer[i] == 59 && szBuffer[i - 1] == 0)
	// return true;
	// }
	// }
	// }
	// else if (szBuffer[0] == 255 && szBuffer[1] == 216) //jpg
	// {
	// //标准jpeg最后出现ff d9
	// if (szBuffer[szBuffer.Length - 2] == 255 && szBuffer[szBuffer.Length - 1]
	// == 217)
	// return true;
	// else
	// {
	// //有好多jpg最后被人为补了些字符也能打得开, 算作完整jpg, ffd9出现在近末端
	// //jpeg开始几个是特殊字节, 所以最后大于10就行了 从最后字符遍历
	// //有些文件会出现两个ffd9 后半部分ffd9才行
	// for (int i = szBuffer.Length - 2; i > szBuffer.Length / 2; --i)
	// {
	// //检查有没有ffd9连在一起的
	// if (szBuffer[i] == 255 && szBuffer[i + 1] == 217)
	// return true;
	// }
	// }
	// }
	//
	// }
}
