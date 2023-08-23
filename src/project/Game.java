package project;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game {
	JFrame StartFrame = new JFrame("가위바위보 게임");
	JButton[] SRPbtn = new JButton[3]; // 가위, 바위, 보 버튼 배열
	int playerChoose; // 플레이어가 어떤 버튼을 선택했는지
	int comChoose; // 컴퓨터가 어떤 거를 선택했는지
	int gameCount; // 게임 카운트
	int comScore = 0;
	int playerScore = 0;

	JLabel timerLbl = new JLabel(""); // 타이머 라벨
	JLabel playerScoreLbl = new JLabel("0"); // 플레이어 점수 라벨
	JLabel comScoreLbl = new JLabel("0"); // 컴퓨터 점수 라벨
	
	JFrame PlayFrame = new JFrame("게임 화면");

	public Game() {
		StartFrame(); // 시작화면 불러오기 (프로그램이 시작화면부터 이어짐)
	}

	public void StartFrame() { // 시작 화면 메소드

		JButton StartBtn = new JButton(); // 게임시작 버튼
		JButton ExplainBtn = new JButton("게임 설명"); // 게임설명 버튼

		StartBtn.setBounds(80, 560, 217, 70);
		StartFrame.add(StartBtn);
		ExplainBtn.setBounds(80, 470, 217, 70);
		StartFrame.add(ExplainBtn);

		StartFrame.setLayout(null); // 시작 화면의 배치 방법을 null로 지정

		ImagePanel StartBtnImg = new ImagePanel(new ImageIcon("./image/게임시작버튼.png").getImage());
		StartBtn.add(StartBtnImg);

		ImagePanel StartFrameImg = new ImagePanel(new ImageIcon("./image/게임시작화면.jpg").getImage());
		StartFrame.add(StartFrameImg);

		ExplainBtn.addActionListener(new ActionListener() { // 게임설명 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				ExplainFrame(); // 게임설명 화면 불러오기
			}
		});

		StartBtn.addActionListener(new ActionListener() { // 게임시작 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				StartFrame.setVisible(false);
				PlayFrame(); // 게임진행 화면 불러오기
			}
		});

		StartFrame.setResizable(false); // 창 크기 조절 불가
		StartFrame.setSize(840, 720);
		StartFrame.setLocationRelativeTo(null); // 창을 정중앙에서 출력
		StartFrame.setVisible(true); // 창이 화면에 보이게
		StartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫았을 때 프로그램이 종료되게 함
	}

	public void PlayFrame() { // 게임진행 화면

		for (int i = 0; i < SRPbtn.length; i++) {
			SRPbtn[i] = new JButton();

			ImagePanel SRPbtnImg = new ImagePanel(new ImageIcon("./image/버튼" + (i + 1) + ".png").getImage());
			SRPbtn[i].add(SRPbtnImg);

			SRPbtn[i].setBounds((i + 2) * 67 + i * 150, 470, 140, 140);
			SRPbtn[i].addActionListener(btnListener);
			PlayFrame.add(SRPbtn[i]);
		}

		playerScoreLbl.setBounds(220, 17, 500, 300);
		playerScoreLbl.setFont(new Font("고딕", Font.BOLD, 35));
		PlayFrame.add(playerScoreLbl);

		comScoreLbl.setBounds(160, 17, 500, 300);
		comScoreLbl.setFont(new Font("고딕", Font.BOLD, 35));
		PlayFrame.add(comScoreLbl);

		timerLbl.setBounds(630, 17, 500, 300);
		timerLbl.setFont(new Font("고딕", Font.BOLD, 35));
		PlayFrame.add(timerLbl);

		ImagePanel PlayFrameImg = new ImagePanel(new ImageIcon("./image/게임진행화면.jpg").getImage());
		PlayFrame.add(PlayFrameImg);

		// 게임이 3초마다 진행되게 하는 타이머
		Timer timer = new Timer(3000, new ActionListener() {
			int secondsPassed = 3; // 타이머 시간

			@Override
			public void actionPerformed(ActionEvent evt) {
				timerLbl.setText(Integer.toString(secondsPassed));
				secondsPassed--;

				if (secondsPassed == 0) {
					PlayLogic();
					System.out.println("게임 카운트: " + gameCount);

					if (gameCount >= 3) {
						((Timer) evt.getSource()).stop();
						// 게임 종료 후 승자 결정
						String winner;
						if (playerScore > comScore) {
							winner = "플레이어";
						} else if (comScore > playerScore) {
							winner = "컴퓨터";
						} else {
							winner = "무승부";
						}
						System.out.println("게임 종료! " + winner + " 승리!");
						PlayFrame.setVisible(false);
						ResultFrame();
					}
					
					secondsPassed = 3;
				}
			}
		});
		timer.start();

		PlayFrame.setLayout(null);
		PlayFrame.setVisible(true);
		PlayFrame.setResizable(false);
		PlayFrame.setSize(840, 720);
		PlayFrame.setLocationRelativeTo(null);
		PlayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	ActionListener btnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < SRPbtn.length; i++) {
				if (e.getSource() == SRPbtn[i]) {
					// 해당 버튼에 따라 playerChoose에 값을 할당
					playerChoose = i;
					System.out.println("Player 선택: " + playerChoose);
				}
			}
		}
	};

	public void PlayLogic() {
		Random comRd = new Random();
		comChoose = comRd.nextInt(3);
		System.out.println("컴퓨터 선택: " + comChoose);

		switch (playerChoose - comChoose) {
		case 0:
			System.out.println("비겼습니다.");
			break;
		case 1:
		case -2:
			System.out.println("이겼습니다.");
			playerScore++;
			break;
		case 2:
		case -1:
			System.out.println("졌습니다.");
			comScore++;
			break;
		}

		System.out.println("플레이어 점수: " + playerScore + " vs " + "컴퓨터 점수: " + comScore);

		playerChoose = -1;
		gameCount++;
	}

	public void ResultFrame() {
		   JFrame ResultFrame = new JFrame("결과 화면");

		   ImagePanel ResultFrameImg = new ImagePanel(new ImageIcon("./image/게임결과화면.jpg").getImage());
		   ResultFrame.add(ResultFrameImg);

		   JButton AgainBtn = new JButton("게임다시시작"); // 게임설명 버튼
		   JButton EndBtn = new JButton("게임종료"); // 게임시작 버튼

		   AgainBtn.setBounds(158, 522, 217, 70);
		   ResultFrame.add(AgainBtn);

		   EndBtn.setBounds(465, 522, 217, 70);
		   ResultFrame.add(EndBtn);

		   // 버튼 배경 이미지 설정
		   AgainBtn.setIcon(new ImageIcon("./image/게임다시버튼.png"));
		   EndBtn.setIcon(new ImageIcon("./image/게임종료버튼.png"));

		   AgainBtn.addActionListener(new ActionListener() { // 게임다시버튼을 눌렀을 때
		      @Override
		      public void actionPerformed(ActionEvent e) {
		         ResultFrame.dispose(); // 현재 창을 닫음
		         PlayFrame.dispose();
		         new Game(); // 게임진행화면 불러오기
		      }
		   });

		   EndBtn.addActionListener(new ActionListener() { // 게임종료버튼을 눌렀을 때
		      @Override
		      public void actionPerformed(ActionEvent e) {
		         System.exit(0); // 프로그램 전체 종료
		      }
		   });

		   ResultFrame.setLayout(null);
		   ResultFrame.setVisible(true);
		   ResultFrame.setResizable(false);
		   ResultFrame.setSize(840, 720);
		   ResultFrame.setLocationRelativeTo(null);
		   ResultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫았을 때 해당 창만 종료
		}

	public void ExplainFrame() { // 게임설명 화면
		JFrame ExplainFrame = new JFrame("게임 설명");

		ImagePanel ExplainFrameImg = new ImagePanel(new ImageIcon("./image/게임설명화면.jpg").getImage());
		ExplainFrame.add(ExplainFrameImg);

		ExplainFrame.setVisible(true);
		ExplainFrame.setResizable(false);
		ExplainFrame.setSize(840, 720);
		ExplainFrame.setLocationRelativeTo(null);
		ExplainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫았을 때 해당 창만 종료
	}

	public static void main(String[] args) {
		new Game();
	}

}

class ImagePanel extends JPanel { // 이미지 클래스 (ImagePanel이 JPanel을 상속받음)
	private Image img;

	public ImagePanel(Image img) {
		this.img = img; // 생성자와 매개변수의 이름이 같기 때문에, this를 사용하여 this의 img는 멤버변수임을 의미
		setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
		setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null))); // 디멘션 객체를 인자로 받아 컴포넌트의 크기 지정
		setLayout(null);
	}

	public void paintComponent(Graphics g) { // 컴포넌트 그리기에 필요한 도구 제공
		g.drawImage(img, 0, 0, null);
	}
}
