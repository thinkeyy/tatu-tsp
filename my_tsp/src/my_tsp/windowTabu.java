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
		carController carController = new carController();
		for (int w = 0; w < (200 / 20); w++) {
			carController.setCarsTimeStatu(cars, 30*w);
			for (int x = 0; x < cars.size(); x++) {
				System.out.print("车辆：" + cars.get(x).getPosition());
				// System.out.print("\t剩余电量：" + carstt.get(x).getCar_len());
				if(!cars.get(x).isTimeStatu()){
					System.out.print("\t车辆状态： 车辆服务中");
				}else{
					System.out.print("\t车辆状态：" + (cars.get(x).isStatus() ? "等待新用户" : "返回仓库充电"));
				}
				System.out.print("\ttime："+cars.get(x).getTime());
				System.out.print("\t乘客：");
				for (int i = 0; i < cars.get(x).getClients().size(); i++) {
					System.out.print(cars.get(x).getClients().get(i) + "\t");
				}
				System.out.println("");
			}
			System.out.println("第" + w + "调度：");
			tububycars tabu = new tububycars(200, 1000, 2000, 200, 20, w, w*30);
			tabu.init("src/my_tsp/data.txt");
			cars = tabu.solve(cars);
			
		}
		for (int x = 0; x < cars.size(); x++) {
			System.out.print("车辆：" + cars.get(x).getPosition());
			// System.out.print("\t剩余电量：" + carstt.get(x).getCar_len());
			if(cars.get(x).isTimeStatu()){
				System.out.print("\t车辆状态：" + (cars.get(x).isStatus() ? "等待新用户" : "返回仓库充电"));
				
			}else{
				System.out.print("\t车辆状态： 车辆服务中");
			}
			
			System.out.print("\t乘客：");
			for (int i = 0; i < cars.get(x).getClients().size(); i++) {
				System.out.print(cars.get(x).getClients().get(i) + "\t");
			}
			System.out.println("");
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