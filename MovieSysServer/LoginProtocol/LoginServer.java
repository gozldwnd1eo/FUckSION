package MovieSysServer.LoginProtocol;

import java.net.*;
import java.io.*;
import MovieSysServer.Member.*;

public class LoginServer{
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
	    ServerSocket sSocket = new ServerSocket(3000);
   	    System.out.println("클라이언트 접속 대기중...");
	    Socket socket = sSocket.accept();
	    System.out.println("클라이언트 접속");
		
		// 바이트 배열로 전송할 것이므로 필터 스트림 없이 Input/OutputStream만 사용해도 됨
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

		// 로그인 정보 요청용 프로토콜 객체 생성 및 전송
//		Protocol protocol = new Protocol(Protocol.PT_REQ_LOGIN);
		Protocol protocol = new Protocol();
//	    os.write(protocol.getPacket());

		boolean program_stop = false;

		String id;				//아이디
		String password;		//비밀번호
		String name;			//이름
		String gender;			//성별
		String phoneNum;		//휴대전화번호
		String email;			//이메일
		String birthday;		//생년월일

		String region;			//지역

	    while(true){
//			protocol = new Protocol();			// 새 Protocol 객체 생성 (기본 생성자)
			byte[] buf = protocol.getPacket();	// 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
			is.read(buf);						// 클라이언트로부터 로그인정보 (ID와 PWD) 수신
			int packetType = buf[0];			// 수신 데이터에서 패킷 타입 얻음
			int packetCode = buf[1];			// 수신 데이터에서 패킷 커드 얻음
			protocol.setPacket(packetType,buf);	// 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

			switch(packetType){
				case Protocol.PT_EXIT:			// 프로그램 종료 수신 
					protocol = new Protocol(Protocol.PT_EXIT);
					os.write(protocol.getPacket());
					program_stop = true;
					System.out.println("서버종료");
					break;
				
				case Protocol.PT_RES_LOGIN:		// 로그인 정보 수신 
					System.out.println("클라이언트가 로그인 정보를 보냈습니다");
					id = protocol.getId();
					password = protocol.getPassword();
					MemberDAO mdao= new MemberDAO();
					String result = mdao.loginRequest(id, password);
					if(result.equals("false")){ //로그인 실패
						//로그인 실패창이 뜹니당
						
						protocol = new Protocol(Protocol.PT_REQ_LOGIN);//코드3   다시 로그인 요청
						os.write(protocol.getPacket());
						break;
					}

					else{ // 로그인 성공
						//여기서 관리자인지 고객인지 구분
						

						//코드로 구분해서 전송

						//고객          코드1
						//영화/영화관/내정보 창 띄우기
						if(result.equals("C")){
							
						}
						

						//관리자 		코드2
						//영화관 관리/영화 관리/ 상영 관리/ 통계정보/계좌관리 창 띄우기
						if(result.equals("A")){
							
						}

					}


					System.out.println("로그인 처리 결과 전송");
					os.write(protocol.getPacket());
					break;
					
				case Protocol.PT_RES_SIGNUP :	//회원가입 요청 수신
					id = protocol.getId();
					password = protocol.getPassword();
					if(idCheck(id)==false){		//회원가입할 때 id 중복체크
						protocol = new Protocol(Protocol.PT_REQ_SIGNUP);//중복에 걸려서 다시 회원가입 요청
						os.write(protocol.getPacket());
						break;
					}
					//회원가입 성공
					//dto에 회원들 정보들 넣어줘야 해용
					break;

				case Protocol.PT_RES_LOOKUP :	//조회 요청 수신
				
			
				id = protocol.getId();
				name = protocol.getName();
				email = protocol.getEmail();
				region = protocol.getRegion();

					//id조회  0				
					if(lookupID(name, email)==false){	
						protocol = new Protocol(Protocol.PT_REQ_LOOKUP);//가입된 아이디가 없어서 다시 조회 요청
						os.write(protocol.getPacket());
						break;
					}
					else{ // 아이디 보내줌
						

					}
					
					//비밀번호 조회 1

					if(lookupPWD(id,name,email)==false){
						protocol = new Protocol(Protocol.PT_REQ_LOOKUP);
						os.write(protocol.getPacket());
						break;
					}
					else{	//비밀번호 보내줌

					}

					//지역 조회 2


					break;
				
				case Protocol.PT_RES_UPDATE : 	//갱신 요청 수신
					break;

			}//end switch
			if(program_stop) break;

	    }//end while

	    is.close();
	    os.close();
	    socket.close();
	}

	public static boolean idCheck(String id){			//회원가입할 때 id중복확인/////////////
		//중복이면 false 아니면 true



		return true;
	}

	public static boolean lookupID(String name, String email){
		// if(idCheck(name)==false){
		// 	if()
		// }
		// else{
		// 	return false;
		// }
		
		//dao에 이름과 이메일을 받아서 id를 select 하는 방식으로 해야합니당


		//있으면 true 없으면 false
		return true;
	}

	public static boolean lookupPWD(String ID, String name, String email){

		//dao에 아이디와 이름과 이메일을 받아서 비밀번호를 select하기

		//있으면 true 없으면 false
		return true;
	}
}

