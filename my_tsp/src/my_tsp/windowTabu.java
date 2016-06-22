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
				System.out.print("������" + cars.get(x).getPosition());
				// System.out.print("\tʣ�������" + carstt.get(x).getCar_len());
				if(!cars.get(x).isTimeStatu()){
					System.out.print("\t����״̬�� ����������");
				}else{
					System.out.print("\t����״̬��" + (cars.get(x).isStatus() ? "�ȴ����û�" : "���زֿ���"));
				}
				System.out.print("\ttime��"+cars.get(x).getTime());
				System.out.print("\t�˿ͣ�");
				for (int i = 0; i < cars.get(x).getClients().size(); i++) {
					System.out.print(cars.get(x).getClients().get(i) + "\t");
				}
				System.out.println("");
			}
			System.out.println("��" + w + "���ȣ�");
			tububycars tabu = new tububycars(200, 1000, 2000, 200, 20, w, w*30);
			tabu.init("src/my_tsp/data.txt");
			cars = tabu.solve(cars);
			
		}
		for (int x = 0; x < cars.size(); x++) {
			System.out.print("������" + cars.get(x).getPosition());
			// System.out.print("\tʣ�������" + carstt.get(x).getCar_len());
			if(cars.get(x).isTimeStatu()){
				System.out.print("\t����״̬��" + (cars.get(x).isStatus() ? "�ȴ����û�" : "���زֿ���"));
				
			}else{
				System.out.print("\t����״̬�� ����������");
			}
			
			System.out.print("\t�˿ͣ�");
			for (int i = 0; i < cars.get(x).getClients().size(); i++) {
				System.out.print(cars.get(x).getClients().get(i) + "\t");
			}
			System.out.println("");
		}
//		tabu tabu = new tabu(40, 200, 200, 40, 20, 0);
//		tabu.init("d://data3.txt");
//		cars = tabu.solve(cars);
//
//		tabu tabu2 = new tabu(40, 200, , 40, 10, 2);  // �˿�����������������������������ɱ��ȣ����ڴ�С�����ȴ���
//		tabu2.init("d://data3.txt");
//		cars = tabu2.solve(cars);
//		System.out.println("end....");
	}
}