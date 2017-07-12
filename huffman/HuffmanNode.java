package huffman;

public class HuffmanNode {//자료들을 가지고 있는 node class
	char letter; //단어
	int frequency; //빈도 수
	int decode = -1; //복호화 코드(가장 위인 root는 -1)
	HuffmanNode lchild;
	HuffmanNode rchild;
	
	HuffmanNode() {//합병하며 생성 시 사용
		letter = '*';
		frequency = 0;
		lchild = null;
		rchild = null;
	}
	
	HuffmanNode(char word) {//단어를 가지고 객체 생성 시 사용
		letter = word;
		frequency = 0;
		lchild = null;
		rchild = null;
	}
	
	public void print_letter() { //단어 별 빈도 수 출력
		System.out.println("word: "+letter+" / frequency: "+ frequency);
	}
	
	public void print_tree(StringBuilder tmp) {
		StringBuilder tmp2 = new StringBuilder(tmp);
		if(this.decode > -1) //복호화 코드 값이 존재 시 문자열에 추가
			tmp2.append(Integer.toString(this.decode));
		//중위순회로 출력
		if(this.lchild != null)
			this.lchild.print_tree(tmp2);
		if(this.letter != '*') { //합병된 노드일 경우 출력하지 않는다
			System.out.println(this.letter+": "+tmp2);
		}
		if(this.rchild != null)
			this.rchild.print_tree(tmp2);
	}
}
