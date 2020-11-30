package MovieSysServer.LoginProtocol;

import java.net.*;
import java.io.*;
import MovieSysServer.Member.*;
import MovieSysServer.Film.*;

public class LoginServer {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		ServerSocket sSocket = new ServerSocket(7127);
		System.out.println("클라이언트 접속 대기중...");
		Socket socket = sSocket.accept();
		System.out.println("클라이언트 접속");

		// 바이트 배열로 전송할 것이므로 필터 스트림 없이 Input/OutputStream만 사용해도 됨
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		// 로그인 정보 요청용 프로토콜 객체 생성 및 전송
		// Protocol protocol = new Protocol(Protocol.PT_REQ_LOGIN);
		// Protocol protocol = new Protocol();
		// os.write(protocol.getPacket());

		boolean program_stop = false;

		String[] idpw;
		String id; // 아이디
		String password; // 비밀번호
		String name; // 이름
		String gender; // 성별
		String phoneNum; // 휴대전화번호
		String email; // 이메일
		String birthday; // 생년월일


		String area; // 지역

		String filmID;	//영화 아이디

		while (true) {
			MemberDAO mdao = new MemberDAO();
			Protocol protocol = new Protocol(); // 새 Protocol 객체 생성 (기본 생성자)
			byte[] buf = protocol.getPacket(); // 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
			is.read(buf); // 클라이언트로부터 로그인정보 (ID와 PWD) 수신
			int packetType = buf[0]; // 수신 데이터에서 패킷 타입 얻음
			int packetCode = buf[1]; // 수신 데이터에서 패킷 코드 얻음
			protocol.setPacket(packetType, packetCode, buf); // 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

			switch (packetType) {
				case Protocol.PT_EXIT: // 프로그램 종료 수신
					protocol = new Protocol(Protocol.PT_EXIT);
					os.write(protocol.getPacket());
					program_stop = true;
					System.out.println("서버종료");
					break;

				case Protocol.PT_REQ_LOGIN: // 로그인 정보 수신
					System.out.println("클라이언트가 로그인 정보를 보냈습니다");
					idpw = protocol.getID_Password();
					id = idpw[0];
					password = idpw[1];

					String result = mdao.loginRequest(id, password);
					if (result.equals("false")) { // 로그인 실패
						// 로그인 실패창이 클라이언트에서 뜨게해줌

						protocol = new Protocol(Protocol.PT_RES_LOGIN, Protocol.CODE_PT_RES_LOGIN_FAIL);// 코드3 다시 로그인 요청
						os.write(protocol.getPacket());
						break;
					}

					else { // 로그인 성공
							// 여기서 관리자인지 고객인지 구분

						// 코드로 구분해서 전송

						// 고객 코드1
						// 영화/영화관/내정보 창 띄우기
						if (result.equals("C")) {
							protocol = new Protocol(Protocol.PT_RES_LOGIN, Protocol.CODE_PT_RES_LOGIN_CUS_SUCCESS);// 코드1
																													// 고객
																													// 로그인
																													// 요청
							os.write(protocol.getPacket());
							break;
						}

						// 관리자 코드2
						// 영화관 관리/영화 관리/ 상영 관리/ 통계정보/계좌관리 창 띄우기
						else if (result.equals("A")) {
							protocol = new Protocol(Protocol.PT_RES_LOGIN, Protocol.CODE_PT_RES_LOGIN_ADMIN_SUCCESS);// 코드2
																														// 관리자
																														// 로그인
																														// 요청
							os.write(protocol.getPacket());
							break;
						}

					}

					// System.out.println("로그인 처리 결과 전송");
					// os.write(protocol.getPacket());
					// break;
					// case Protocol.PT_REQ_SIGNUP : //회원가입 요청 수신
					// // id = protocol.getId();
					// // password = protocol.getPassword();
					// // if(idCheck(id)==false){ //회원가입할 때 id 중복체크
					// // protocol = new Protocol(Protocol.PT_RES_SIGNUP);//중복에 걸려서 다시 회원가입 요청
					// // os.write(protocol.getPacket());
					// // break;
					// // }
					// //회원가입 성공
					// //dto에 회원들 정보들 넣어줘야 해용
					// break;

					// 여부터 다시 시작하셈
					 case Protocol.PT_REQ_LOOKUP : //조회 요청 수신

					 //id = protocol.getID();

					// // name = protocol.getName();
					// // email = protocol.getEmail();
					// // region = protocol.getRegion();

					 switch(packetCode){
					 case Protocol.CODE_PT_REQ_LOOKUP_FIND_CUS_ID : //ID조회
					 String[] name_email = protocol.getName_Email();
					 name = name_email[0];
					 email = name_email[1];
					 String memberID = mdao.selectMemberID(name, email);

					 if(memberID.equals("false")){
					 protocol = new Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_ID_NO);
					 //가입된
					// 아이디가 없어서 다시 조회 요청
					 os.write(protocol.getPacket()); //코드 2
					 break;
					 }
					 else{ // 아이디 보내줌
					 protocol = new
					 Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_ID_OK);
					// // 코드 1
					 protocol.setID(memberID);
					 os.write(protocol.getPacket());
					 break;

					 }

					// //비밀번호 조회 1
					 case Protocol.CODE_PT_REQ_LOOKUP_FIND_CUS_PASSWORD : //비밀번호 조회
					 String[] id_name_email = protocol.getID_Name_Email();
					 id = id_name_email[0];
					 name = id_name_email[1];
					 email = id_name_email[2];

					 password = mdao.selectMemberPassword(id, name, email);

					 if(password.equals("false")){
					 protocol = new Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_NO);
					 os.write(protocol.getPacket());
					 break;
					 }
					 else{ //비밀번호 보내줌
					protocol =new Protocol(Protocol.PT_RES_LOOKUP,Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_OK);
					protocol.setPassword(password);
					os.write(protocol.getPacket());
					break;
					 }

					// //지역 조회 2
					 ///////////////////////////////////////////////////////////
					case Protocol.CODE_PT_REQ_LOOKUP_AREA :
					 filmID = protocol.getFlimID();
					 break;

					 //영화관 조회 3
					 /////////////////////////////////////////////////////////
					case Protocol.CODE_PT_REQ_LOOKUP_THEATER : 

					break;

					 //상영시간 조회4
					 ////////////////////////////////////////////////////////
					 case Protocol.CODE_PT_REQ_LOOKUP_SCREEN_TIME :

					 break;

					 //모든 영화관 조회 5
					 ///////////////////////////////////////////////////////
					 case Protocol.CODE_PT_REQ_LOOKUP_ALL_THEATER :
					 break;

					 //해당 영화관의 상영시간표 조회 6
					 ///////////////////////////////////////////////////////
					 case Protocol.CODE_PT_REQ_LOOKUP_SCREEN_TABLE :
					 break;
					 
					 // 현재 좌석 상황 조회 요청 7
					 /////////////////////////////////////////////////////////
					 case Protocol.CODE_PT_REQ_LOOKUP_SEAT_SITUATION :
					 break;

					 // 영화의 상세정보 조회 요청 8
					 case Protocol.CODE_PT_REQ_LOOKUP_FILM_DETAIL :
					 
					 break;
					// }

					// break;

					// case Protocol.PT_REQ_UPDATE : //갱신 요청 수신
					// switch(packetCode){
					// case Protocol.CODE_PT_REQ_UPDATE_ADD_MEM : //회원 추가(가입) 요청

					// if(idCheck(id)==false){ //회원가입할 때 id 중복체크
					// protocol = new
					// Protocol(Protocol.PT_RES_SIGNUP,Protocol.CODE_PT_RES_UPDATE_ADD_MEM_NO);//중복에
					// 걸려서 다시 회원가입 요청
					// os.write(protocol.getPacket());
					// break;
					// }
					// else{
					// CustomerDTO dto= new CustomerDTO();

					// dto.setCus_id(protocol.getID());
					// dto.setCus_password(protocol.getPassword());
					// // dto.setCus_name();
					// //~~~~
					// //~
					// mdao.insertCustomer(dto);

					// protocol= new
					// Protocol(Protocol.PT_RES_SIGNUP,Protocol.CODE_PT_RES_UPDATE_ADD_MEM_OK);
					// os.write(protocol.getPacket());

					// break;
					// }
					// }
					

			}// end switch
			if (program_stop)
				break;

		} // end while

		is.close();
		os.close();
		socket.close();}

	}

	public static boolean idCheck(String id) { // 회원가입할 때 id중복확인/////////////
		// 중복이면 false 아니면 true

		return true;
	}

	public static boolean lookupID(String name, String email) {
		// if(idCheck(name)==false){
		// if()
		// }
		// else{
		// return false;
		// }

		// dao에 이름과 이메일을 받아서 id를 select 하는 방식으로 해야합니당

		// 있으면 true 없으면 false
		return true;
	}

	public static boolean lookupPWD(String ID, String name, String email) {

		// dao에 아이디와 이름과 이메일을 받아서 비밀번호를 select하기

		// 있으면 true 없으면 false
		return true;
	}
}
