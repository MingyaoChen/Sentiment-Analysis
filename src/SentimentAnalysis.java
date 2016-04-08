
import com.dcu.mingyao.FeatureExtraction;

public class SentimentAnalysis {
	public static void main(String args[]) {
		String data_path = "C:/Users/mingyao/Documents/eclipse/SentimentAnalysis/src/com/dcu/mingyao/test.txt";
		FeatureExtraction pp = new FeatureExtraction(data_path);
		pp.process();
	}
}
