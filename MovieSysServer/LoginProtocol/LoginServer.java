package MovieSysServer.LoginProtocol;

import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import MovieSysServer.Member.*;
import MovieSysServer.Film.*;
import MovieSysServer.Cinema.*;

public class LoginServer {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		ServerSocket sSocket = new ServerSocket(7127);
		System.out.println("클라이언트 접속 대기중...");
		Socket socket = sSocket.accept();
		System.out.println("클라이언트 접속");

		// 바이트 배열로 전송할 것이므로 필터 스트림 없이 Input/OutputStream만 사용해도 됨
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		boolean program_stop = false;

		String[] idpw;
		String id; // 아이디
		String password; // 비밀번호
		String name; // 이름
		String gender; // 성별
		String phone; // 휴대전화번호
		String account; // 계좌번호
		String money; // 잔액
		String flag; // 고객 담당자 구분
		String email; // 이메일
		String birthday; // 생년월일

		String cid = ""; // 고객 아이디 로그인 후 저장될 아이디

		String area; // 지역

		String filmID; // 영화 아이디

		String screenID; // 상영영화 아이디
		String residualSeat; // 잔여좌석수
		String startTime; // 시작시각
		String finalTime; // 종료시각

		String audiID; // 상영관 아이디
		String audiNum; // 상영관 번호
		String seatcnt; // 총좌석수

		String theaterID; // 영화관 아이디
		String theaterName; // 영화관명
		String address; // 영화관 주소

		String resvnum; // 예매번호
		String seatNum; // 좌석번호
		String peopleNum; // 인원수(예약)
		String amount; // 금액

		String reviewID; // 게시물 아이디
		String REVcontent; // 리뷰내용
		String starpoint; // 별점

		while (true) {
			MemberDAO mdao = new MemberDAO();
			CustomerDTO cdto = new CustomerDTO();

			CinemaDAO cinemadao = new CinemaDAO();
			ScreenDTO screendto = new ScreenDTO();
			AuditoriumDTO audidto = new AuditoriumDTO();
			TheaterDTO theaterdto = new TheaterDTO();
			ResvDTO resvdto = new ResvDTO();

			FilmDAO fdao = new FilmDAO();
			FilmDTO filmdto = new FilmDTO();
			ReviewDTO reviewdto = new ReviewDTO();

			Protocol protocol = new Protocol(); // 새 Protocol 객체 생성 (기본 생성자)
			byte[] buf = protocol.getPacket(); // 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨

			ArrayList<Protocol> packetList = new ArrayList<Protocol>();// 분할된 패킷의 리스트
			Iterator<Protocol> iterator; // 분할된패킷리스트에 사용될 iterator
			int count = 0; // 패킷리스트의 접근 인덱스

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
					cid = idpw[0];
					password = idpw[1];

					String result = mdao.loginRequest(cid, password);
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

				case Protocol.PT_REQ_LOOKUP: // 조회 요청 수신

					switch (packetCode) {
						case Protocol.CODE_PT_REQ_LOOKUP_FIND_CUS_ID: // ID조회
							String[] name_email = protocol.getName_Email();
							name = name_email[0];
							email = name_email[1];
							String memberID = mdao.selectMemberID(name, email);

							if (memberID.equals("false")) {
								protocol = new Protocol(Protocol.PT_RES_LOOKUP,
										Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_ID_NO);
								// 가입된
								// 아이디가 없어서 다시 조회 요청
								os.write(protocol.getPacket()); // 코드 2
								break;
							} else { // 아이디 보내줌
								protocol = new Protocol(Protocol.PT_RES_LOOKUP,
										Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_ID_OK);
								// // 코드 1
								protocol.setID(memberID);
								os.write(protocol.getPacket());
								break;

							}

							// //비밀번호 조회 1
						case Protocol.CODE_PT_REQ_LOOKUP_FIND_CUS_PASSWORD: // 비밀번호 조회
							String[] id_name_email = protocol.getID_Name_Email();
							id = id_name_email[0];
							name = id_name_email[1];
							email = id_name_email[2];

							boolean resultpassword = mdao.selectMemberPassword(id, name, email);

							if (resultpassword == false) {
								protocol = new Protocol(Protocol.PT_RES_LOOKUP,
										Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_NO);
								os.write(protocol.getPacket());
								break;
							} else { // 비밀번호 재설정 요청
								protocol = new Protocol(Protocol.PT_RES_LOOKUP,
										Protocol.CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_OK);
								os.write(protocol.getPacket());
								break;
							}

							// //지역 조회 2
							///////////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_AREA:
							filmID = protocol.getFlimID();
							String areaResult = fdao.displayArea(filmID);
							

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_AREA_OK);
					
							protocol.setList(areaResult);
							os.write(protocol.getPacket());

							break;

						// 영화관 조회 3
						/////////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_THEATER:
							String[] area_filmID = protocol.getTheaterArea_FlimID();
							area = area_filmID[0];
							filmID = area_filmID[1];
							String theaterresult = cinemadao.displayTheaterByArea(area, filmID);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_THEATER_OK);
							protocol.setList(theaterresult);
							os.write(protocol.getPacket());
							break;

						// 상영시간 조회4
						////////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_SCREEN_TIME:
							String[] theaterID_filmID = protocol.getTheaterID_FlimID();
							theaterID = theaterID_filmID[0];
							filmID = theaterID_filmID[1];

							String screenResult = cinemadao.displayScreen(filmID, theaterID);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_SCREEN_TIME_OK);
							protocol.setList(screenResult);
							os.write(protocol.getPacket());

							break;

						// 모든 영화관 조회 5
						///////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_ALL_THEATER:
							String theaterResult = cinemadao.displayTheater();
							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_ALL_THEATER_OK);
							protocol.setList(theaterResult);
							os.write(protocol.getPacket());
							break;

						// 해당 영화관의 상영시간표 조회 6
						///////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_SCREEN_TABLE:
							theaterID = protocol.getTheaterID();

							String screenresult = cinemadao.displayScreenTable(theaterID);
							protocol = new Protocol(Protocol.PT_RES_LOOKUP,
									Protocol.CODE_PT_RES_LOOKUP_SCREEN_TABLE_OK);
							protocol.setList(screenresult);
							os.write(protocol.getPacket());

							break;

						// 현재 좌석 상황 조회 요청 7
						/////////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_SEAT_SITUATION:
							screenID = protocol.getScreenID();

							String seatsituationResult = cinemadao.displaySeatSituation(screenID);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP,
									Protocol.CODE_PT_RES_LOOKUP_SEAT_SITUATION_OK);
							protocol.setList(seatsituationResult);
							os.write(protocol.getPacket());
							break;

						// 영화의 상세정보 조회 요청 8
						///////////////////////////////////////////////////////
						case Protocol.CODE_PT_REQ_LOOKUP_FILM_DETAIL:
							System.out.println("영화상세정보 요청 받음");

							filmID = protocol.getFlimID();

							String filmDetailResult = fdao.displayMovieDetail(filmID);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_FILM_DETAIL_OK);
							packetList = protocol.setList(filmDetailResult);

							iterator = packetList.iterator();
							while (iterator.hasNext()) {
								Protocol temp = iterator.next();
								temp = packetList.get(count++);
								os.write(temp.getPacket());
							}
							System.out.println("영화상세정보 요청에 대한 응답 보냄");
							break;

						

						// 내 정보 조회 요청 9
						case Protocol.CODE_PT_REQ_LOOKUP_MY_INFO:
							id = protocol.getID();

							String myInfoResult = mdao.displayMyInfo(id);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_MY_INFO_OK);
							protocol.setList(myInfoResult);
							os.write(protocol.getPacket());
							break;

						// 자신이 작성한 리뷰 리스트 조회 10
						case Protocol.CODE_PT_REQ_LOOKUP_MY_REVIEWS:
							id = protocol.getID();

							String myreviewResult = fdao.displayReviewDetail(id);
						

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_MY_REVIEWS_OK);
							protocol.setList(myreviewResult);
							os.write(protocol.getPacket());
							break;

						// 예매 내역 조회 요청 11
						case Protocol.CODE_PT_REQ_LOOKUP_RESV_LIST:
							id = protocol.getID();

							String resvResult = cinemadao.displayResvList(id);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_RESV_LIST_OK);
							protocol.setList(resvResult);
							os.write(protocol.getPacket());
							break;

						// 현재 상영 중 영화 조회 요청 12
						case Protocol.CODE_PT_REQ_LOOKUP_ALL_SCREEN:

							System.out.println("현재상영조회요청 받음");

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_ALL_SCREEN_OK);

							String filmResult = fdao.displayScreenList();
							packetList = protocol.setScreenList(filmResult);

							iterator = packetList.iterator();
							while (iterator.hasNext()) {
								Protocol temp = iterator.next();
								temp = packetList.get(count++);
								os.write(temp.getPacket());
							}
							System.out.println("현재상영조회요청에 대한 응답 보냄");
							break;

						// 담당자용 영화관 조회 요청 13
						case Protocol.CODE_PT_REQ_LOOKUP_THEATER_FOR_ADMIN:
							id = protocol.getID();

							String theateradminResult = cinemadao.displayTheaterForAdmin(id);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP,
									Protocol.CODE_PT_RES_LOOKUP_THEATER_FOR_ADMIN_OK);
							protocol.setList(theateradminResult);
							os.write(protocol.getPacket());
							break;

						// 상영관 조회 요청 14
						case Protocol.CODE_PT_REQ_LOOKUP_AUDI:
							theaterID = protocol.getTheaterID();

							String audiResult = cinemadao.displayAuditorium(theaterID);

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_AUDI_OK);
							protocol.setList(audiResult);
							os.write(protocol.getPacket());
							break;

						//영화별 매출 조회 요청 15
						case Protocol.CODE_PT_REQ_LOOKUP_SALES_PER_MOVIE :
						filmID = protocol.getFlimID();

						String salesPerMovieResult = cinemadao.displaySalesPerMovie();

						protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_SALES_PER_MOVIE_OK);
						protocol.setList(salesPerMovieResult);
						os.write(protocol.getPacket());

						break;

						// 총 매출 조회 요청 16
						case Protocol.CODE_PT_REQ_LOOKUP_TOTAL_SALES:
							String allsales = cinemadao.displayAllSales();

							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_TOTAL_SALES_OK);
							protocol.setList(allsales);
							os.write(protocol.getPacket());
							break;

						// 영화별 취소율 조회 요청 17
						case Protocol.CODE_PT_REQ_LOOKUP_THEATER_CANCEL_RATE:
							String cancelrate = cinemadao.displayCancelRatePerMovie();
							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_THEATER_CANCEL_RATE_OK);
							protocol.setList(cancelrate);
							os.write(protocol.getPacket());
							break;

						// 영화별 예매율 조회 요청 18
						case Protocol.CODE_PT_REQ_LOOKUP_THEATER_RESV_RATE:
							String resvrate = cinemadao.displayResvRatePerMovie();
							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_THEATER_RESV_RATE_OK);
							protocol.setList(resvrate);
							os.write(protocol.getPacket());
							break;

						// 계좌 조회 요청 19
						case Protocol.CODE_PT_REQ_LOOKUP_ACCOUNT:
							id = protocol.getID();

							String accountResult = mdao.displayAccountInfo(id);
							protocol = new Protocol(Protocol.PT_RES_LOOKUP, Protocol.CODE_PT_RES_LOOKUP_ACCOUNT_OK);
							protocol.setList(accountResult);
							os.write(protocol.getPacket());
							break;

					}

					break;

				case Protocol.PT_REQ_UPDATE: // 갱신 요청 수신
					switch (packetCode) {
						// 회원 추가(가입) 요청 1
						case Protocol.CODE_PT_REQ_UPDATE_ADD_MEM:
							String[] id_password_name_phone_account_gender_money_email_birthday_flag = protocol
									.getMemberJoin();
							id = id_password_name_phone_account_gender_money_email_birthday_flag[0];
							password = id_password_name_phone_account_gender_money_email_birthday_flag[1];
							name = id_password_name_phone_account_gender_money_email_birthday_flag[2];
							phone = id_password_name_phone_account_gender_money_email_birthday_flag[3];
							account = id_password_name_phone_account_gender_money_email_birthday_flag[4];
							gender = id_password_name_phone_account_gender_money_email_birthday_flag[5];
							money = id_password_name_phone_account_gender_money_email_birthday_flag[6];
							email = id_password_name_phone_account_gender_money_email_birthday_flag[7];
							birthday = id_password_name_phone_account_gender_money_email_birthday_flag[8];
							flag = id_password_name_phone_account_gender_money_email_birthday_flag[9];

							boolean resultIDCheck = mdao.idCheck(id);
							if (resultIDCheck == true) { // 아이디 중복
								protocol = new Protocol(Protocol.PT_RES_UPDATE, Protocol.CODE_PT_RES_UPDATE_ADD_MEM_NO);
								os.write(protocol.getPacket());
								break;
							}

							cdto.setCus_id(id);
							cdto.setCus_password(password);
							cdto.setCus_name(name);
							cdto.setCus_phoneNum(phone);
							cdto.setCus_account(account);
							cdto.setCus_gender(gender);
							cdto.setCus_money(Integer.parseInt(money));
							cdto.setEmail(email);
							cdto.setBirthday(birthday);
							cdto.setFlag(flag);
							boolean signupresult = mdao.insertCustomer(cdto);
							if (signupresult == false) { // 회원가입 실패
								protocol = new Protocol(Protocol.PT_RES_UPDATE, Protocol.CODE_PT_RES_UPDATE_ADD_MEM_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE, Protocol.CODE_PT_RES_UPDATE_ADD_MEM_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 회원 정보 수정 요청 2
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_MEM_INFO:
							String[] password_phone_email_account = protocol.getMember_Modify_Inf();
							password = password_phone_email_account[0];
							phone = password_phone_email_account[1];
							email = password_phone_email_account[2];
							account = password_phone_email_account[3];

							boolean resultMemInfo = mdao.updateMemInfo(cid, password, phone, email, account);
							if (resultMemInfo == false) {// 오류로 인한 실패
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_NO);
								os.write(protocol.getPacket());
								break;
							} else { // 성공
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 회원 삭제 요청 3
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_MEM:
							id = protocol.getID();
							boolean deleteresult = mdao.deleteCustomer(id);
							if (deleteresult == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_MEM_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_MEM_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 비밀번호 재설정 요청 4
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_PASSWORD:
							password = protocol.getChangePwd();
							boolean updateresult = mdao.updateMemPWD(cid, password);
							if (updateresult == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_PASSWORD_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_PASSWORD_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 결제 요청 5(예매)
						case Protocol.CODE_PT_REQ_UPDATE_ADD_PAY_RESV:
							String[] id_screenID_seatNum_peopleNum_amount = protocol.getAdd_Pay_Resv();
							id = id_screenID_seatNum_peopleNum_amount[0];
							screenID = id_screenID_seatNum_peopleNum_amount[1];
							seatNum = id_screenID_seatNum_peopleNum_amount[2];
							peopleNum = id_screenID_seatNum_peopleNum_amount[3];
							amount = id_screenID_seatNum_peopleNum_amount[4];
							resvdto.setCus_id(id);
							resvdto.setScreen_id(screenID);
							resvdto.setResv_seatNum(seatNum);
							resvdto.setResv_peopleNum(Integer.parseInt(peopleNum));
							resvdto.setResv_depositAmount(Integer.parseInt(amount));
							boolean insertresult = cinemadao.insertResvation(resvdto);
							if (insertresult == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_PAY_RESV_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 예매 취소 요청 6
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_PAY_RESV:
							String[] id_resvnum = protocol.getDel_Pay_Resv();
							id = id_resvnum[0];
							resvnum = id_resvnum[1];
							boolean deleteresvresult = cinemadao.deleteResv(id, resvnum);
							if (deleteresvresult == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_PAY_RESV_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_PAY_RESV_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 리뷰 추가 요청 7
						case Protocol.CODE_PT_REQ_UPDATE_ADD_REVIEW:

							String[] id_filmID_REVcontent_starpoint = protocol.getAdd_Review();
							id = id_filmID_REVcontent_starpoint[0];
							filmID = id_filmID_REVcontent_starpoint[1];
							REVcontent = id_filmID_REVcontent_starpoint[2];
							starpoint = id_filmID_REVcontent_starpoint[3];

							reviewdto.setCus_id(id);
							reviewdto.setFilm_id(filmID);
							reviewdto.setRev_content(REVcontent);
							reviewdto.setRev_starPoint(Integer.parseInt(starpoint));
							boolean insertreviewresult = fdao.insertReview(reviewdto);
							if (insertreviewresult) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_REVIEW_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_REVIEW_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 리뷰 수정 요청 8
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_REVIEW:
							String[] reviewID_REVcontent_starpoint = protocol.getChange_Review();
							reviewID = reviewID_REVcontent_starpoint[0];
							REVcontent = reviewID_REVcontent_starpoint[1];
							starpoint = reviewID_REVcontent_starpoint[2];

							boolean updatereviewresult = true;/////////////// fafdsadfssdfsd미완성
							if (updatereviewresult == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_REVIEW_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_REVIEW_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 리뷰 삭제 요청 9
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_REVIEW:
							reviewID = protocol.getDel_Review();
							boolean deletereviewResult = fdao.deleteReview(reviewID);
							if (deletereviewResult == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_REVIEW_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_REVIEW_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 영화관 추가 10
						case Protocol.CODE_PT_REQ_UPDATE_ADD_THEATER:
							String[] id_theatername_area_address = protocol.getAdd_Theater();
							id = id_theatername_area_address[0];
							theaterName = id_theatername_area_address[1];
							area = id_theatername_area_address[2];
							address = id_theatername_area_address[3];

							theaterdto.setAd_id(id);
							theaterdto.setTheater_name(theaterName);
							theaterdto.setTheater_area(area);
							theaterdto.setTheater_address(address);
							boolean insertTheater = cinemadao.insertTheater(theaterdto);
							if (insertTheater == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_REVIEW_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_REVIEW_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 영화관 정보 변경 11
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_THEATER:
							String[] theaterID_id_theaterName_area_address = protocol.getChange_Theater();
							theaterID = theaterID_id_theaterName_area_address[0];
							id = theaterID_id_theaterName_area_address[1];
							theaterName = theaterID_id_theaterName_area_address[2];
							area = theaterID_id_theaterName_area_address[3];
							address = theaterID_id_theaterName_area_address[4];

							theaterdto.setTheater_name(theaterName);
							theaterdto.setTheater_area(area);
							theaterdto.setTheater_address(address);
							theaterdto.setTheater_id(theaterID);
							theaterdto.setAd_id(id);

							boolean updateTheater = cinemadao.updateTheater(theaterdto);
							if (updateTheater == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_THEATER_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_THEATER_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 영화관 삭제 12
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_THEATER:
							theaterID = protocol.getDel_Theater();
							boolean deleteTheater = cinemadao.deleteTheater(theaterID);
							if (deleteTheater == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_THEATER_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_THEATER_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 상영관 추가 13
						case Protocol.CODE_PT_REQ_UPDATE_ADD_AUDI:
							String[] audiNum_theaterID_seatcnt = protocol.getAdd_Audi();
							audiNum = audiNum_theaterID_seatcnt[0];
							theaterID = audiNum_theaterID_seatcnt[1];
							seatcnt = audiNum_theaterID_seatcnt[2];

							audidto.setAudi_num(Integer.parseInt(audiNum));
							audidto.setTheater_id(theaterID);
							audidto.setAudi_seatCnt(Integer.parseInt(seatcnt));

							boolean insertAudi = cinemadao.insertAuditorium(audidto);
							if (insertAudi == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_AUDI_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_AUDI_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 상영관 변경 14
							
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_AUDI:
							String[] audiID_audiNum_theaterID_seatcnt = protocol.getChange_Audi();
							audiID = audiID_audiNum_theaterID_seatcnt[0];
							audiNum = audiID_audiNum_theaterID_seatcnt[1];
							theaterID = audiID_audiNum_theaterID_seatcnt[2];
							seatcnt = audiID_audiNum_theaterID_seatcnt[3];

							audidto.setAudi_id(audiID);
							audidto.setAudi_num(Integer.parseInt(audiNum));
							audidto.setTheater_id(theaterID);
							audidto.setAudi_seatCnt(Integer.parseInt(audiNum));

							boolean updateAudi = cinemadao.updateAudi(audidto);
							if (updateAudi == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_AUDI_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_AUDI_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 상영관 삭제 15
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_AUDI:
							audiID = protocol.getDel_Audi();
							boolean deleteAudiID = cinemadao.deleteAuditorium(audiID);
							if (deleteAudiID == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_AUDI_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_AUDI_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 상영스케줄 추가 16
						case Protocol.CODE_PT_REQ_UPDATE_ADD_SCREEN_TABLE:
							String[] audiID_filmID_residualSeat_startTime_finalTime = protocol.getAdd_Screen();
							audiID = audiID_filmID_residualSeat_startTime_finalTime[0];
							filmID = audiID_filmID_residualSeat_startTime_finalTime[1];
							residualSeat = audiID_filmID_residualSeat_startTime_finalTime[2];
							startTime = audiID_filmID_residualSeat_startTime_finalTime[3];
							finalTime = audiID_filmID_residualSeat_startTime_finalTime[4];

							screendto.setAudi_id(audiID);
							screendto.setFilm_id(filmID);
							screendto.setScreen_residualSeat(Integer.parseInt(residualSeat));
							screendto.setScreen_startTime(startTime);
							screendto.setScreen_finalTime(finalTime);

							boolean insertScreen = cinemadao.insertScreen(screendto);
							if (insertScreen == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_SCREEN_TABLE_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_ADD_SCREEN_TABLE_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 상영스케줄 변경 17
							
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_SCREEN_TABLE:
							String[] screenID_audiID_filmID_residualSeat_startTime_finalTime = protocol
									.getChange_Screen();
							screenID = screenID_audiID_filmID_residualSeat_startTime_finalTime[0];
							audiID = screenID_audiID_filmID_residualSeat_startTime_finalTime[1];
							filmID = screenID_audiID_filmID_residualSeat_startTime_finalTime[2];
							residualSeat = screenID_audiID_filmID_residualSeat_startTime_finalTime[3];
							startTime = screenID_audiID_filmID_residualSeat_startTime_finalTime[4];
							finalTime = screenID_audiID_filmID_residualSeat_startTime_finalTime[5];

							screendto.setAudi_id(audiID);
							screendto.setFilm_id(filmID);
							screendto.setScreen_residualSeat(Integer.parseInt(residualSeat));
							screendto.setScreen_startTime(startTime);
							screendto.setScreen_finalTime(finalTime);
							screendto.setScreen_id(screenID);

							boolean updateScreen = cinemadao.updateScreen(screendto);
							if (updateScreen == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_SCREEN_TABLE_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_CHANGE_SCREEN_TABLE_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 상영영화 삭제 18
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_SCREEN_TABLE:
							screenID = protocol.getDel_Screen();
							boolean deleteScreen = cinemadao.deleteScreen(screenID);
							if (deleteScreen == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_SCREEN_TABLE_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_SCREEN_TABLE_OK);
								os.write(protocol.getPacket());
								break;
							}

							// 영화 추가 19
						case Protocol.CODE_PT_REQ_UPDATE_ADD_FILM:
							protocol = new Protocol(protocol.PT_RES_UPDATE,protocol.CODE_PT_REQ_UPDATE_ADD_FILM);
							protocol.setList(list)
							break;

						// 영화 수정 20
						case Protocol.CODE_PT_REQ_UPDATE_CHANGE_FILM:
							break;

						// 영화 삭제 21
						case Protocol.CODE_PT_REQ_UPDATE_DELETE_FILM:
							filmID = protocol.getDel_Film();
							boolean deleteFilm = fdao.deleteFilm(filmID);
							if (deleteFilm == false) {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_FILM_NO);
								os.write(protocol.getPacket());
								break;
							} else {
								protocol = new Protocol(Protocol.PT_RES_UPDATE,
										Protocol.CODE_PT_RES_UPDATE_DELETE_FILM_OK);
								os.write(protocol.getPacket());
								break;
							}
					}

			}// end switch
			if (program_stop)
				break;

		} // end while

		is.close();
		os.close();
		socket.close();

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
