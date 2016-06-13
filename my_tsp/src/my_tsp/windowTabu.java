package my_tsp;

import java.io.IOException;
import java.util.ArrayList;

public class windowTabu {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Start....");
		ArrayList<car> cars = new ArrayList<car>();

		for (int w = 0; w < (200 / 20); w++) {
			System.out.println("第" + w + "调度：");
			tabu tabu = new tabu(200, 2000, 200, 2000, 20, w);
			tabu.init("D://tatu-tsp//my_tsp//src//my_tsp//data.txt");
			cars = tabu.solve(cars);
		}

//		tabu tabu = new tabu(40, 200, 200, 40, 20, 0);
//		tabu.init("d://data3.txt");
//		cars = tabu.solve(cars);
//
//		tabu tabu2 = new tabu(40, 200, , 40, 10, 2);  // 顾客总量，代数，搜索邻域次数，禁忌表长度，窗口大小，调度次数
//		tabu2.init("d://data3.txt");
//		cars = tabu2.solve(cars);
//		System.out.println("end....");
	}
}