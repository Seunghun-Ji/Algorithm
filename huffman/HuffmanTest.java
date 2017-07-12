package huffman;
import java.io.*;
public class HuffmanTest {
/* 17년 1학기 알고리즘 수업 - 허프만 코드 과제 제출용
 * 학교: 단국대학교 | 학과: 소프트웨어학과 | 학번:32131814 | 이름: 지승훈
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HuffmanStack tree = new HuffmanStack();
		//허프만노드를 연결리스트로 보관
		HuffmanNode[] node = new HuffmanNode[27];
		//단어, 반복횟수, 복호화 코드를 가지고 있는 노드
		char word = 'a';
		for(int i=0;i<26;i++) {//27개 node 생성(초기화)
			node[i] = new HuffmanNode(word);
			word++;
		}
		node[26] = new HuffmanNode(' ');
		
		try {
			//참고 사이트: http://yangyag.tistory.com/71
			//파일 경로
			File fl = new File("D:/test.txt");
			//파일을 읽어들여 File Input Stream 객체 생성
			FileInputStream fls = new FileInputStream(fl);
			//File Input Stream 객체를 이용해 Input Stream 객체를 생성하는데 인코딩을 UTF-8로 지정
			InputStreamReader isr = new InputStreamReader(fls, "UTF-8");
			//Input Stream 객체를 이용하여 버퍼를 생성
			BufferedReader bf = new BufferedReader(isr);
			String temp = null;
			while((temp = bf.readLine()) != null) {//한줄씩 읽어들인다.
				for(int i=0;i<temp.length();i++) {
					if(temp.charAt(i) == ' ') //스페이스일 경우, index 26에
						node[26].frequency++;
					else {
						for(int j=0;j<26;j++) //단어(소문자)일 경우, index 0~25에
							if(temp.charAt(i) == node[j].letter) {
							node[j].frequency++;
							break; //찾았으므로 그만 찾는다
							}
					}
				}
			}
		}
		catch (Exception e){ //파일 오류 시 에러 핸들러
			System.out.println("Exception: " + e);
		}
		System.out.println("|파일 내 단어 빈도 수|");
		for(int i=0;i<27;i++)
			if(node[i].frequency != 0){
				//알파벳 a부터 찾아서 연결리스트에 저장
				tree = tree.insert(node[i]);
			}
		//find 포인터로 tree(연결리스트)에 있는 노드들을 접근한다
		HuffmanStack find = new HuffmanStack();
		find = tree;
		while(find != null) {//print_letter 함수로 단어의 빈도수 출력
			find.node.print_letter();
			find = find.next;
		}
		//다시 연결리스트 처음으로 이동해서 tree 생성 시작
		find = tree;
		while(find.next != null)
			find = find.merge(); //가장 작은 빈도수의 노드 2개를 합병
		StringBuilder code = new StringBuilder("");
		System.out.println("\n\n|복호화 코드|");
		find.node.print_tree(code); //각 노드들의 복호화 코드 출력
	}
}
