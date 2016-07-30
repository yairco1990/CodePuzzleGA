
public class Code implements Comparable<Code>{
	private int[] array;
	private Integer grade;

	public Code(boolean generateCode) {
		grade = 100;
		int length = GA.codeLength;
		array = new int[length];
		if(generateCode) {
			for (int i = 0; i < length; i++) {
				array[i] = (int) (Math.random() * 10);
			}
		}
	}

	public Code(int []arr) {
		array = arr;
	}
	
	@Override
	public String toString() {
		String s = "[";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i] + ", ";
		}
		return s + "] errors = " + grade;
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public int compareTo(Code o) {
		return this.getGrade() - o.getGrade();
	}

}
