package my_tsp;

import java.util.ArrayList;

public class carController {

	// ��ȡ��ǰ״̬Ϊtrue��cars
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

	// �ж�car�ܷ��ڿͻ��涨��ʱ���ڽ������䵽Ŀ�ĵء�
	public boolean checktime(car c, int startx, int starty, int endx, int endy, int time ,int t ) {//time Ϊ�ͻ��ķ���ʱ�䣬tΪ��ǰ����ʱ��
		int x = c.getX();
		int y = c.getY();
		boolean flag = false;
		double tempdistance = Math.sqrt((startx - x) * (startx - x) + (starty - y) * (starty - y))
				+ Math.sqrt((startx - endx) * (startx - endx) + (starty - endy) * (starty - endy));
		
		int timetemp =( t >= c.getTime()? t:c.getTime());
//		if(t>180){
//			System.out.println(time+t+">");System.out.println(t+">"+c.getTime());
//			System.out.println(timetemp + tempdistance);System.out.println(tempdistance);}
		if (time+t >= (timetemp + tempdistance)) {
			flag = true;
		}
		
		//System.out.println(flag);
		return flag;
	}

	// �ж�car�������һ���������ܷ񷵻س��㡣
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
		//System.out.println(flag);
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

	// ����������г����ķ���ʱ������λ0
	public void restCarsTime(ArrayList<car> carlist) {

		for (int i = 0; i < carlist.size(); i++) {
			car c = carlist.get(i);
			c.setTime(0);
		}
	}
	public void setCarsTimeStatu(ArrayList<car> carlist , int t) {

		for (int i = 0; i < carlist.size(); i++) {
			car c = carlist.get(i);
			int t1 = c.getTime();
			if(t1>t){
				c.setTimeStatu(false);
			}else{
				c.setTimeStatu(true);
				c.setTime(t);
			}
		}
	}

	// ����������г����Ŀͻ�����Ϊ��
	// public void restCarsClents(ArrayList<car> carlist){
	// ArrayList<Integer> clients = new ArrayList<Integer> ();
	// for(int i = 0; i<carlist.size();i++){
	// car c= carlist.get(i);
	// c.setClients(clients);
	// }
	// }
}
