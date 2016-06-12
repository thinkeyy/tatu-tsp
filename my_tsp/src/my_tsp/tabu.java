package my_tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tabu {

	private int MAX_GEN;// 迭代次数
	private int N;// 每次搜索邻居个数
	private int tabuLen;// 禁忌长度
	private int cityNum; // 客户数量，编码长度
	private int windowNum;

	private int[] clienttime;// 客户的等待时间
	private int[][] clientstart;// 客户上车点
	private int[][] clientdone;// 客户下车点
	private int[] distance;// 客户下车点
	private int bestT;// 最佳出现代数

	// private ArrayList<car> cars = new ArrayList<car>();

	private int[] Ghh;// 初始路径编码
	private int[] bestGh;// 最好的路径编码
	private double bestEvaluation;
	private int[] LocalGhh;// 当代最好编码
	private double localEvaluation;
	private int[] tempGhh;// 存放临时编码
	private double tempEvaluation;

	private int[][] tabuTab;// 禁忌表

	private int t;// 当前代数
	private int w;// 初始化

	private carController carc = new carController();

	public tabu() {

	}

	/**
	 * constructor of GA
	 * 
	 * @param n
	 *            城市数量
	 * @param g
	 *            运行代数
	 * @param c
	 *            每次搜索邻居个数
	 * @param m
	 *            禁忌长度
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

	// 给编译器一条指令，告诉它对被批注的代码元素内部的某些警告保持静默
	/**
	 * 初始化Tabu算法类
	 * 
	 * @param filename
	 *            数据文件名，该文件存储所有城市节点坐标数据
	 * @throws IOException
	 */
	public void init(String filename) throws IOException {
		// 读取数据

		initClient initc = new initClient(cityNum);

		clientstart = initc.getClientStart(filename);
		clientdone = initc.getClientdone(filename);
		clienttime = initc.getClienttime(filename);
		distance = initc.getClientBasicValue(clientstart, clientdone);
//		for(int i=0 ;i<distance.length; i++){
//			System.out.println(i+"\t"+distance[i]);
//		}
		Ghh = new int[windowNum]; // 初始路径
		bestGh = new int[windowNum];// 最好路径
		bestEvaluation = Integer.MAX_VALUE;//
		LocalGhh = new int[windowNum];// 当前最好代码
		localEvaluation = Integer.MAX_VALUE;
		tempGhh = new int[windowNum];// 存放临时编码
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
						// 标记要反回车库的车。
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
				// 添加新车
				car c = new car(200, 25, 25, true, carstemp.size(), 0);
				carstemp.add(c);
			}
		}

		valuemap.put("carstemp", carstemp);
		valuemap.put("len", len);
		return valuemap;
	}

	// 初始化编码Ghh
	void initGroup(int s, int e) {

		int i, j;
		Ghh[0] = (int) (Math.random() * (e - s)) + s;
		for (i = 1; i < (e - s);)// 编码长度
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
		// System.out.print("初始化");
		// for(int k = 0; k < Ghh.length; k++) {
		// System.out.print(+Ghh[k]+"\t");
		// }
	}

	public void copyGh(int[] Gha, int[] Ghb) {
		for (int i = 0; i < Gha.length; i++) {
			Ghb[i] = Gha[i];
		}
	}

	// 邻域交换，得到邻居
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

	// 判断编码是否在禁忌表中
	public int panduan(int[] tempGh) {
		int i, j;
		int flag = 0;
		for (i = 0; i < tabuLen; i++) {
			flag = 0;
			for (j = 0; j < tempGh.length; j++) {
				if (tempGh[j] != tabuTab[i][j]) {
					flag = 1;// 不相同
					break;
				}
			}
			if (flag == 0) // 相同，返回存在相同
			{
				// return 1;
				break;
			}
		}
		if (i == tabuLen) // 不等
		{
			return 0;// 不存在
		} else {
			return 1;// 存在
		}
	}

	// 解禁忌与加入禁忌
	public void jiejinji(int[] tempGh) {
		int i, j, k;
		// 删除禁忌表第一个编码，后面编码往前挪动
		for (i = 0; i < tabuLen - 1; i++) {
			for (j = 0; j < tempGh.length; j++) {
				tabuTab[i][j] = tabuTab[i + 1][j];
			}
		}

		// 新的编码加入禁忌表
		for (k = 0; k < tempGh.length; k++) {
			tabuTab[tabuLen - 1][k] = tempGh[k];
		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<car> solve(ArrayList<car> cars) {
		int nn;
		initGroup(windowNum * w, windowNum * (w + 1));
		t = 0;
		copyGh(Ghh, bestGh);// 复制当前编码Ghh到最好编码bestGh
		bestEvaluation = (double) evaluate(Ghh, cars).get("len");

		while (t < MAX_GEN) { // MAX_GEN :1000 t:当前代数
			nn = 0;
			localEvaluation = Integer.MAX_VALUE;
			while (nn < N) { // 每次搜索邻域个数
				Linju(Ghh, tempGhh);// 得到当前编码Ghh的邻域编码tempGhh
				if (panduan(tempGhh) == 0) // 判断编码是否在禁忌表中
				{
					// 不在
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
			copyGh(LocalGhh, Ghh);// 将当前最好的本地路径，作为新的初始路径

			// 解禁忌表，LocalGhh加入禁忌表
			jiejinji(LocalGhh);
			t++;
		}

		ArrayList<car> carstt = new ArrayList<car>();
		carstt.addAll((ArrayList<car>) evaluate(bestGh, cars).get("carstemp"));

		System.out.println("最佳调度：");
		for (int x = 0; x < carstt.size(); x++) {
			System.out.print("车辆：" + carstt.get(x).getPosition());
			// System.out.print("\t剩余电量：" + carstt.get(x).getCar_len());
			System.out.print("\t车辆状态：" + (carstt.get(x).isStatus() ? "等待新用户" : "返回仓库充电"));
			System.out.print("\t乘客：");
			for (int i = 0; i < carstt.get(x).getClients().size(); i++) {
				System.out.print(carstt.get(x).getClients().get(i) + "\t");
			}
			System.out.println("");
		}
		System.out.print("最佳长度出现代数：\t");
		System.out.println(bestT);
		System.out.print("最少浪费长度：\t");
		System.out.println(bestEvaluation);
		carc.restCarsTime(carstt);
		return carstt;
	}

}