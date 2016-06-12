package my_tsp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class tspDemo {

	private int MAX_GEN;// ��������
	private int N;// ÿ�������ھӸ���
	private int tabuLen;// ���ɳ���
	private int cityNum; // �������������볤��

	private int[][] distance; // �������
	private int bestT;// ��ѳ��ִ���

	private int[] Ghh;// ��ʼ·������
	private int[] bestGh;// ��õ�·������
	private int bestEvaluation;
	private int[] LocalGhh;// ������ñ���
	private int localEvaluation;
	private int[] tempGhh;// �����ʱ����
	private int tempEvaluation;

	private int[][] tabuTab;// ���ɱ�

	private int t;// ��ǰ����

	private Random random;

	public tspDemo() {

	}

	/**
	 * constructor of GA
	 * 
	 * @param n
	 *            ��������
	 * @param g
	 *            ���д���
	 * @param c
	 *            ÿ�������ھӸ���
	 * @param m
	 *            ���ɳ���
	 * 
	 **/
	public tspDemo(int n, int g, int c, int m) {
		cityNum = n;
		MAX_GEN = g;
		N = c;
		tabuLen = m;
	}

	// ��������һ��ָ��������Ա���ע�Ĵ���Ԫ���ڲ���ĳЩ���汣�־�Ĭ
	@SuppressWarnings("resource")
	/**
	 * ��ʼ��Tabu�㷨��
	 * 
	 * @param filename
	 *            �����ļ��������ļ��洢���г��нڵ���������
	 * @throws IOException
	 */
	private void init(String filename) throws IOException {
		// ��ȡ����
		int[] x;
		int[] y;
		String strbuff;
		BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		distance = new int[cityNum][cityNum];
		x = new int[cityNum];
		y = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			// ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453
			strbuff = data.readLine();
			// �ַ��ָ�
			String[] strcol = strbuff.split(" ");
			x[i] = Integer.valueOf(strcol[1]);// x����
			y[i] = Integer.valueOf(strcol[2]);// y����
		}
		// ����������
		// ����Ծ������⣬������㷽��Ҳ��һ�����˴��õ���att48��Ϊ����������48�����У�������㷽��Ϊαŷ�Ͼ��룬����ֵΪ10628
		for (int i = 0; i < cityNum - 1; i++) {
			distance[i][i] = 0; // �Խ���Ϊ0
			for (int j = i + 1; j < cityNum; j++) {
				double rij = Math.sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j])) / 10.0);
				// �������룬ȡ��
				int tij = (int) Math.round(rij);
				if (tij < rij) {
					distance[i][j] = tij + 1;
					distance[j][i] = distance[i][j];
				} else {
					distance[i][j] = tij;
					distance[j][i] = distance[i][j];
				}
			}
		}
		distance[cityNum - 1][cityNum - 1] = 0;

		Ghh = new int[cityNum]; // ��ʼ·��
		bestGh = new int[cityNum];// ���·��
		bestEvaluation = Integer.MAX_VALUE;//
		LocalGhh = new int[cityNum];// ��ǰ��ô���
		localEvaluation = Integer.MAX_VALUE;
		tempGhh = new int[cityNum];// �����ʱ����
		tempEvaluation = Integer.MAX_VALUE;

		tabuTab = new int[tabuLen][cityNum];
		bestT = 0;
		t = 0;

		random = new Random(System.currentTimeMillis());
		/*
		 * for(int i=0;i<cityNum;i++) { for(int j=0;j<cityNum;j++) {
		 * System.out.print(distance[i][j]+","); } System.out.println(); }
		 */

	}

	// ��ʼ������Ghh
	void initGroup() {
		int i, j;
		Ghh[0] = random.nextInt(65535) % cityNum;
		for (i = 1; i < cityNum;)// ���볤��
		{
			Ghh[i] = random.nextInt(65535) % cityNum;
			for (j = 0; j < i; j++) {
				if (Ghh[i] == Ghh[j]) {
					break;
				}
			}
			if (j == i) {
				i++;
			}
		}
	}

	// ���Ʊ����壬���Ʊ���Gha��Ghb
	public void copyGh(int[] Gha, int[] Ghb) {
		for (int i = 0; i < cityNum; i++) {
			Ghb[i] = Gha[i];
		}
	}

	public int evaluate(int[] chr) {
		// 0123
		int len = 0;
		// ���룬��ʼ����,����1,����2...����n
		for (int i = 1; i < cityNum; i++) {
			len += distance[chr[i - 1]][chr[i]];
		}
		// ����n,��ʼ����
		len += distance[chr[cityNum - 1]][chr[0]];// �ô����ǵ�ĩβ��Գ�ʼ��ľ��룬
		return len;
	}

	// ���򽻻����õ��ھ�
	public void Linju(int[] Gh, int[] tempGh) {
		int i, temp;
		int ran1, ran2;

		for (i = 0; i < cityNum; i++) {
			tempGh[i] = Gh[i];
		}
		ran1 = random.nextInt(65535) % cityNum;
		ran2 = random.nextInt(65535) % cityNum;
		while (ran1 == ran2) {
			ran2 = random.nextInt(65535) % cityNum;
		}
		temp = tempGh[ran1];
		tempGh[ran1] = tempGh[ran2];
		tempGh[ran2] = temp;
	}

	// �жϱ����Ƿ��ڽ��ɱ���
	public int panduan(int[] tempGh) {
		int i, j;
		int flag = 0;
		for (i = 0; i < tabuLen; i++) {
			flag = 0;
			for (j = 0; j < cityNum; j++) {
				if (tempGh[j] != tabuTab[i][j]) {
					flag = 1;// ����ͬ
					break;
				}
			}
			if (flag == 0) // ��ͬ�����ش�����ͬ
			{
				// return 1;
				break;
			}
		}
		if (i == tabuLen) // ����
		{
			return 0;// ������
		} else {
			return 1;// ����
		}
	}

	// �������������
	public void jiejinji(int[] tempGh) {
		int i, j, k;
		// ɾ�����ɱ��һ�����룬���������ǰŲ��
		for (i = 0; i < tabuLen - 1; i++) {
			for (j = 0; j < cityNum; j++) {
				tabuTab[i][j] = tabuTab[i + 1][j];
			}
		}

		// �µı��������ɱ�
		for (k = 0; k < cityNum; k++) {
			tabuTab[tabuLen - 1][k] = tempGh[k];
		}

	}

	public void solve() {
		int nn;
		// ��ʼ������Ghh
		initGroup();
		copyGh(Ghh, bestGh);// ���Ƶ�ǰ����Ghh����ñ���bestGh
		bestEvaluation = evaluate(Ghh);

		while (t < MAX_GEN) { // MAX_GEN :1000 t:��ǰ����
			nn = 0;
			localEvaluation = Integer.MAX_VALUE;
			while (nn < N) { // ÿ�������������
				Linju(Ghh, tempGhh);// �õ���ǰ����Ghh���������tempGhh
				if (panduan(tempGhh) == 0) // �жϱ����Ƿ��ڽ��ɱ���
				{
					// ����
					tempEvaluation = evaluate(tempGhh);
					if (tempEvaluation < localEvaluation) {
						copyGh(tempGhh, LocalGhh);
						localEvaluation = tempEvaluation;
					}
					nn++;
				}
			}
			if (localEvaluation < bestEvaluation) {
				bestT = t;
				copyGh(LocalGhh, bestGh);
				bestEvaluation = localEvaluation;
			}
			copyGh(LocalGhh, Ghh);// ����ǰ��õı���·������Ϊ�µĳ�ʼ·��

			// ����ɱ�LocalGhh������ɱ�
			jiejinji(LocalGhh);
			t++;
		}

		System.out.println("��ѳ��ȳ��ִ�����");
		System.out.println(bestT);
		System.out.println("��ѳ���");
		System.out.println(bestEvaluation);
		System.out.println("���·����");
		for (int i = 0; i < cityNum; i++) {
			System.out.print(bestGh[i] + ",");
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Start....");
		tspDemo tabu = new tspDemo(48, 1000, 200, 20);
		tabu.init("c://data.txt");
		tabu.solve();
	}
}