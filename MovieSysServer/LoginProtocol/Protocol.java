package MovieSysServer.LoginProtocol;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Protocol implements Serializable {
	// 프로토콜 타입에 관한 변수
	public static final int PT_UNDEFINED = -1; // 프로토콜이 지정되어 있지 않은 경우
	public static final int PT_EXIT = 0; // 프로그램 종료
	public static final int PT_REQ_LOGIN = 1; // 로그인 요청
	public static final int PT_RES_LOGIN = 2; // 로그인 응답
	// public static final int PT_LOGIN_RESULT=3; // 인증 결과
	public static final int PT_REQ_SIGNUP = 3; // 회원가입 요청
	public static final int PT_RES_SIGNUP = 4; // 회원가입 응답
	public static final int PT_REQ_LOOKUP = 5; // 조회 요청
	public static final int PT_RES_LOOKUP = 6; // 조회 응답
	public static final int PT_REQ_UPDATE = 7; // 갱신 요청
	public static final int PT_RES_UPDATE = 8; // 갱신 응답
	public static final int LEN_LOGIN_ID = 10; // ID 길이
	public static final int LEN_LOGIN_PASSWORD = 20; // PWD 길이
	public static final int LEN_LOGIN_RESULT = 2; // 로그인 인증 값 길이
	public static final int LEN_PROTOCOL_TYPE = 1; // 프로토콜 타입 길이
	public static final int LEN_TYPE_CODE = 2; // 타입 코드 길이
	public static final int LEN_PROTOCOL_BODYLEN = 2; // 프로토콜 바디 길이 길이
	public static final int LEN_PROTOCOL_FRAG = 1; // 프로토콜 분할여부 길이
	public static final int LEN_PROTOCOL_LAST = 1; // 프로토콜 마지막여부 길이
	public static final int LEN_PROTOCOL_SEQNUM = 2; // 프로토콜 분할 순서번호 길이
	public static final int LEN_MAX = 1000; // 최대 데이터 길이
	public static final int LEN_BODY_SEPARATOR = 1; // 바디 구분자 길이
	public static final int LEN_TOTAL_SALES = 4; // 총 매출 길이
	public static final int LEN_CANCEL_RATE = 10; // 취소율 길이(?????얼만지 몰라요 )
	// MEMBERS TABLE
	public static final int LEN_MEM_NAME = 20; // 멤버 이름 길이
	public static final int LEN_MEM_PHONENUM = 20; // 멤버 전화번호 길이
	public static final int LEN_MEM_ACCOUNT = 50; // 멤버 계좌번호 길이
	public static final int LEN_MEM_GENDER = 1; // 멤버 성별 길이
	public static final int LEN_MEM_MONEY = 4; // 멤버 잔액 길이
	public static final int LEN_MEM_EMAIL = 50; // 멤버 이메일 길이
	public static final int LEN_MEM_BIRTHDAY = 10; // 멤버 생일 길이
	public static final int LEN_MEM_FLAG = 1; // 멤버 구분 길이
	// THEATERS TABLE
	public static final int LEN_THEATER_ID = 5; // 영화관 ID 길이
	public static final int LEN_THEATER_NAME = 50; // 영화관 이름 길이
	public static final int LEN_THEATER_AREA = 10; // 영화관 지역 길이
	public static final int LEN_THEATER_ADDRESS = 100; // 영화관 주소 길이
	// AUDITORIUMS TABLE
	public static final int LEN_AUDI_ID = 5; // 상영관 ID 길이
	public static final int LEN_AUDI_NUM = 2; // 상영관 번호 길이
	public static final int LEN_AUDI_SEATCNT = 4; // 상영관 좌석수 길이
	// FILM TABLE
	public static final int LEN_FILM_ID = 5; // 영화 ID 길이
	public static final int LEN_FILM_NAME = 50; // 영화 이름 길이
	public static final int LEN_FILM_TEASER = 100; // 영화 예고편 영상 링크 길이
	public static final int LEN_FILM_INFO = 50; // 영화 정보 길이
	public static final int LEN_FILM_GENRE = 20; // 영화 장르 길이
	public static final int LEN_FILM_OPENINGDATE = 20; // 영화 개봉일 길이
	// public static final int LEN_FILM_SUMMARY=?; // 영화 줄거리 길이
	// public static final int LEN_FILM_POSTER=?; // 영화 포스터 길이
	public static final int LEN_FILM_RESVDATE = 4; // 영화 예매율 길이
	// RESV TABLE
	public static final int LEN_RESV_NUM = 10; // 영화 예매번호 길이
	public static final int LEN_RESV_PEOPLENUM = 1; // 예매 인원수 길이
	public static final int LEN_RESV_SEATNUM = 50; // 예매 좌석번호 길이
	public static final int LEN_RESV_DEPOSITDATE = 20; // 예매 입금일 길이
	public static final int LEN_RESV_DEPOSITAMOUNT = 4; // 예매 입금액 길이
	public static final int LEN_RESV_CANCELDATE = 20; // 예매 취소일 길이
	// SCREEN TABLE
	public static final int LEN_SCREEN_ID = 5; // 상영 영화 ID 길이
	public static final int LEN_SCREEN_RESIDUALSEAT = 4; // 상영영화 잔여좌석수 길이
	public static final int LEN_SCREEN_STARTTIME = 20; // 상영영화 시작시간 길이
	public static final int LEN_SCREEN_FINALTIME = 20; // 상영영화 끝나는 시간 길이
	// REVIEW TABLE
	public static final int LEN_REV_ID = 10; // 리뷰 ID 길이
	public static final int LEN_REV_STARPOINT = 3; // 리뷰 별점 길이
	public static final int LEN_REV_CONTENT = 220; // 리뷰 내용 길이
	public static final int LEN_REV_DATE = 20; // 리뷰 게시날짜 길이
	// TYPE 1 CODE
	public static final int CODE_PT_RES_LOGIN_CUS_SUCCESS = 1; // 고객 아이디 찾기 요청 코드번호
	public static final int CODE_PT_RES_LOGIN_ADMIN_SUCCESS = 2; // 고객 아이디 찾기 요청 코드번호
	public static final int CODE_PT_RES_LOGIN_FAIL = 3; // 고객 아이디 찾기 요청 코드번호
	// TYPE 5 CODE
	public static final int CODE_PT_REQ_LOOKUP_FIND_CUS_ID = 0; // 고객 아이디 찾기 요청 코드번호 //관리자 도 아이디 여기서 찾아내는가?
	public static final int CODE_PT_REQ_LOOKUP_FIND_CUS_PASSWORD = 1; // 고객 비밀번호 찾기 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_AREA = 2; // 지역 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_THEATER = 3; // 영화관 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_SCREEN_TIME = 4; // 상영시간 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_ALL_THEATER = 5; // 모든 영화관 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_SCREEN_TABLE = 6; // 해당 영화관의 상영시간표 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_SEAT_SITUATION = 7; // 현재 좌석 상황 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_FILM_DETAIL = 8; // 영화의 상세정보 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_MY_INFO = 9; // 내 정보 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_MY_REVIEWS = 10; // 자신이 작성한 리뷰 리스트 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_RESV_LIST = 11; // 예매 내역 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_ALL_SCREEN = 12; // 현재 상영 중 영화 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_THEATER_FOR_ADMIN = 13; // 담당자용 영화관 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_AUDI = 14; // 상영관 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_THEATER_SALES = 15; // 영화관별 매출 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_TOTAL_SALES = 16; // 총 매출 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_THEATER_CANCEL_RATE = 17; // 영화별 취소율 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_THEATER_RESV_RATE = 18; // 영화별 예매율 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_ACCOUNT = 19; // 계좌 조회 요청 코드번호
	public static final int CODE_PT_REQ_LOOKUP_ALL_SCREEN_SCHEDULE = 20; // 모든 상영영화 조회 조회 요청 코드번호
	// TYPE 6 CODE
	public static final int CODE_PT_RES_LOOKUP_FIND_CUS_ID_OK = 1; // 고객 아이디 찾기 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_FIND_CUS_ID_NO = 2; // 고객 아이디 찾기 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_OK = 3; // 고객 비밀번호 찾기 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_NO = 4; // 고객 비밀번호 찾기 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_AREA_OK = 33; // 지역 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_AREA_NO = 34; // 지역 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_OK = 5; // 영화관 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_NO = 6; // 영화관 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_SCREEN_TIME_OK = 35; // 상영시간 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_SCREEN_TIME_NO = 36; // 상영시간 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_ALL_THEATER_OK = 37; // 모든 영화관 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_ALL_THEATER_NO = 38; // 모든 영화관 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_SCREEN_TABLE_OK = 39; // 해당 영화관의 상영시간표 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_SCREEN_TABLE_NO = 40; // 해당 영화관의 상영시간표 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_SEAT_SITUATION_OK = 9; // 현재 좌석 상황 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_SEAT_SITUATION_NO = 10; // 현재 좌석 상황 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_FILM_DETAIL_OK = 11; // 영화의 상세정보 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_FILM_DETAIL_NO = 12; // 영화의 상세정보 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_MY_INFO_OK = 13; // 내 정보 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_MY_INFO_NO = 14; // 내 정보 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_MY_REVIEWS_OK = 15; // 자신이 작성한 리뷰 리스트 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_MY_REVIEWS_NO = 16; // 자신이 작성한 리뷰 리스트 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_RESV_LIST_OK = 17; // 예매 내역 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_RESV_LIST_NO = 18; // 예매 내역 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_ALL_SCREEN_OK = 19; // 현재 상영 중 영화 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_ALL_SCREEN_NO = 20; // 현재 상영 중 영화 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_FOR_ADMIN_OK = 21; // 담당자용 영화관 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_FOR_ADMIN_NO = 22; // 담당자용 영화관 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_AUDI_OK = 7; // 상영관 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_AUDI_NO = 8; // 상영관 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_SALES_OK = 23; // 영화관별 매출 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_SALES_NO = 24; // 영화관별 매출 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_TOTAL_SALES_OK = 25; // 총 매출 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_TOTAL_SALES_NO = 26; // 총 매출 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_CANCEL_RATE_OK = 27; // 영화별 취소율 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_CANCEL_RATE_NO = 28; // 영화별 취소율 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_RESV_RATE_OK = 29; // 영화별 예매율 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_THEATER_RESV_RATE_NO = 30; // 영화별 예매율 조회 요청 거절 코드번호
	public static final int CODE_PT_RES_LOOKUP_ACCOUNT_OK = 31; // 계좌 조회 요청 승인 코드번호
	public static final int CODE_PT_RES_LOOKUP_ACCOUNT_NO = 32; // 계좌 조회 요청 거절 코드번호
	public static final int CODE_PT_REQ_LOOKUP_ALL_SCREEN_SCHEDULE_OK = 41; // 모든 상영영화 조회 요청 승인 코드번호
	public static final int CODE_PT_REQ_LOOKUP_ALL_SCREEN_SCHEDULE_NO = 42; // 모든 상영영화 조회 요청 거절 코드번호
	// TYPE 7 CODE
	public static final int CODE_PT_REQ_UPDATE_ADD_MEM = 1; // 회원 추가 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_MEM_INFO = 2; // 회원 정보 수정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_MEM = 3; // 회원 삭제 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_PASSWORD = 4; // 비밀번호 재설정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_ADD_PAY_RESV = 5; // 결제 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_PAY_RESV = 6; // 예매 취소 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_ADD_REVIEW = 7; // 리뷰 추가 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_REVIEW = 8; // 리뷰 수정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_REVIEW = 9; // 리뷰 삭제 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_ADD_THEATER = 10; // 영화관 추가 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_THEATER = 11; // 영화관 수정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_THEATER = 12; // 영화관 삭제 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_ADD_AUDI = 13; // 상영관 추가 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_AUDI = 14; // 상영관 수정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_AUDI = 15; // 상영관 삭제 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_ADD_SCREEN_TABLE = 16; // 상영스케줄 추가 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_SCREEN_TABLE = 17; // 상영스케줄 수정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_SCREEN_TABLE = 18; // 상영스케줄 삭제 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_ADD_FILM = 19; // 영화 추가 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_CHANGE_FILM = 20; // 영화 수정 요청 코드번호
	public static final int CODE_PT_REQ_UPDATE_DELETE_FILM = 21; // 영화 삭제 요청 코드번호
	// TYPE 8 CODE
	public static final int CODE_PT_RES_UPDATE_ADD_MEM_OK = 1; // 회원 추가 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_MEM_NO = 2; // 회원 추가 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_OK = 3; // 회원 정보 수정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_NO = 4; // 회원 정보 수정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_MEM_OK = 5; // 회원 삭제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_MEM_NO = 6; // 회원 삭제 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_PASSWORD_OK = 41; // 비밀번호 재설정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_PASSWORD_NO = 42; // 비밀번호 재설정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_PAY_RESV_OK = 7; // 결제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_PAY_RESV_NO = 8; // 결제 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_PAY_RESV_OK = 15; // 예매 취소 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_PAY_RESV_NO = 16; // 예매 취소 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_REVIEW_OK = 9; // 리뷰 추가 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_REVIEW_NO = 10; // 리뷰 추가 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_REVIEW_OK = 11; // 리뷰 수정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_REVIEW_NO = 12; // 리뷰 수정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_REVIEW_OK = 13; // 리뷰 삭제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_REVIEW_NO = 14; // 리뷰 삭제 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_THEATER_OK = 17; // 영화관 추가 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_THEATER_NO = 18; // 영화관 추가 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_THEATER_OK = 19; // 영화관 수정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_THEATER_NO = 20; // 영화관 수정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_THEATER_OK = 21; // 영화관 삭제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_THEATER_NO = 22; // 영화관 삭제 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_AUDI_OK = 23; // 상영관 추가 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_AUDI_NO = 24; // 상영관 추가 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_AUDI_OK = 25; // 상영관 수정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_AUDI_NO = 26; // 상영관 수정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_AUDI_OK = 27; // 상영관 삭제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_AUDI_NO = 28; // 상영관 삭제 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_SCREEN_TABLE_OK = 29; // 상영스케줄 추가 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_SCREEN_TABLE_NO = 30; // 상영스케줄 추가 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_SCREEN_TABLE_OK = 31; // 상영스케줄 수정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_SCREEN_TABLE_NO = 32; // 상영스케줄 수정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_SCREEN_TABLE_OK = 33; // 상영스케줄 삭제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_SCREEN_TABLE_NO = 34; // 상영스케줄 삭제 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_FILM_OK = 35; // 영화 추가 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_ADD_FILM_NO = 36; // 영화 추가 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_FILM_OK = 37; // 영화 수정 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_CHANGE_FILM_NO = 38; // 영화 수정 요청 거절 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_FILM_OK = 39; // 영화 삭제 요청 승인 코드번호
	public static final int CODE_PT_RES_UPDATE_DELETE_FILM_NO = 40; // 영화 삭제 요청 거절 코드번호
	protected int protocolType;
	protected int protocolCode = -1; // 코드 추가
	protected int protocolBodyLen = 0;
	protected int protocolFlag = 0; // 0 == false 1==true
	protected int protocolLast = 0; // 0 == false 1==true
	protected int protocolSeqNum = 0;

	private byte[] packet; // 프로토콜과 데이터의 저장공간이 되는 바이트 배열

	public Protocol() { // 생성자
		this(PT_UNDEFINED);
	}

	public Protocol(int protocolType) { // 생성자
		this.protocolType = protocolType;
		getPacket(protocolType, protocolCode);
	}

	public Protocol(int protocolType, int protocolCode) { // 코드가 있는 타입 생성자..by 규철
		this.protocolType = protocolType;
		this.protocolCode = protocolCode;
		getPacket(protocolType, protocolCode);
	}

	///////////////////////////////////////////////////////// 일단 조회는 LOOKUP에 갱신은
	///////////////////////////////////////////////////////// UPDATE라고 했습니다 행님들
	// 그리고 밑에도 추가해야함
	// 프로토콜 타입에 따라 바이트 배열 packet의 length가 다름
	public byte[] getPacket(int protocolType, int typeCode) {
		if (packet == null) {
			switch (protocolType) {
				case PT_REQ_LOGIN:
					packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_LOGIN_ID + LEN_LOGIN_PASSWORD
							+ LEN_BODY_SEPARATOR];
					break;
				case PT_RES_LOGIN:
					switch (typeCode) {
						case CODE_PT_RES_LOGIN_CUS_SUCCESS:
						case CODE_PT_RES_LOGIN_ADMIN_SUCCESS:
						case CODE_PT_RES_LOGIN_FAIL:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE];
							break;
					}
					break;
				case PT_UNDEFINED:
					packet = new byte[LEN_MAX];
					break;
				case PT_REQ_LOOKUP:
					switch (typeCode) {
						case CODE_PT_REQ_LOOKUP_ALL_THEATER:
						case CODE_PT_REQ_LOOKUP_ALL_SCREEN:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_PROTOCOL_FRAG + LEN_PROTOCOL_LAST + LEN_PROTOCOL_SEQNUM];
							break;
						case CODE_PT_REQ_LOOKUP_THEATER_SALES:
						case CODE_PT_REQ_LOOKUP_TOTAL_SALES:
						case CODE_PT_REQ_LOOKUP_THEATER_CANCEL_RATE:
						case CODE_PT_REQ_LOOKUP_THEATER_RESV_RATE:
						case CODE_PT_REQ_LOOKUP_ALL_SCREEN_SCHEDULE:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE];
							break;
						case CODE_PT_REQ_LOOKUP_FIND_CUS_ID:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_BODY_SEPARATOR + LEN_MEM_NAME + LEN_MEM_EMAIL];
							break;
						case CODE_PT_REQ_LOOKUP_FIND_CUS_PASSWORD:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 2 * LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_MEM_NAME + LEN_MEM_EMAIL];
							break;
						case CODE_PT_REQ_LOOKUP_THEATER:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_BODY_SEPARATOR + LEN_THEATER_AREA + LEN_FILM_ID];
							break;
						case CODE_PT_REQ_LOOKUP_SCREEN_TIME:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_BODY_SEPARATOR + LEN_THEATER_ID + LEN_FILM_ID];
							break;
						case CODE_PT_REQ_LOOKUP_SCREEN_TABLE:
						case CODE_PT_REQ_LOOKUP_AUDI:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_THEATER_ID];
							break;
						case CODE_PT_REQ_LOOKUP_SEAT_SITUATION:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_SCREEN_ID];
							break;
						case CODE_PT_REQ_LOOKUP_AREA:
						case CODE_PT_REQ_LOOKUP_FILM_DETAIL:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_FILM_ID];
							break;
						case CODE_PT_REQ_LOOKUP_MY_INFO:
						case CODE_PT_REQ_LOOKUP_MY_REVIEWS:
						case CODE_PT_REQ_LOOKUP_RESV_LIST:
						case CODE_PT_REQ_LOOKUP_THEATER_FOR_ADMIN:
						case CODE_PT_REQ_LOOKUP_ACCOUNT:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_LOGIN_ID];
							break;
					}
					break;
				case PT_RES_LOOKUP:
					switch (typeCode) {
						case CODE_PT_RES_LOOKUP_FIND_CUS_ID_NO:
						case CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_OK:
						case CODE_PT_RES_LOOKUP_FIND_CUS_PASSWORD_NO:
						case CODE_PT_RES_LOOKUP_THEATER_NO:
						case CODE_PT_RES_LOOKUP_AUDI_NO:
						case CODE_PT_RES_LOOKUP_SEAT_SITUATION_NO:
						case CODE_PT_RES_LOOKUP_FILM_DETAIL_NO:
						case CODE_PT_RES_LOOKUP_MY_INFO_NO:
						case CODE_PT_RES_LOOKUP_MY_REVIEWS_NO:
						case CODE_PT_RES_LOOKUP_RESV_LIST_NO:
						case CODE_PT_RES_LOOKUP_ALL_SCREEN_NO:
						case CODE_PT_RES_LOOKUP_THEATER_FOR_ADMIN_NO:
						case CODE_PT_RES_LOOKUP_THEATER_SALES_NO:
						case CODE_PT_RES_LOOKUP_TOTAL_SALES_NO:
						case CODE_PT_RES_LOOKUP_THEATER_CANCEL_RATE_NO:
						case CODE_PT_RES_LOOKUP_THEATER_RESV_RATE_NO:
						case CODE_PT_RES_LOOKUP_ACCOUNT_NO:
						case CODE_PT_RES_LOOKUP_AREA_NO:
						case CODE_PT_RES_LOOKUP_SCREEN_TIME_NO:
						case CODE_PT_RES_LOOKUP_ALL_THEATER_NO:
						case CODE_PT_RES_LOOKUP_SCREEN_TABLE_NO:
						case CODE_PT_REQ_LOOKUP_ALL_SCREEN_SCHEDULE_NO:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE];
							break;
						case CODE_PT_RES_LOOKUP_AUDI_OK:
						case CODE_PT_RES_LOOKUP_THEATER_OK:
						case CODE_PT_RES_LOOKUP_SEAT_SITUATION_OK:
						case CODE_PT_RES_LOOKUP_FILM_DETAIL_OK:
						case CODE_PT_RES_LOOKUP_MY_REVIEWS_OK:
						case CODE_PT_RES_LOOKUP_RESV_LIST_OK:
						case CODE_PT_RES_LOOKUP_ALL_SCREEN_OK:

						case CODE_PT_RES_LOOKUP_THEATER_FOR_ADMIN_OK:
						case CODE_PT_RES_LOOKUP_THEATER_SALES_OK:
						case CODE_PT_RES_LOOKUP_THEATER_CANCEL_RATE_OK:
						case CODE_PT_RES_LOOKUP_THEATER_RESV_RATE_OK:
						case CODE_PT_RES_LOOKUP_AREA_OK:
						case CODE_PT_RES_LOOKUP_SCREEN_TIME_OK:
						case CODE_PT_RES_LOOKUP_ALL_THEATER_OK:
						case CODE_PT_RES_LOOKUP_SCREEN_TABLE_OK:
						case CODE_PT_REQ_LOOKUP_ALL_SCREEN_SCHEDULE_OK:
							packet = new byte[LEN_MAX];
							break;
						case CODE_PT_RES_LOOKUP_MY_INFO_OK:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 8 * LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_LOGIN_PASSWORD + LEN_MEM_NAME
									+ LEN_MEM_PHONENUM + LEN_MEM_EMAIL + LEN_MEM_ACCOUNT + LEN_MEM_MONEY
									+ LEN_MEM_GENDER + LEN_MEM_BIRTHDAY];
							break;
						case CODE_PT_RES_LOOKUP_TOTAL_SALES_OK:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_TOTAL_SALES];
							break;
						case CODE_PT_RES_LOOKUP_ACCOUNT_OK:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_BODY_SEPARATOR + LEN_MEM_ACCOUNT + LEN_MEM_MONEY];
							break;
						case CODE_PT_RES_LOOKUP_FIND_CUS_ID_OK:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_LOGIN_ID];
							break;
					}
					break;
				case PT_REQ_UPDATE:
					switch (typeCode) {
						case CODE_PT_REQ_UPDATE_ADD_MEM:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 7 * LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_LOGIN_PASSWORD + LEN_MEM_NAME
									+ LEN_MEM_PHONENUM + LEN_MEM_GENDER + LEN_MEM_EMAIL + LEN_MEM_ACCOUNT
									+ LEN_MEM_BIRTHDAY];
							break;
						case CODE_PT_REQ_UPDATE_CHANGE_MEM_INFO:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 3 * LEN_BODY_SEPARATOR + LEN_LOGIN_PASSWORD + LEN_MEM_PHONENUM + LEN_MEM_EMAIL
									+ LEN_MEM_ACCOUNT];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_MEM:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_LOGIN_ID];
							break;
						case CODE_PT_REQ_UPDATE_CHANGE_PASSWORD:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_LOGIN_PASSWORD];
							break;
						case CODE_PT_REQ_UPDATE_ADD_PAY_RESV:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 4 * LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_SCREEN_ID + LEN_RESV_SEATNUM
									+ LEN_RESV_PEOPLENUM + LEN_RESV_DEPOSITAMOUNT];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_PAY_RESV:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_RESV_NUM];
							break;
						case CODE_PT_REQ_UPDATE_ADD_REVIEW:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 3 * LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_FILM_ID + LEN_REV_CONTENT
									+ LEN_REV_STARPOINT];
							break;
						case CODE_PT_REQ_UPDATE_CHANGE_REVIEW:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 2 * LEN_BODY_SEPARATOR + LEN_REV_ID + LEN_REV_CONTENT + LEN_REV_STARPOINT];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_REVIEW:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_REV_ID];
							break;
						case CODE_PT_REQ_UPDATE_ADD_THEATER:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 3 * LEN_BODY_SEPARATOR + LEN_LOGIN_ID + LEN_THEATER_NAME + LEN_THEATER_AREA
									+ LEN_THEATER_ADDRESS];
							break;
						case CODE_PT_REQ_UPDATE_CHANGE_THEATER:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 4 * LEN_BODY_SEPARATOR + LEN_THEATER_ID + LEN_LOGIN_ID + LEN_THEATER_NAME
									+ LEN_THEATER_AREA + LEN_THEATER_ADDRESS];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_THEATER:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ LEN_THEATER_ID];
							break;
						case CODE_PT_REQ_UPDATE_ADD_AUDI:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 2 * LEN_BODY_SEPARATOR + LEN_AUDI_NUM + LEN_THEATER_ID + LEN_AUDI_SEATCNT];
							break;
						case CODE_PT_REQ_UPDATE_CHANGE_AUDI:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 3 * LEN_BODY_SEPARATOR + LEN_AUDI_ID + LEN_AUDI_NUM + LEN_THEATER_ID
									+ LEN_AUDI_SEATCNT];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_AUDI:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_AUDI_ID];
							break;
						case CODE_PT_REQ_UPDATE_ADD_SCREEN_TABLE:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 4 * LEN_BODY_SEPARATOR + LEN_AUDI_ID + LEN_FILM_ID + LEN_AUDI_SEATCNT
									+ LEN_SCREEN_STARTTIME + LEN_SCREEN_FINALTIME];
							break;
						case CODE_PT_REQ_UPDATE_CHANGE_SCREEN_TABLE:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN
									+ 5 * LEN_BODY_SEPARATOR + LEN_SCREEN_ID + LEN_AUDI_ID + LEN_FILM_ID
									+ LEN_SCREEN_RESIDUALSEAT + LEN_SCREEN_STARTTIME + LEN_SCREEN_FINALTIME];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_SCREEN_TABLE:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_SCREEN_ID];
							break;
						case CODE_PT_REQ_UPDATE_ADD_FILM:
						case CODE_PT_REQ_UPDATE_CHANGE_FILM:
							packet = new byte[LEN_MAX];
							break;
						case CODE_PT_REQ_UPDATE_DELETE_FILM:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_FILM_ID];
							break;
					}
				case PT_RES_UPDATE:
					switch (typeCode) {
						case CODE_PT_RES_UPDATE_ADD_MEM_OK:
						case CODE_PT_RES_UPDATE_ADD_MEM_NO:
						case CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_OK:
						case CODE_PT_RES_UPDATE_CHANGE_MEM_INFO_NO:
						case CODE_PT_RES_UPDATE_DELETE_MEM_OK:
						case CODE_PT_RES_UPDATE_DELETE_MEM_NO:
						case CODE_PT_RES_UPDATE_CHANGE_PASSWORD_OK:
						case CODE_PT_RES_UPDATE_CHANGE_PASSWORD_NO:
						case CODE_PT_RES_UPDATE_ADD_PAY_RESV_OK:
						case CODE_PT_RES_UPDATE_ADD_PAY_RESV_NO:
						case CODE_PT_RES_UPDATE_DELETE_PAY_RESV_OK:
						case CODE_PT_RES_UPDATE_DELETE_PAY_RESV_NO:
						case CODE_PT_RES_UPDATE_ADD_REVIEW_OK:
						case CODE_PT_RES_UPDATE_ADD_REVIEW_NO:
						case CODE_PT_RES_UPDATE_CHANGE_REVIEW_OK:
						case CODE_PT_RES_UPDATE_CHANGE_REVIEW_NO:
						case CODE_PT_RES_UPDATE_DELETE_REVIEW_OK:
						case CODE_PT_RES_UPDATE_DELETE_REVIEW_NO:
						case CODE_PT_RES_UPDATE_ADD_THEATER_OK:
						case CODE_PT_RES_UPDATE_ADD_THEATER_NO:
						case CODE_PT_RES_UPDATE_CHANGE_THEATER_OK:
						case CODE_PT_RES_UPDATE_CHANGE_THEATER_NO:
						case CODE_PT_RES_UPDATE_DELETE_THEATER_OK:
						case CODE_PT_RES_UPDATE_DELETE_THEATER_NO:
						case CODE_PT_RES_UPDATE_ADD_AUDI_OK:
						case CODE_PT_RES_UPDATE_ADD_AUDI_NO:
						case CODE_PT_RES_UPDATE_CHANGE_AUDI_OK:
						case CODE_PT_RES_UPDATE_CHANGE_AUDI_NO:
						case CODE_PT_RES_UPDATE_DELETE_AUDI_OK:
						case CODE_PT_RES_UPDATE_DELETE_AUDI_NO:
						case CODE_PT_RES_UPDATE_ADD_SCREEN_TABLE_OK:
						case CODE_PT_RES_UPDATE_ADD_SCREEN_TABLE_NO:
						case CODE_PT_RES_UPDATE_CHANGE_SCREEN_TABLE_OK:
						case CODE_PT_RES_UPDATE_CHANGE_SCREEN_TABLE_NO:
						case CODE_PT_RES_UPDATE_DELETE_SCREEN_TABLE_OK:
						case CODE_PT_RES_UPDATE_DELETE_SCREEN_TABLE_NO:
						case CODE_PT_RES_UPDATE_ADD_FILM_OK:
						case CODE_PT_RES_UPDATE_ADD_FILM_NO:
						case CODE_PT_RES_UPDATE_CHANGE_FILM_OK:
						case CODE_PT_RES_UPDATE_CHANGE_FILM_NO:
						case CODE_PT_RES_UPDATE_DELETE_FILM_OK:
						case CODE_PT_RES_UPDATE_DELETE_FILM_NO:
							packet = new byte[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE];
							break;
					}
					break;
				case PT_EXIT:
					packet = new byte[LEN_PROTOCOL_TYPE];
					break;
			} // end switch
		} // end if
		packet[0] = (byte) protocolType; // packet 바이트 배열의 첫 번째 바이트에 프로토콜 타입 설정
		packet[1] = (byte) typeCode;
		return packet;
	}

	// 로그인후 성공/실패의 결과 값을 프로토콜로부터 추출하여 문자열로 리턴
	public String getLoginResult() {
		// String의 다음 생성자를 사용 : String(byte[] bytes, int offset, int length)
		return new String(packet, LEN_TYPE_CODE, LEN_TYPE_CODE).trim();
	}

	// String ok를 byte[]로 만들어서 packet의 프로토콜 타입 바로 뒤에 추가
	public void setLoginResult(String ok) {
		// arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
		System.arraycopy(ok.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, ok.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + ok.trim().getBytes().length] = '\0';
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public byte[] getPacket() {
		return packet;
	}

	// Default 생성자로 생성한 후 Protocol 클래스의 packet 데이터를 바꾸기 위한 메서드
	public void setPacket(int pt, int code, byte[] buf) {
		packet = null;
		packet = getPacket(pt, code);
		protocolType = pt;
		protocolCode = code;
		System.arraycopy(buf, 0, packet, 0, packet.length);
	}

	public void setPacket(int pt, int code, int bodyLen, byte[] buf) {
		packet = null;
		packet = getPacket(pt, code);
		protocolType = pt;
		protocolCode = code;
		protocolBodyLen = bodyLen;
		System.arraycopy(buf, 0, packet, 0, packet.length);
	}

	public void setPacket(int pt, int code, int bodyLen, int flag, int last, int seqNum, byte[] buf) {
		packet = null;
		packet = getPacket(pt, code);
		protocolType = pt;
		protocolCode = code;
		this.packet[3] = (byte) bodyLen;
		protocolBodyLen = bodyLen;
		this.packet[5] = (byte) flag;
		protocolFlag = flag;
		this.packet[6] = (byte) last;
		protocolLast = last;
		this.packet[7] = (byte) seqNum;
		protocolSeqNum = seqNum;
		System.arraycopy(buf, 0, packet, 0, buf.length);
	}
	// byte[] packet에 String ID를 byte[]로 만들어 프로토콜 타입 바로 뒤에 추가

	public void setID_Password(String id, String pw) {
		String finalStr = id + "\\" + pw;
		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getID_Password() { // 로그인인증요청
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_LOGIN_ID + LEN_LOGIN_PASSWORD + LEN_BODY_SEPARATOR).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setID(String id) { // 조회응답코드1 조회요청코드D 조회요청코드13 조회요청코드9 조회요청코드A 조회요청코드B 갱신요청코드3
		System.arraycopy(id.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN,
				id.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + id.trim().getBytes().length] = '\0';
	}

	public String getID() {
		// String(byte[] bytes, int offset, int length)
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_LOGIN_ID).trim();
	}

	// 패스워드는 byte[]에서 로그인 아이디 바로 뒤에 있음
	public String getPassword() {
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_LOGIN_PASSWORD).trim();
	}

	public void setPassword(String password) { // 갱신요청코드4
		System.arraycopy(password.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				password.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + password.trim().getBytes().length] = '\0';
	}

	public ArrayList<Protocol> setTheaterArea(String list) {
		ArrayList<Protocol> arr = new ArrayList<>();

		return arr;
	}

	public String getTheaterArea() {
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_THEATER_AREA).trim();
	}

	public void setName_Email(String name, String email) { // 조회요청코드0 아이디 찾기 요청시 name,email 입력..by 규철
		this.protocolCode = 0;
		String finalStr = name + "\\" + email;
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getName_Email() { // 위에꺼 세트..by 규철
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN,
				getProtocolBodyLen()).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setID_Name_Email(String id, String name, String email) { // 조회요청코드1 비밀번호 찾기 요청시 로그인id,name,email 입력..by
																			// 규철
		String finalStr = id + "\\" + name + "\\" + email;
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getID_Name_Email() { // 위에꺼 세트..by 규철
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setFlimID(String id) { // 조회요청코드2 지역 조회 요청시 영화id 입력..by 규철
		System.arraycopy(id.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				id.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + id.trim().getBytes().length] = '\0';
	}

	public String getFlimID() { // 위에꺼 세트 ..by 규철
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
	}

	public void setTheaterArea_FlimID(String area, String flimID) { // 조회요청코드3 영화관 조회 요청시 지역, 영화id 입력..by 규철
		String finalStr = area + "\\" + flimID;
		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getTheaterArea_FlimID() { // 위에꺼 세트 ..by 규철
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setTheaterID_FlimID(String theaterID, String flimID) { // 조회요청코드4 상영시간 조회 요청시 영화관id, 영화id 입력..by 규철
		String finalStr = theaterID + "\\" + flimID;
		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getTheaterID_FlimID() { // 위에꺼 세트 ..by 규철
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setTheaterID(String id) { // 조회요청코드6 조회요청코드8 조회요청코드E 영화관id 입력..by 규철
		System.arraycopy(id.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN,
				id.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + id.trim().getBytes().length] = '\0';
	}

	public String getTheaterID() { // 위에꺼 세트 ..by 규철
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, getProtocolBodyLen())
				.trim();
	}

	public void setScreenID(String id) { // 조회요청코드7 영화관id 입력..by 규철
		System.arraycopy(id.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN,
				id.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + id.trim().getBytes().length] = '\0';
	}

	public String getScreenID() { // 위에꺼 세트 ..by 규철
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, getProtocolBodyLen())
				.trim();
	}

	public void setTheaterList(String[] list) {// 조회응답코드5
		String finalStr = "";// 사용시 쿼리문 받으면서 바로 넣던지 스트링배열로 받아서 cnt세서 여기서 넣을지 결정할것
		for (int i = 0; i < list.length; i++) {
			finalStr += (list[i] + "\\");
		} ////////////////////////////////////////////////////

		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getTheaterList() {
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, getProtocolBodyLen()).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public ArrayList<Protocol> setScreenList(String list) {// 조회영회리스트
		ArrayList<Protocol> arr = new ArrayList<Protocol>();

		int headLength = LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG
				+ LEN_PROTOCOL_LAST + LEN_PROTOCOL_SEQNUM;
		int maxBodyLen = LEN_MAX - headLength;
		int srcBegin = 0;
		int srcEnd = 0;
		String packetList = "";
		int bodyLen = list.getBytes().length;

		byte[] b = new byte[2];
		byte[] s = new byte[2];

		int seqNum = 0;
		packet = new byte[LEN_MAX];
		for (; maxBodyLen < bodyLen; bodyLen -= maxBodyLen, seqNum++) {

			srcEnd += maxBodyLen + 1;
			packetList = list.substring(srcBegin, srcEnd);
			srcBegin += srcEnd;

			packet[0] = PT_RES_LOOKUP;
			packet[1] = CODE_PT_RES_LOOKUP_ALL_SCREEN_OK;
			b[0] = (byte) ((bodyLen & 0x0000ff00) >> 8);
			b[1] = (byte) (bodyLen & 0x000000ff);
			System.arraycopy(b, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_PROTOCOL_BODYLEN);
			packet[5] = 1;
			packet[6] = 0;
			s[0] = (byte) ((seqNum & 0x0000ff00) >> 8);
			s[1] = (byte) (seqNum & 0x000000ff);
			System.arraycopy(s, 0, packet,
					LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG + LEN_PROTOCOL_LAST,
					LEN_PROTOCOL_SEQNUM);

			System.arraycopy(packetList.getBytes(), 0, packet, headLength, maxBodyLen);

			arr.add(this);
		}
		if (bodyLen < maxBodyLen) {

			packetList = list.substring(srcBegin);

			packet[0] = PT_RES_LOOKUP;
			packet[1] = CODE_PT_RES_LOOKUP_ALL_SCREEN_OK;
			b[0] = (byte) ((bodyLen & 0x0000ff00) >> 8);
			b[1] = (byte) (bodyLen & 0x000000ff);
			System.arraycopy(b, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_PROTOCOL_BODYLEN);
			packet[5] = 1;
			packet[6] = 1;
			s[0] = (byte) ((seqNum & 0x0000ff00) >> 8);
			s[1] = (byte) (seqNum & 0x000000ff);
			System.arraycopy(s, 0, packet,
					LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG + LEN_PROTOCOL_LAST,
					LEN_PROTOCOL_SEQNUM);

			System.arraycopy(packetList.getBytes(), 0, packet, headLength, packetList.getBytes().length);

			arr.add(this);
		}
		return arr;
	}

	public String[] getScreenList(String list) {// 조회영회리스트// "영화id\영화제목\영화포스터\예매율\별점"
		int headLength = LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG
				+ LEN_PROTOCOL_LAST + LEN_PROTOCOL_SEQNUM;
		int dataLength = LEN_MAX - headLength;
		int srcBegin = headLength;
		int srcEnd = LEN_MAX + 1;
		String packetList = "";
		String resultStr = "";
		int i = list.length();
		for (; LEN_MAX < i; i -= LEN_MAX) {
			packetList = list.substring(srcBegin, srcEnd);
			srcBegin += LEN_MAX;
			srcEnd += LEN_MAX;
			resultStr += new String(packetList.getBytes(), headLength, dataLength);
		}
		if (i <= LEN_MAX) {
			packetList = list.substring(srcBegin);
			resultStr += new String(packetList.getBytes(), headLength, packetList.length() - headLength);
		}
		String[] splited = resultStr.split("|");
		return splited;

	}

	public String getScreenList()// "영화제목\영화포스터\예매율\별점"
	{
		return new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG
				+ LEN_PROTOCOL_LAST + LEN_PROTOCOL_SEQNUM, getProtocolBodyLen());
	}

	public void setTheScreenList(int cnt, String[] list) {// 조회응답코드7
		String finalStr = "";// 사용시 쿼리문을 스트링 배열로 받으면서 상영관과 상영시간 사이 ~ 를 넣고(상영관~상영시간)여기로 가져와서 \추가할것.
		for (int i = cnt; i > 0; i--) {
			finalStr += (list[i - 1] + "|");
		} ////////////////////////////////////////////////////

		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getTheScreenList()// 위에꺼 세트 by 규철
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;// (상영관~상영시간)이므로 한번 더 잘라야함
	}

	public ArrayList<Protocol> setList(String list) {
		ArrayList<Protocol> arr = new ArrayList<Protocol>();

		int headLength = LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG
				+ LEN_PROTOCOL_LAST + LEN_PROTOCOL_SEQNUM;
		int maxBodyLen = LEN_MAX - headLength;
		int srcBegin = 0;
		int srcEnd = 0;
		String packetList = "";
		int bodyLen = list.getBytes().length;

		byte[] b = new byte[2];
		byte[] s = new byte[2];

		int seqNum = 0;
		packet = new byte[LEN_MAX];
		for (; maxBodyLen < bodyLen; bodyLen -= maxBodyLen, seqNum++) {

			srcEnd += maxBodyLen + 1;
			packetList = list.substring(srcBegin, srcEnd);
			srcBegin += srcEnd;

			packet[0] = (byte) protocolType;
			packet[1] = (byte) protocolCode;
			b[0] = (byte) ((bodyLen & 0x0000ff00) >> 8);
			b[1] = (byte) (bodyLen & 0x000000ff);
			System.arraycopy(b, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_PROTOCOL_BODYLEN);
			packet[5] = 1;
			packet[6] = 0;
			s[0] = (byte) ((seqNum & 0x0000ff00) >> 8);
			s[1] = (byte) (seqNum & 0x000000ff);
			System.arraycopy(s, 0, packet,
					LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG + LEN_PROTOCOL_LAST,
					LEN_PROTOCOL_SEQNUM);

			System.arraycopy(packetList.getBytes(), 0, packet, headLength, maxBodyLen);

			arr.add(this);
		}
		if (bodyLen < maxBodyLen) {

			packetList = list.substring(srcBegin);

			packet[0] = (byte) protocolType;
			packet[1] = (byte) protocolCode;
			b[0] = (byte) ((bodyLen & 0x0000ff00) >> 8);
			b[1] = (byte) (bodyLen & 0x000000ff);
			System.arraycopy(b, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_PROTOCOL_BODYLEN);
			packet[5] = 1;
			packet[6] = 1;
			s[0] = (byte) ((seqNum & 0x0000ff00) >> 8);
			s[1] = (byte) (seqNum & 0x000000ff);
			System.arraycopy(s, 0, packet,
					LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_PROTOCOL_FRAG + LEN_PROTOCOL_LAST,
					LEN_PROTOCOL_SEQNUM);

			System.arraycopy(packetList.getBytes(), 0, packet, headLength, packetList.getBytes().length);

			arr.add(this);
		}
		return arr;
	}

	public void setSeatNumList(int cnt, String[] list) {// 조회응답코드9
		String finalStr = "";// 사용시 쿼리문을 스트링 배열로 받으면서 상영관과 상영시간 사이 ~ 를 넣고(상영관~상영시간)여기로 가져와서 \추가할것.
		for (int i = cnt; i > 0; i--) {
			finalStr += (list[i - 1] + "~");
		} ////////////////////////////////////////////////////

		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getSeatNumList()// 위에꺼 세트 by 규철
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("~");
		return splited;
	}

	public void setScreenDetails(int cnt, String[] detail) {// 조회응답코드B
		String finalStr = "";// 사용시 쿼리문을 스트링 배열로 받으면서 상영관과 상영시간 사이 ~ 를 넣고(상영관~상영시간)여기로 가져와서 \추가할것.
		for (int i = cnt; i > 0; i--) {
			finalStr += (detail[i - 1] + "\\");
		} ////////////////////////////////////////////////////

		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getScreenDetails()// 위에꺼 세트 by 규철
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setMemberDetails(int cnt, String[] detail) {// 조회응답코드D
		String finalStr = "";
		for (int i = cnt; i > 0; i--) {
			finalStr += (detail[i - 1] + "\\");
		} ////////////////////////////////////////////////////

		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getMemberDetails()// 위에꺼 세트 by 규철
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setMemberReviews(String[] detail) {// 조회응답코드F
		String finalStr = detail[0] + "\\" + detail[1] + "\\" + detail[2] + "\\" + detail[3] + "\\" + detail[4];
		System.arraycopy(finalStr.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getMemberReviews()// 위에꺼 세트 by 규철
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setMemberJoin(String[] data) {// 갱신요청코드1(아이디\비밀번호\성명\휴대전화번호\계좌번호\성별\이메일\생년월일)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3] + "\\" + data[4] + "\\" + data[5]
				+ "\\" + data[6] + "\\" + data[7];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 7 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getMemberJoin()// 위에꺼 세트 by 규철
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setMember_Modify_Info(String[] data) {// 갱신요청코드2(비밀번호\휴대전화번호\이메일 주소\계좌번호)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 3 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getMember_Modify_Inf()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setID_ResevID(String loginID) {// 갱신요청코드3(아이디)
		String finalStr = loginID;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getID_ResevID()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	public void setChangePwd(String newPwd) {// 갱신요청코드4(비밀번호)
		String finalStr = newPwd;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getChangePwd()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	public void setAdd_Pay_Resv(String[] data) {// 갱신요청코드5(고객ID\상영영화ID\좌석번호\인원수\금액)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3] + "\\" + data[4];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 4 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getAdd_Pay_Resv()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setDel_Pay_Resv(String[] data) {// 갱신요청코드6(고객ID\예매번호ID)
		String finalStr = data[0] + "\\" + data[1];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getDel_Pay_Resv()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setAdd_Review(String[] data) {// 갱신요청코드7(고객id\영화id\리뷰내용\별점)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 3 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getAdd_Review()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setChange_Review(String[] data) {// 갱신요청코드8(게시물id\리뷰내용\별점)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 2 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getChange_Review()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setDel_Review(String reviewId) {// 갱신요청코드9(게시물id)
		String finalStr = reviewId;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getDel_Review()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	public void setAdd_Theater(String[] data) {// 갱신요청코드10(담당자id\영화관명\지역\주소)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 3 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getAdd_Theater()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setChange_Theater(String[] data) {// 갱신요청코드11(영화관id\담당자id\영화관명\지역\주소)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3] + "\\" + data[4];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 4 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getChange_Theater()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setDel_Theater(String theaterId) {// 갱신요청코드12(영화관id)
		String finalStr = theaterId;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getDel_Theater()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	public void setAdd_Audi(String[] data) {// 갱신요청코드13(상영관 번호\영화관id\좌석수)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 2 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getAdd_Audi()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setChange_Audi(String[] data) {// 갱신요청코드14(상영관ID\상영관번호\영화관ID\좌석수)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 3 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getChange_Audi()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setDel_Audi(String audiId) {// 갱신요청코드15(상영관id)
		String finalStr = audiId;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getDel_Audi()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	public void setAdd_Screen(String[] data) {// 갱신요청코드16(상영관id\영화id\상영관 좌석수\상영시작시간\상영종료시간)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3] + "\\" + data[4];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 4 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getAdd_Screen()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setChange_Screen(String[] data) {// 갱신요청코드17(상영영화ID\상영관ID\영화ID\잔여석\상영시작시각\상영종료시각)
		String finalStr = data[0] + "\\" + data[1] + "\\" + data[2] + "\\" + data[3] + "\\" + data[4] + "\\" + data[5];
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + 5 * LEN_BODY_SEPARATOR
				+ finalStr.trim().getBytes().length] = '\0';
	}

	public String[] getChange_Screen()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		String[] splited = origin.split("\\\\");
		return splited;
	}

	public void setDel_Screen(String screenId) {// 갱신요청코드18(상영영화ID)
		String finalStr = screenId;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getDel_Screen()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	// 갱신요청코드19(영화명\티저\정보(감독~출연진1~2~3)\장르\개봉일\줄거리\포스터\예매율)
	// 갱신요청코드20(영화ID\영화명\티저\정보(감독~출연진1~2~3)\장르\개봉일\줄거리\포스터\예매율)

	public void setDel_Film(String filmId) {// 갱신요청코드21(영화ID)
		String finalStr = filmId;
		System.arraycopy(finalStr.trim().getBytes().length, 0, packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE,
				LEN_PROTOCOL_BODYLEN);
		System.arraycopy(finalStr.trim().getBytes(), 0, packet,
				LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, finalStr.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN + finalStr.trim().getBytes().length] = '\0';
	}

	public String getDel_Film()// 위에꺼 세트
	{
		String origin = new String(packet, LEN_PROTOCOL_TYPE + LEN_TYPE_CODE + LEN_PROTOCOL_BODYLEN, LEN_MAX).trim();
		return origin;
	}

	public int getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(int protocolCode) {
		this.protocolCode = protocolCode;
	}

	public int getProtocolBodyLen() {
		return protocolBodyLen;
	}

	public void setProtocolBodyLen(int protocolBodyLen) {
		this.protocolBodyLen = protocolBodyLen;
	}

	public int getProtocolFlag() {
		return protocolFlag;
	}

	public void setProtocolFlag(int protocolFlag) {
		this.protocolFlag = protocolFlag;
	}

	public int getProtocolLast() {
		return protocolLast;
	}

	public void setProtocolLast(int protocolLast) {
		this.protocolLast = protocolLast;
	}

	public int getProtocolSeqNum() {
		return protocolSeqNum;
	}

	public void setProtocolSeqNum(int protocolSeqNum) {
		this.protocolSeqNum = protocolSeqNum;
	}
}
