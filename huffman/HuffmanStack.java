package huffman;

public class HuffmanStack {//연결리스트를 위한 class
	HuffmanNode node;
	HuffmanStack next;

	HuffmanStack() { //생성자
		node = null;
		next = null;
	}
	
	public HuffmanStack insert(HuffmanNode n) {
		//연결리스트에 노드 삽입 후 업데이트, 삽입 시 빈도수가 작은 순서대로 저장한다
		if(node == null) {//노드가 비어있을 시 그 자리에 넣는다
			node = n;
			return this;
		}
		else if (n.frequency <= node.frequency) {
			//새로 들어온 노드가 빈도 수가 적거나 같을 경우 자리를 바꿔 빈도수가 작은 순서대로 저장
			HuffmanStack tmp = new HuffmanStack();
			tmp.node = this.node;
			tmp.next = this.next;
			this.node = n;
			this.next = tmp;
			return this;
		}
		else {//위 2개의 조건이 아니면 다음 칸에서 다시 노드 삽입 시도
			HuffmanStack tmp = new HuffmanStack();
			tmp.node = n;
			if(this.next == null) //다음 칸이 존재하지 않을 경우 새로 생성
				this.next = new HuffmanStack();
			//다음 tree가 null이었으면 위 3가지 조건 중 1번에 해당된다
			tmp = this.next.insert(n);
			this.next = tmp;
			return this;
		}
	}
	
	public HuffmanStack merge() { //노드 합병 함수
		HuffmanNode mergenode = new HuffmanNode(); //빈도수가 작은 노드 둘을 합병한 노드를 생성
		//작은 순서대로 정렬되어있으므로 합병될 노드는 가장 앞의 빈도수와 그 다음의 빈도수를 합한다
		mergenode.frequency = this.node.frequency + this.next.node.frequency;
		//복호화 코드 할당
		this.node.decode = 0;
		this.next.node.decode = 1;
		//왼쪽 자식과 오른쪽 자식을 알려준다
		mergenode.lchild = this.node;
		mergenode.rchild = this.next.node;
		//합병된 노드를 다시 tree에 삽입 과정
		//앞의 둘을 연결 해제하기 위해 접근 시 null 상태면 새로 생성
		if(this.next.next == null) 
			this.next.next = new HuffmanStack();
		//순서대로 정렬되어 있으므로 삭제 될 노드 다음부터 재정렬해서 합병 노드 삽입
		this.next.next = this.next.next.insert(mergenode);
		return this.next.next;
	}
}
