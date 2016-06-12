package my_tsp;

public class student implements Cloneable {
	String name;
	int age;

	student(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Object clone() {
		Object o = null;
		try {
			o = (student) super.clone();// Object 中的clone()识别出你要复制的是哪一个对象。
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return o;
	}

	public static void main(String[] args) {
		student s1 = new student("zhangsan", 18);
		student s2 = (student) s1.clone();
		s2.name = "lisi";
		s2.age = 20;
		// 修改学生2后，不影响学生1的值。
		System.out.println("name=" + s1.name + "," + "age=" + s1.age);
		System.out.println("name=" + s2.name + "," + "age=" + s2.age);
	}
}