package my_tsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class initClient {
	int COUNT;

	public initClient(int c) {
		this.COUNT = c;
	}

	// д�����ݵ�d://data.txt
	public void WriteStringToFile(String filePath, String str) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str + "\r\n");
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �������λ��
	public String randomInitClient() {

		String str = "";
		int[] temp = new int[4];// �����洢�û�����ʼ����յ㡣
		
		boolean flag = true;
		while(flag){
		for (int j = 0; j < 4; j++) {
			temp[j] = (int) (Math.random() * 50);
			
		}
		int distanceTemp = (int) (Math.sqrt((temp[0] - temp[2]) * (temp[0] - temp[2]) + (temp[1] - temp[3]) * (temp[1] - temp[3])));
		if(distanceTemp<35){
		str = str + " " + temp[0]+ " " + temp[1]+ " " + temp[2]+ " " + temp[3];
		flag = false;
		str = str + " "
				+ (int) (Math.sqrt((temp[0] - temp[2]) * (temp[0] - temp[2]) + (temp[1] - temp[3]) * (temp[1] - temp[3]))
					+Math.sqrt((temp[0] - 25) * (temp[0] -25) + (temp[1] - 25) * (temp[1] - 25))+ (int) (Math.random() * 30)+1);
		}
		}
		return str;
	}

	// ��ȡ�ϳ�����
	public int[][] getClientStart(String str) throws IOException {
		int[][] clientstart = new int[2][COUNT];
		String strbuff;
		@SuppressWarnings("resource")
		BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
		for (int i = 0; i < COUNT; i++) {
			// ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453
			strbuff = data.readLine();
			// �ַ��ָ�
			String[] strcol = strbuff.split(" ");
			clientstart[0][i] = Integer.valueOf(strcol[1]);
			clientstart[1][i] = Integer.valueOf(strcol[2]);
		}
		return clientstart;
	}

	// ��ȡ�³�����
	public int[][] getClientdone(String str) throws IOException {
		int[][] clientdone = new int[2][COUNT];
		String strbuff;
		@SuppressWarnings("resource")
		BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
		for (int i = 0; i < COUNT; i++) {
			// ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453
			strbuff = data.readLine();
			// �ַ��ָ�
			String[] strcol = strbuff.split(" ");
			clientdone[0][i] = Integer.valueOf(strcol[3]);
			clientdone[1][i] = Integer.valueOf(strcol[4]);
		}
		return clientdone;
	}

	// ��ȡ�ͻ��Ⱥ�ʱ��
	public int[] getClienttime(String str) throws IOException {
		int[] clienttime = new int[COUNT];
		String strbuff;
		@SuppressWarnings("resource")
		BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
		for (int i = 0; i < COUNT; i++) {
			// ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453
			strbuff = data.readLine();
			// �ַ��ָ�
			String[] strcol = strbuff.split(" ");
			clienttime[i] = Integer.valueOf(strcol[5]);

		}
		return clienttime;
	}

	// ��ȡ�ͻ���㵽�յ�ľ���
	public int[] getClientBasicValue(int[][] clientstart, int[][] clientdone) {
		int[] clientBasicValue = new int[COUNT];
		for (int i = 0; i < COUNT; i++) {
			double temp = Math.sqrt(((clientstart[0][i] - clientdone[0][i]) * (clientstart[0][i] - clientdone[0][i])
					+ (clientstart[1][i] - clientdone[1][i]) * (clientstart[1][i] - clientdone[1][i])));
			int inttemp = (int) Math.round(temp);
			if (inttemp < temp) {// ��������
				clientBasicValue[i] = inttemp + 1;
			} else {
				clientBasicValue[i] = inttemp;
			}
		}
		return clientBasicValue;
	}

	// ��ȡ�ͻ������
	public int[][] getClientCostValue(int[][] clientstart, int[][] clientdone) {
		int[][] clientCostValue = new int[COUNT][COUNT];

		for (int i = 0; i < COUNT - 1; i++) {
			for (int j = i; j < COUNT - 1; j++) {
				if (i != j) {
					double temp = Math.pow((Math.pow((clientstart[0][i] - clientdone[0][j]), 2)
							+ Math.pow((clientstart[1][i] - clientdone[1][j]), 2)), 0.5);
					int inttemp = (int) Math.round(temp);
					if (inttemp < temp) {// ��������
						clientCostValue[i][j] = inttemp + 1;
					} else {
						clientCostValue[i][j] = inttemp;
					}
				} else {
					clientCostValue[i][j] = 0;
				}
				// System.out.println("dist_list["+i+"]["+j+"]"+clientBasicValue[i][j]);
			}
		}
		for (int i = (COUNT - 1); i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (i != j) {
					double temp = Math.pow((Math.pow((clientstart[0][i] - clientdone[0][j]), 2)
							+ Math.pow((clientstart[1][i] - clientdone[1][j]), 2)), 0.5);
					int inttemp = (int) Math.round(temp);
					if (inttemp < temp) {// ��������
						clientCostValue[i][j] = inttemp + 1;
					} else {
						clientCostValue[i][j] = inttemp;
					}
				} else {
					clientCostValue[i][j] = 0;

				}
				// System.out.println("dist_list["+i+"]["+j+"]"+clientCostValue[i][j]);
			}
		}
		clientCostValue[clientstart[0].length - 1][clientstart[0].length - 1] = 0;

		return clientCostValue;
	}
	
//	public static void main (String[] args){ //��������
//		initClient initClient = new initClient(250);
//		String string = "";
//		for(int i=0;i<250;i++){
//		string = string+initClient.randomInitClient()+"\n";
//		}
//		//System.out.println(string);
//		initClient.WriteStringToFile("d://data.txt", string);
//	}
}
