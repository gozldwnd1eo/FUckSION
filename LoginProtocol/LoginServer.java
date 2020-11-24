package LoginProtocol;

import java.net.*;
import java.io.*;

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
		Protocol protocol = new Protocol(Protocol.PT_REQ_LOGIN);
	    os.write(protocol.getPacket());

		boolean program_stop = false;

	    while(true){
			protocol = new Protocol();			// 새 Protocol 객체 생성 (기본 생성자)
			byte[] buf = protocol.getPacket();	// 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
			is.read(buf);						// 클라이언트로부터 로그인정보 (ID와 PWD) 수신
			int packetType = buf[0];			// 수신 데이터에서 패킷 타입 얻음
			protocol.setPacket(packetType,buf);	// 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

			switch(packetType){
				case Protocol.PT_EXIT:			// 프로그램 종료 수신 
					protocol = new Protocol(Protocol.PT_EXIT);
					os.write(protocol.getPacket());
					program_stop = true;
					System.out.println("서버종료");
					break;
				
				case Protocol.PT_RES_LOGIN:		// 로그인 결과 수신
					System.out.println("클라이언트가 로그인 결과를 보냈습니다");
					String result = protocol.getLoginResult();
					if(result.equals("1")){ // 고객 인증 성공

					}
					else if(result.equals("2")){ // 담당자 인증 성공

					}
					else if(result.equals("3")){ // 인증 실패

					}
					else if(result.equals("4")){ // 5회 인증 실패

					}
					
					// String id = protocol.getId();
					// String password = protocol.getPassword();
					// System.out.println(id+password);

// 					if(id.equals("")){	//id
// 						if(password.equals("")){	//로그인 성공        비밀번호
// //							protocol = new Protocol(Protocol.PT_LOGIN_RESULT);
// 							protocol.setLoginResult("1");
// 							System.out.println("로그인 성공");
// 						 }else{	//암호 틀림
// //							protocol = new Protocol(Protocol.PT_LOGIN_RESULT);
// 							protocol.setLoginResult("2");
// 							System.out.println("암호 틀림");
// 						 }
// 					}else{	//아이디 존재 안함
// //						protocol = 	new Protocol(Protocol.PT_LOGIN_RESULT);
// 						protocol.setLoginResult("3");
// 						System.out.println("아이디 존재안함");
// 					}

					System.out.println("로그인 처리 결과 전송");
					os.write(protocol.getPacket());
					break;
				case Protocol.PT_RES_SIGNUP : 
					break;
				case Protocol.PT_RES_LOOKUP :
					break;
				case Protocol.PT_RES_UPDATE :
					break;
			}//end switch
			if(program_stop) break;

	    }//end while

	    is.close();
	    os.close();
	    socket.close();
	}
}
