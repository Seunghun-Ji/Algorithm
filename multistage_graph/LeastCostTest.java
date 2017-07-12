package multistage_graph;
import java.util.*;
public class LeastCostTest {

	private static Scanner sc;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int point, line;
		sc = new Scanner (System.in);
		System.out.print("정점의 개수를 입력하세요: ");
		point = sc.nextInt();
		int[][] array = new int[point+1][point+1]; //다단계 그래프 인접행렬
		int[] level = new int[point+1]; //정점 별 레벨 값을 저장하는 배열
		int[] d = new int[point+1]; //d(i,j) 값을 저장하는 배열
		level[1] = 1; //시작 정점 레벨은 1
		
		//인접행렬 초기화
		for(int i=1;i<=point;i++)
			for(int j=1;j<=point;j++) {
				if(i==j)
					array[i][j] = 0;
				else
					array[i][j] = Integer.MAX_VALUE;
			}
		
		//간선 수 입력
		System.out.print("간선의 개수를 입력하세요: ");
		line = sc.nextInt();
		for(int n=1; line != 0;) {
			int i, j, cost;
			System.out.print(n+"번째 간선 입력: ");
			i = sc.nextInt(); //시작점
			j = sc.nextInt(); //끝점
			cost = sc.nextInt(); //비용
			if(i!=j && cost != 0) {
				array[i][j] = cost;
				level[j] = level[i]+1; //입력한 정점의 레벨 값은 이전 정점의 레벨+1
				line--; //입력 카운트 감소
				n++; //몇 번 입력 성공했는지 count
			}
			else //잘못 입력했을 시
				System.out.println("잘못 입력하셨습니다.\n");
		}
		
		//단계별 시작 지점 파악
		int[] v_start = new int[level[point]+1];
		for(int i=1;i<=level[point];i++)
			for(int j=i;j<=point && v_start[i] == 0;j++) {
				if(i==level[j])
					v_start[i]=j; //시작 정점 레벨 파악
			}
		
		//단계 수 파악
		System.out.println("\n<레벨 별 시작 정점>");
		for(int i=1;i<=level[point];i++)
			System.out.println(i+"번째 단계 시작 정점: "+v_start[i]);
		
		//bcost, d 출력
		System.out.println("\n<bcost(i,j), d(i,j) 값 출력>");
		for(int i=2;i<=level[point];i++) {
			if(i==level[point]) //마지막 단계에서는 v_start[i+1]에서 Out of Boundary error가 생기므로 따로 작성
				System.out.println("bcost("+i+","+point+")= "+bcost(i, point, d, v_start, array)+"\td("+i+","+point+")= "+d[point]);
			else
				for(int j=v_start[i];j<v_start[i+1];j++)
					System.out.println("bcost("+i+","+j+")= "+bcost(i, j, d, v_start, array)+"\td("+i+","+j+")= "+d[j]);
		}
		
		//최소비용 경로 출력
		System.out.println("\n<최소 비용 경로>");
		Stack<Integer> spath = new Stack<Integer>();
		for(int i=point;i>0;i=d[i])
			spath.push(i);
		
		System.out.print(spath.pop());
		while(spath.empty()==false)
	        System.out.print(" -> "+spath.pop());
	}
	
	
	public static int bcost(int a, int b, int[] d, int[] v_start, int[][] array) {
		if(a==1) //bcost(1,1)의 경우 값이 0
			return 0;
		int min = Integer.MAX_VALUE;
		int tmp;
		for(int i = v_start[a-1];i<v_start[a];i++)
			if(array[i][b] < Integer.MAX_VALUE) {
				tmp = bcost(a-1, i, d, v_start, array) + array[i][b]; //동적 프로그래밍 후
				if(tmp < min) { //해당 값이 현재 찾은 최소값보다 작을 경우 최신화
					min = tmp;
					d[b] = i; //d값을 최신화
				}
			}
		return min; //최소값 return
	}
}
