package my_tsp;

import java.util.ArrayList;

/*
 * car 
 */
public class car implements Cloneable {
	double car_len;// 辆车的行驶距离
	int x; // 车辆的位置：x坐标
	int y; // 车辆的位置：y坐标
	int oldx; // 车辆的位置：x坐标
	int oldy; // 车辆的位置：y坐标
	boolean status;// 车辆拉客状态
	boolean timeStatu;//车辆时间状态
	int position;// 车辆在carlist中的下标。
	ArrayList<Integer> clients = new ArrayList<Integer>();// 记录服务的客户。
	int time;// 记录本次调度所有客户的时间

	public ArrayList<Integer> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Integer> clients) {
		this.clients = clients;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public car(int cl, int x, int y, boolean status, int p, int t, Boolean timestatu) {
		this.car_len = cl;
		this.x = x;
		this.y = y;
		this.position = p;
		this.status = status;
		this.time = t;
		this.timeStatu = timestatu;
	}

	public boolean isTimeStatu() {
		return timeStatu;
	}

	public void setTimeStatu(boolean timeStatu) {
		this.timeStatu = timeStatu;
	}

	public double getCar_len() {
		return car_len;
	}

	public void setCar_len(double d) {
		this.car_len = d;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object clone() {
		Object o = null;
		try {
			o = (car) super.clone();// Object 中的clone()识别出你要复制的是哪一个对象。
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return o;
	}

}
