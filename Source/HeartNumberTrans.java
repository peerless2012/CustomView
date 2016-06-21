import java.util.ArrayList;
import java.util.List;


public class HeartNumberTrans {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("297.29747f,550.86823f");
		list.add("283.52243f,535.43191f,249.1268f,505.33855f, 220.86277f,483.99412f");
		list.add("137.11867f,420.75228f,125.72108f,411.5999f,91.719238f,380.29088f");
		list.add("29.03471f,322.57071f,2.413622f,264.58086f,2.5048478f,185.95124f");
		list.add("2.5493594f,147.56739f,5.1656152f,132.77929f,15.914734f,110.15398f");
		list.add("34.151433f,71.768267f,61.014996f,43.244667f,95.360052f,25.799457f");
		list.add("119.68545f,13.443675f,131.6827f,7.9542046f,172.30448f,7.7296236f");
		list.add("214.79777f,7.4947896f,223.74311f,12.449347f,248.73919f,26.181459f");
		list.add("279.1637f,42.895777f,310.47909f,78.617167f,316.95242f,103.99205f");

		list.add("320.95052f,119.66445f");
		list.add("330.81015f,98.079942f");

		list.add("386.52632f,-23.892986f,564.40851f,-22.06811f,626.31244f,101.11153f");
		list.add("645.95011f,140.18758f,648.10608f,223.6247f,630.69256f,270.6244f");
		list.add("607.97729f,331.93377f,565.31255f,378.67493f,466.68622f,450.30098f");
		list.add("402.0054f,497.27462f,328.80148f,568.34684f,323.70555f,578.32901f");
		list.add("317.79007f,589.91654f,323.42339f,580.14491f,297.29747f,550.86823f");
		
		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);
			String[] split = string.split(",");
			StringBuilder line = new StringBuilder("mHeartPath."+ (split.length != 6 ? "moveTo" : "cubicTo")+"(");
			for (int j = 0; j < split.length; j++) {
				float parseFloat = Float.parseFloat(split[j]);
				float percent = (parseFloat - 322.5f) / 322.5f;
//				System.out.println("percent = " + percent);
				line.append(percent + "f * width ");
				if (j != split.length - 1) {
					line.append(",");
				}
			}
			line.append(");");
			System.out.println(line.toString());
		}
	}

}
