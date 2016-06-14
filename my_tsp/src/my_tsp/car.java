package my_tsp;

import java.util.ArrayList;

/*
 * car 
 */
public class car implements Cloneable {
	double car_len;// ��������ʻ����
	int x; // ������λ�ã�x����
	int y; // ������λ�ã�y����
	int oldx; // ������λ�ã�x����
	int oldy; // ������λ�ã�y����
	boolean status;// ��������״̬
	boolean timeStatu;//����ʱ��״̬
	int position;// ������carlist�е��±ꡣ
	ArrayList<Integer> clients = new ArrayList<Integer>();// ��¼����Ŀͻ���
	int time;// ��¼���ε������пͻ���ʱ��

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
			o = (car) super.clone();// Object �е�clone()ʶ�����Ҫ���Ƶ�����һ������
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return o;
	}

}
