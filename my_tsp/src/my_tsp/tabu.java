package my_tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tabu {

	private int MAX_GEN;// ��������
	private int N;// ÿ�������ھӸ���
	private int tabuLen;// ���ɳ���
	private int cityNum; // �ͻ����������볤��
	private int windowNum;

	private int[] clienttime;// �ͻ��ĵȴ�ʱ��
	private int[][] clientstart;// �ͻ��ϳ���
	private int[][] clientdone;// �ͻ��³���
	private int[] distance;// �ͻ��³���
	private int bestT;// ��ѳ��ִ���

	// private ArrayList<car> cars = new ArrayList<car>();

	private int[] Ghh;// ��ʼ·������
	private int[] bestGh;// ��õ�·������
	private double bestEvaluation;
	private int[] LocalGhh;// ������ñ���
	private double localEvaluation;
	private int[] tempGhh;// �����ʱ����
	private double tempEvaluation;

	private int[][] tabuTab;// ���ɱ�

	private int t;// ��ǰ����
	private int w;// ��ʼ��

	private carController carc = new carController();

	public tabu() {

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
	public tabu(int n, int g, int c, int m, int wm, int w) {
		cityNum = n;
		MAX_GEN = g;
		N = c;
		tabuLen = m;
		windowNum = wm;
		this.w = w;
	}

	// ��������һ��ָ��������Ա���ע�Ĵ���Ԫ���ڲ���ĳЩ���汣�־�Ĭ
	/**
	 * ��ʼ��Tabu�㷨��
	 * 
	 * @param filename
	 *            �����ļ��������ļ��洢���г��нڵ���������
	 * @throws IOException
	 */
	public void init(String filename) throws IOException {
		// ��ȡ����

		initClient initc = new initClient(cityNum);

		clientstart = initc.getClientStart(filename);
		clientdone = initc.getClientdone(filename);
		clienttime = initc.getClienttime(filename);
		distance = initc.getClientBasicValue(clientstart, clientdone);
//		for(int i=0 ;i<distance.length; i++){
//			System.out.println(i+"\t"+distance[i]);
//		}
		Ghh = new int[windowNum]; // ��ʼ·��
		bestGh = new int[windowNum];// ���·��
		bestEvaluation = Integer.MAX_VALUE;//
		LocalGhh = new int[windowNum];// ��ǰ��ô���
		localEvaluation = Integer.MAX_VALUE;
		tempGhh = new int[windowNum];// �����ʱ����
		tempEvaluation = Integer.MAX_VALUE;

		tabuTab = new int[tabuLen][windowNum];
		bestT = 0;
		t = 0;
	}

	Map<String, Object> evaluate(int[] ghh, ArrayList<car> cars) {
		int i = 0;
		int j = 0;
		double len = 0;
		Map<String, Object> valuemap = new HashMap<>();

		ArrayList<car> carstemp = new ArrayList<>();
		for (car c : cars) {
			car ctemp = (car) c.clone();
			carstemp.add(ctemp);
		}
		if (carstemp.isEmpty() || carstemp.size() == 0) {
			car c = new car(200, 25, 25, true, carstemp.size(), 0);
			carstemp.add(c);
		}

		while (j < ghh.length) {
			if (i < carstemp.size()) {
				if (carstemp.get(i).isStatus()) {

					if (carc.checkBack(carstemp.get(i), clientstart[0][ghh[j]], clientstart[1][ghh[j]],
							clientdone[0][ghh[j]], clientdone[1][ghh[j]])
							&& carc.checktime(carstemp.get(i), clientstart[0][ghh[j]], clientstart[1][ghh[j]],
									clientdone[0][ghh[j]], clientdone[1][ghh[j]], clienttime[ghh[j]] ,0)) {

						len = len + Math.sqrt((clientstart[0][ghh[j]] - carstemp.get(i).getX())
								* (clientstart[0][ghh[j]] - carstemp.get(i).getX())
								+ (clientstart[1][ghh[j]] - carstemp.get(i).getY())
										* (clientstart[1][ghh[j]] - carstemp.get(i).getY()));

						carstemp.get(i).setCar_len(carc.overLen(carstemp.get(i), clientstart[0][ghh[j]],
								clientstart[1][ghh[j]], clientdone[0][ghh[j]], clientdone[1][ghh[j]]));

						carstemp.get(i).setTime(carc.getTime(carstemp.get(i), clientstart[0][ghh[j]],
								clientstart[1][ghh[j]], clientdone[0][ghh[j]], clientdone[1][ghh[j]]));

						carstemp.get(i).setX(clientdone[0][ghh[j]]);

						carstemp.get(i).setY(clientdone[1][ghh[j]]);

						ArrayList<Integer> clents = new ArrayList<Integer>();
						clents.addAll(carstemp.get(i).getClients());

						clents.add(ghh[j]);

						carstemp.get(i).setClients(clents);

						j = j + 1;

					} else {
						// ���Ҫ���س���ĳ���
						if ((!carc.checkBack(carstemp.get(i), clientstart[0][ghh[j]], clientstart[1][ghh[j]],
								clientdone[0][ghh[j]], clientdone[1][ghh[j]]))) {
							carstemp.get(i).setStatus(false);
							len = len + Math.sqrt((25 - carstemp.get(i).getX()) * (25 - carstemp.get(i).getX())
									+ (25 - carstemp.get(i).getY()) * (25 - carstemp.get(i).getY()));
						}
						i++;
					}
				} else {
					i++;
				}
			} else {
				// ����³�
				car c = new car(200, 25, 25, true, carstemp.size(), 0);
				carstemp.add(c);
			}
		}

		valuemap.put("carstemp", carstemp);
		valuemap.put("len", len);
		return valuemap;
	}

	// ��ʼ������Ghh
	void initGroup(int s, int e) {

		int i, j;
		Ghh[0] = (int) (Math.random() * (e - s)) + s;
		for (i = 1; i < (e - s);)// ���볤��
		{
			Ghh[i] = (int) (Math.random() * (e - s)) + s;
			for (j = 0; j < i; j++) {
				if (Ghh[i] == Ghh[j]) {
					break;
				}
			}
			if (j == i) {
				i++;
			}
		}
		// System.out.print("��ʼ��");
		// for(int k = 0; k < Ghh.length; k++) {
		// System.out.print(+Ghh[k]+"\t");
		// }
	}

	public void copyGh(int[] Gha, int[] Ghb) {
		for (int i = 0; i < Gha.length; i++) {
			Ghb[i] = Gha[i];
		}
	}

	// ���򽻻����õ��ھ�
	public void Linju(int[] Gh, int[] tempGh) {
		int i, temp;
		int ran1, ran2;

		for (i = 0; i < Gh.length; i++) {
			tempGh[i] = Gh[i];
		}
		ran1 = (int) (Math.random() * Gh.length);
		ran2 = (int) (Math.random() * Gh.length);
		while (ran1 == ran2) {
			ran2 = (int) (Math.random() * Gh.length);
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
			for (j = 0; j < tempGh.length; j++) {
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
			for (j = 0; j < tempGh.length; j++) {
				tabuTab[i][j] = tabuTab[i + 1][j];
			}
		}

		// �µı��������ɱ�
		for (k = 0; k < tempGh.length; k++) {
			tabuTab[tabuLen - 1][k] = tempGh[k];
		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<car> solve(ArrayList<car> cars) {
		int nn;
		initGroup(windowNum * w, windowNum * (w + 1));
		t = 0;
		copyGh(Ghh, bestGh);// ���Ƶ�ǰ����Ghh����ñ���bestGh
		bestEvaluation = (double) evaluate(Ghh, cars).get("len");

		while (t < MAX_GEN) { // MAX_GEN :1000 t:��ǰ����
			nn = 0;
			localEvaluation = Integer.MAX_VALUE;
			while (nn < N) { // ÿ�������������
				Linju(Ghh, tempGhh);// �õ���ǰ����Ghh���������tempGhh
				if (panduan(tempGhh) == 0) // �жϱ����Ƿ��ڽ��ɱ���
				{
					// ����
					tempEvaluation = (double) evaluate(tempGhh, cars).get("len");
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

		ArrayList<car> carstt = new ArrayList<car>();
		carstt.addAll((ArrayList<car>) evaluate(bestGh, cars).get("carstemp"));

		System.out.println("��ѵ��ȣ�");
		for (int x = 0; x < carstt.size(); x++) {
			System.out.print("������" + carstt.get(x).getPosition());
			// System.out.print("\tʣ�������" + carstt.get(x).getCar_len());
			System.out.print("\t����״̬��" + (carstt.get(x).isStatus() ? "�ȴ����û�" : "���زֿ���"));
			System.out.print("\t�˿ͣ�");
			for (int i = 0; i < carstt.get(x).getClients().size(); i++) {
				System.out.print(carstt.get(x).getClients().get(i) + "\t");
			}
			System.out.println("");
		}
		System.out.print("��ѳ��ȳ��ִ�����\t");
		System.out.println(bestT);
		System.out.print("�����˷ѳ��ȣ�\t");
		System.out.println(bestEvaluation);
		carc.restCarsTime(carstt);
		return carstt;
	}

}