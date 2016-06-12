package my_tsp;

import java.util.ArrayList;

public class carController {

	// 获取当前状态为true的cars
	public ArrayList<car> getCarStatu(ArrayList<car> carlist) {
		ArrayList<car> cars = new ArrayList<car>();
		for (int i = 0; i < carlist.size(); i++) {
			car c = carlist.get(i);
			if (c.isStatus()) {
				cars.add(c);
			}
		}
		return cars;
	}

	// 判断car能否在客户规定的时间内将其运输到目的地。
	public boolean checktime(car c, int startx, int starty, int endx, int endy, int time ,int t) {//time 为客户的服务时间，t为当前调度时间
		int x = c.getX();
		int y = c.getY();
		boolean flag = false;
		double tempdistance = Math.sqrt((startx - x) * (startx - x) + (starty - y) * (starty - y))
				+ Math.sqrt((startx - endx) * (startx - endx) + (starty - endy) * (starty - endy));

		if (time > (c.getTime() + tempdistance + t)) {
			flag = true;
		}
		return flag;
	}

	// 判断car在完成下一个订单后能否返回充电点。
	public boolean checkBack(car c, int startx, int starty, int endx, int endy) {
		int x = c.getX();
		int y = c.getY();
		boolean flag = false;
		double tempdistance = Math.sqrt((startx - x) * (startx - x) + (starty - y) * (starty - y))
				+ Math.sqrt((startx - endx) * (startx - endx) + (starty - endy) * (starty - endy));
		tempdistance = tempdistance + Math.sqrt((25 - endx) * (25 - endx) + (25 - endy) * (25 - endy));
		double overlen = c.getCar_len() - tempdistance;
		if (overlen > 0) {
			flag = true;
		}
		return flag;
	}

	public double overLen(car c, int startx, int starty, int endx, int endy) {
		int x = c.getX();
		int y = c.getY();
		double tempdistance = Math.sqrt((startx - x) * (startx - x) + (starty - y) * (starty - y))
				+ Math.sqrt((startx - endx) * (startx - endx) + (starty - endy) * (starty - endy));
		double overlen = c.getCar_len() - tempdistance;
		return overlen;
	}

	public int getTime(car c, int startx, int starty, int endx, int endy) {
		int x = c.getX();
		int y = c.getY();
		int tempdistance = (int) (Math.sqrt((startx - x) * (startx - x) + (starty - y) * (starty - y))
				+ Math.sqrt((startx - endx) * (startx - endx) + (starty - endy) * (starty - endy)));
		// System.out.print(c.getTime()+"\t");
		return c.getTime() + tempdistance;
	}

	// 调度完后将所有车辆的服务时间重置位0
	public void restCarsTime(ArrayList<car> carlist) {

		for (int i = 0; i < carlist.size(); i++) {
			car c = carlist.get(i);
			c.setTime(0);
		}
	}

	// 调度完后将所有车辆的客户重置为空
	// public void restCarsClents(ArrayList<car> carlist){
	// ArrayList<Integer> clients = new ArrayList<Integer> ();
	// for(int i = 0; i<carlist.size();i++){
	// car c= carlist.get(i);
	// c.setClients(clients);
	// }
	// }
}
